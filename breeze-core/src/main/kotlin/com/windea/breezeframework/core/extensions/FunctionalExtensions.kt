package com.windea.breezeframework.core.extensions

/**柯里化当前函数。*/
fun <P1, P2, R> Function2<P1, P2, R>.curried() =
	fun(p1: P1) = fun(p2: P2) = this(p1, p2)

/**柯里化当前函数。*/
fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)

/**柯里化当前函数。*/
fun <P1, P2, P3, P4, R> Function4<P1, P2, P3, P4, R>.curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(p1, p2, p3, p4)

/**柯里化当前函数。*/
fun <P1, P2, P3, P4, P5, R> Function5<P1, P2, P3, P4, P5, R>.curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = this(p1, p2, p3, p4, p5)


/**得到当前函数的偏函数。*/
fun <P1, P2, R> Function2<P1, P2, R>.partial(p2: P2) =
	fun(p1: P1) = this(p1, p2)

/**得到当前函数的偏函数。*/
fun <P1, P2, P3, R> Function3<P1, P2, P3, R>.partial(p3: P3) =
	fun(p1: P1) = fun(p2: P2) = this(p1, p2, p3)

/**得到当前函数的偏函数。*/
fun <P1, P2, P3, P4, R> Function4<P1, P2, P3, P4, R>.partial(p4: P4) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3, p4)

/**得到当前函数的偏函数。*/
fun <P1, P2, P3, P4, P5, R> Function5<P1, P2, P3, P4, P5, R>.partial(p5: P5) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(p1, p2, p3, p4, p5)
