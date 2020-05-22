package com.windea.breezeframework.core.types

/**Represents a code block with no receivers.*/
typealias Block0 = () -> Unit

/**Represents a code block with one receiver.*/
typealias Block<T> = T.() -> Unit

/**Represents a action with no arguments.*/
typealias Action0 = () -> Unit

/**Represents a action with one argument.*/
typealias Action<T> = (T) -> Unit
