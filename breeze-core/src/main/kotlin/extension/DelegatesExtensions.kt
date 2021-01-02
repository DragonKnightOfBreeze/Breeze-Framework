// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DelegatesExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import kotlin.properties.*

/**创建一个可观察的委托属性，并传入回调函数。*/
inline fun <T> Delegates.onChange(initialValue: T, crossinline callback: (oldValue: T, newValue: T) -> Boolean): ReadWriteProperty<Any?, T> {
	return observable(initialValue) { _, oldValue, newValue -> callback(oldValue, newValue) }
}

/**创建一个可否决的委托属性，并通过检验新值判断是否否决。*/
inline fun <T> Delegates.validate(initialValue: T, crossinline predicate: (newValue: T) -> Boolean): ReadWriteProperty<Any?, T> {
	return vetoable(initialValue) { _, _, newValue -> predicate(newValue) }
}

/**创建一个可否决的委托属性，并通过比较新旧值判断是否否决。*/
inline fun <T> Delegates.contrast(initialValue: T, crossinline predicate: (oldValue: T, newValue: T) -> Boolean): ReadWriteProperty<Any?, T> {
	return vetoable(initialValue) { _, oldValue, newValue -> predicate(oldValue, newValue) }
}
