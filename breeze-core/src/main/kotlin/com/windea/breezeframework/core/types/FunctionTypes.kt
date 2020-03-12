package com.windea.breezeframework.core.types

/**代码块。即，以一个泛型参数为接收者类型，且没有返回值的方法类型。*/
typealias Block<T> = T.() -> Unit

/**动作。即，以一个泛型参数为参数类型，且没有返回值的方法类型。*/
typealias Action<T> = (T) -> Unit
