@file:JvmName("CloneableExtensions")
@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*

//由于java.lang.Object的clone方法默认是受保护的，不能直接在扩展方法中访问
//这里采用反射的方式实现，但是不能保证克隆能够成功

private val cloneMethod = Any::class.java.getDeclaredMethod("clone").apply {
	isAccessible = true
}

private fun <T : Cloneable> T.cloneMethod() = try {
	this::class.java.getMethod("clone")
} catch(e: Exception) {
	cloneMethod
}

/**
 * 克隆一个可克隆对象。自动进行类型转化。
 *
 * 应当为可克隆对象提供一个公开的克隆方法的重载。
 */
fun <T : Cloneable> T.shallowClone(): T {
	return this.cloneMethod().invoke(this) as T
}

/**
 * 深克隆一个可克隆对象。自动进行类型转化。
 * 仅对可克隆可更改的字段采用深克隆操作。
 * 当字段为集合类型（列表、集或映射）但未实现Cloneable接口时，仍然对其采用深克隆操作。
 *
 * 应当为可克隆对象提供一个公开的克隆方法的重载。
 */
@NotTested
@LowPerformanceApi
fun <T : Cloneable> T.deepClone(): T {
	return this.shallowClone().apply {
		this::class.java.declaredFields.filterNot { prop ->
			//需要排除的情况：基本类型，基本类型数组，枚举，不可变字段，静态字段
			prop.type.isPrimitive || prop.type.componentType?.isPrimitive.orFalse() || prop.type.isEnum ||
			Modifier.isFinal(prop.modifiers) || Modifier.isStatic(prop.modifiers)
		}.forEach { prop ->
			prop.isAccessible = true

			when(val propValue = prop.get(this)) {
				is Cloneable -> prop.set(this, propValue.deepClone())
				//以下只读集合类型，在Jvm中可能没有实现Cloneable接口
				is List<*> -> prop.set(this, propValue.map { (it as? Cloneable)?.deepClone() ?: it })
				is Set<*> -> prop.set(this, propValue.map { (it as? Cloneable)?.deepClone() ?: it })
				is Map<*, *> -> prop.set(this, propValue.mapValues { (_, v) -> (v as? Cloneable)?.deepClone() ?: v })
				//认为所有用户可访问的常见可变集合类型，在Jvm中都实现了Cloneable接口
			}
		}
	}
}
