package com.windea.breezeframework.core.extensions

/**柯里化当前函数。*/
fun <P1, P2, R> Function2<P1, P2, R>.curried() = fun(p1: P1) = fun(p2: P2) = this(p1, p2)

/**得到当前函数的偏函数。*/
fun <P1, P2, R> Function2<P1, P2, R>.partial(p2: P2) = fun(p1: P1) = this(p1, p2)
