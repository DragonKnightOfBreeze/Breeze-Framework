@file:Reference("[funKTionale](https://github.com/MarioAriasC/funKTionale)")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.internal.*

//////////curried & uncurried

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, R> ((P1, P2) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = this(p1, p2)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(p1, p2, p3, p4)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = this(p1, p2, p3, p4, p5)

///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = this(p1, p2, p3, p4, p5, p6)
//
///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
//		this(p1, p2, p3, p4, p5, p6, p7)
//
///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
//		fun(p8: P8) = this(p1, p2, p3, p4, p5, p6, p7, p8)
//
///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
//		fun(p8: P8) = fun(p9: P9) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9)
//
///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
//		fun(p8: P8) = fun(p9: P9) = fun(p10: P10) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)
//
///**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
//fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).curried() =
//	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
//		fun(p8: P8) = fun(p9: P9) = fun(p10: P10) = fun(p11: P11) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)

///////////reversed

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, R> ((P1, P2) -> R).reversed() = fun(p2: P2, p1: P1) = this(p1, p2)

//////////partial

/**得到当前函数的偏函数。即，`fun(a,b,c) -> fun(a)(b)`。*/
fun <P1, P2, R> ((P1, P2) -> R).partial(p2: P2) =
	fun(p1: P1) = this(p1, p2)

/**得到当前函数的偏函数。即，`fun(a,b,c) -> fun(a)(b)`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial(p3: P3) =
	fun(p1: P1) = fun(p2: P2) = this(p1, p2, p3)

/**得到当前函数的偏函数。即，`fun(a,b,c) -> fun(a)(b)`。*/
fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partial(p4: P4) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3, p4)

/**得到当前函数的偏函数。即，`fun(a,b,c) -> fun(a)(b)`。*/
fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).partial(p5: P5) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(p1, p2, p3, p4, p5)

///////////paired & unpaired & tripled & untripled

/**绑定当前函数的参数。即，`fun(a,b,c) -> fun((a,b,c))`。*/
fun <P1, P2, R> ((P1, P2) -> R).paired() = fun(pair: Pair<P1, P2>) = this(pair.first, pair.second)

/**解绑当前函数的参数。即，`fun((a,b,c)) -> fun(a,b,c)`。*/
fun <P1, P2, R> ((Pair<P1, P2>) -> R).unpaired() = fun(p1: P1, p2: P2) = this(p1 to p2)

/**绑定当前函数的参数。即，`fun(a,b,c) -> fun((a,b,c))`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).tripled() = fun(triple: Triple<P1, P2, P3>) = this(triple.first, triple.second, triple.third)

/**解绑当前函数的参数。即，`fun((a,b,c)) -> fun(a,b,c)`。*/
fun <P1, P2, P3, R> ((Triple<P1, P2, P3>) -> R).untripled() = fun(p1: P1, p2: P2, p3: P3) = this(p1 to p2 and p3)

///////////////pipe

/**将当前项作为首个参数传入另一个函数。即，`a & fun(a,b,c) -> fun(b,c)`。*/
infix fun <P1, R> P1.pipe(function: (P1) -> R) = function(this)

/**将当前项作为首个参数传入另一个函数。即，`a & fun(a,b,c) -> fun(b,c)`。*/
infix fun <P1, P2, R> P1.pipe(function: (P1, P2) -> R) = fun(p2: P2) = function(this, p2)

////////////compose

/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <P, I, R> ((P) -> I).compose(other: (I) -> R) = fun(p: P) = other(this(p))

/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <I, R> (() -> I).compose(other: (I) -> R) = fun() = other(this())
