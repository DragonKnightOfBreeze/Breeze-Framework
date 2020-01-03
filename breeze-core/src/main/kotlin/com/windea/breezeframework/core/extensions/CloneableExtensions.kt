@file:JvmName("CloneableExtensions")
@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.marks.*
import java.lang.reflect.*

//由于java.lang.Object的clone方法默认是受保护的，不能直接在扩展方法中访问
//这里采用反射的方式实现，但是不能保证克隆能够成功

internal val cloneMethod = Any::class.java.getDeclaredMethod("clone").apply {
	isAccessible = true
}

/**
 * 克隆一个可克隆对象。自定进行类型转换。
 */
@NotRecommended("There is no guarantee that clone operation will succeed by reflection.")
fun <T : Cloneable> T.shallowClone(): T {
	return cloneMethod.invoke(this) as T
}

/**
 * 深克隆一个可克隆对象。自定进行类型转换。
 * 仅对可克隆可更改的字段采用深克隆操作。
 * 可选是否克隆集合（如列表、集、映射）字段中（可克隆可更改）的字段，默认为true。
 */
@NotRecommended("There is no guarantee that clone operation will succeed by reflection.")
@LowPerformanceApi
fun <T : Cloneable> T.deepClone(includeCollection: Boolean = true): T {
	return this.shallowClone().apply {
		this::class.java.declaredFields.forEach { prop ->
			//如果是不可变字段或静态字段，则不处理
			if(Modifier.isFinal(prop.modifiers) || Modifier.isStatic(prop.modifiers)) return@forEach

			prop.isAccessible = true
			val propValue = prop.get(this)
			if(propValue is Cloneable) prop.set(this, propValue.deepClone())

			if(!includeCollection) return@forEach
			when(propValue) {
				is List<*> -> prop.set(this, propValue.map { (it as? Cloneable)?.deepClone() ?: it })
				is Set<*> -> prop.set(this, propValue.map { (it as? Cloneable)?.deepClone() ?: it })
				is Map<*, *> -> prop.set(this, propValue.mapValues { (_, v) -> (v as? Cloneable)?.deepClone() ?: v })
			}
		}
	}
}
