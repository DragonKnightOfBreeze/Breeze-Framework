package com.windea.breezeframework.core.types

import com.windea.breezeframework.core.domain.*

/**二元素元组。*/
typealias Tuple2<A, B> = Pair<A, B>

/**三元素元组。*/
typealias Tuple3<A, B, C> = Triple<A, B, C>

/**四元素元组。*/
typealias Tuple4<A, B, C, D> = Quadruple<A, B, C, D>

/**四五元素元组。*/
typealias Tuple5<A, B, C, D, E> = Quintuple<A, B, C, D, E>


/**单一类型的二元素元组。*/
typealias TypedTuple2<T> = Pair<T, T>

/**单一类型的三元素元组。*/
typealias TypedTuple3<T> = Triple<T, T, T>

/**单一类型的四元素元组。*/
typealias TypedTuple4<T> = Quadruple<T, T, T, T>

/**单一类型的五元素元组。*/
typealias TypedTuple5<T> = Quintuple<T, T, T, T, T>
