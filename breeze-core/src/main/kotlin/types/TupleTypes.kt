package com.windea.breezeframework.core.types

import com.windea.breezeframework.core.domain.*

/**Represents a generic tuple of two values. */
typealias Tuple2<A, B> = Pair<A, B>

/**Represents a generic tuple of three values.*/
typealias Tuple3<A, B, C> = Triple<A, B, C>

/**Represents a generic tuple of four values.*/
typealias Tuple4<A, B, C, D> = Quadruple<A, B, C, D>

/**Represents a generic tuple of five values.*/
typealias Tuple5<A, B, C, D, E> = Quintuple<A, B, C, D, E>


/**Represents a typed tuple of two values.*/
typealias TypedTuple2<T> = Pair<T, T>

/**Represents a typed tuple of three values.*/
typealias TypedTuple3<T> = Triple<T, T, T>

/**Represents a typed tuple of four values.*/
typealias TypedTuple4<T> = Quadruple<T, T, T, T>

/**Represents a typed tuple of five values.*/
typealias TypedTuple5<T> = Quintuple<T, T, T, T, T>



