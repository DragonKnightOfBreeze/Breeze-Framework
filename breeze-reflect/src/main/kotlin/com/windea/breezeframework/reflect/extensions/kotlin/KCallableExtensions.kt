package com.windea.breezeframework.reflect.extensions.kotlin

import kotlin.reflect.*

/**得到指定类型、名称以及其他附加条件的首个可调用项。*/
inline fun <reified T : KCallable<*>> Collection<KCallable<*>>.getCallable(name: String, predicate: T.() -> Boolean): T? {
	return this.filter { it.name == name }.filterIsInstance<T>().firstOrNull(predicate)
}

/**得到指定类型、名称以及其他附加条件的首个属性。*/
inline fun <reified T : KProperty<*>> Collection<KProperty<*>>.getProperty(name: String, predicate: T.() -> Boolean): T? {
	return this.filter { it.name == name }.filterIsInstance<T>().firstOrNull(predicate)
}

/**得到指定类型、名称以及其他附加条件的首个方法。*/
inline fun <reified T : KFunction<*>> Collection<KFunction<*>>.getFunction(name: String, predicate: T.() -> Boolean): T? {
	return this.filter { it.name == name }.filterIsInstance<T>().firstOrNull(predicate)
}
