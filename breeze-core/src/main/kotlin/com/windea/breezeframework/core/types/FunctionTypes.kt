package com.windea.breezeframework.core.types

/**无参代码块。即，没有接收者类型，且没有返回值的函数类型。*/
typealias Block0 = () -> Unit

/**代码块。即，以一个泛型参数为接收者类型，且没有返回值的函数类型。*/
typealias Block<T> = T.() -> Unit

/**无参动作。即，没有参数类型，且没有返回值的函数类型。*/
typealias Action0 = () -> Unit

/**动作。即，以一个泛型参数为参数类型，且没有返回值的函数类型。*/
typealias Action<T> = (T) -> Unit

/**函数。即，以一个泛型参数为参数类型，以另一个泛型参数为返回值类型的函数类型。*/
typealias Func<T, R> = (T) -> R
