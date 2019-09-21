@file:Reference("[funKTionale](https://github.com/MarioAriasC/funKTionale)")

package com.windea.breezeframework.functional

import com.windea.breezeframework.core.annotations.marks.*

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

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = this(p1, p2, p3, p4, p5, p6)


/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, R> ((P1) -> (P2) -> R).uncurried() =
	fun(p1: P1, p2: P2) = this(p1)(p2)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, R> ((P1) -> (P2) -> (P3) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3) = this(p1)(p2)(p3)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, R> ((P1) -> (P2) -> (P3) -> (P4) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4) = this(p1)(p2)(p3)(p4)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5) = this(p1)(p2)(p3)(p4)(p5)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6) = this(p1)(p2)(p3)(p4)(p5)(p6)

///////////reversed

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, R> ((P1, P2) -> R).reversed() =
	fun(p2: P2, p1: P1) = this(p1, p2)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).reversed() =
	fun(p3: P3, p2: P2, p1: P1) = this(p1, p2, p3)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).reversed() =
	fun(p4: P4, p3: P3, p2: P2, p1: P1) = this(p1, p2, p3, p4)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).reversed() =
	fun(p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) = this(p1, p2, p3, p4, p5)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).reversed() =
	fun(p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) = this(p1, p2, p3, p4, p5, p6)

///////////paired & unpaired & tripled & untripled

/**绑定当前函数的参数。即，`fun(a,b,c) -> fun((a,b,c))`。*/
fun <P1, P2, R> ((P1, P2) -> R).paired() = fun(pair: Pair<P1, P2>) = this(pair.first, pair.second)

/**解绑当前函数的参数。即，`fun((a,b,c)) -> fun(a,b,c)`。*/
fun <P1, P2, R> ((Pair<P1, P2>) -> R).unpaired() = fun(p1: P1, p2: P2) = this(Pair(p1, p2))

/**绑定当前函数的参数。即，`fun(a,b,c) -> fun((a,b,c))`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).tripled() = fun(triple: Triple<P1, P2, P3>) = this(triple.first, triple.second, triple.third)

/**解绑当前函数的参数。即，`fun((a,b,c)) -> fun(a,b,c)`。*/
fun <P1, P2, P3, R> ((Triple<P1, P2, P3>) -> R).untripled() = fun(p1: P1, p2: P2, p3: P3) = this(Triple(p1, p2, p3))

//////////partial & partialLast

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, R> ((P1, P2) -> R).partial(param: P1) =
	fun(p2: P2) = this(param, p2)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = this(param, p2, p3)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(param, p2, p3, p4)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = this(param, p2, p3, p4, p5)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = this(param, p2, p3, p4, p5, p6)


/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, R> ((P1, P2) -> R).partialLast(param: P2) =
	fun(p1: P1) = this(p1, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partialLast(param: P3) =
	fun(p1: P1) = fun(p2: P2) = this(p1, p2, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).partialLast(param: P4) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = this(p1, p2, p3, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).partialLast(param: P5) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = this(p1, p2, p3, p4, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).partialLast(param: P6) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = this(p1, p2, p3, p4, p5, param)

///////////////pipe & pipeLast

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, R> ((P1, P2) -> R).pipe(param: P1) =
	fun(p2: P2) = this(param, p2)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, R> ((P1, P2, P3) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3) = this(param, p2, p3)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4) = this(param, p2, p3, p4)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5) = this(param, p2, p3, p4, p5)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6) = this(param, p2, p3, p4, p5, p6)


/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, R> ((P1, P2) -> R).pipeLast(param: P2) =
	fun(p1: P1) = this(p1, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, R> ((P1, P2, P3) -> R).pipeLast(param: P3) =
	fun(p1: P1, p2: P2) = this(p1, p2, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).pipeLast(param: P4) =
	fun(p1: P1, p2: P2, p3: P3) = this(p1, p2, p3, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).pipeLast(param: P5) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4) = this(p1, p2, p3, p4, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, R> ((P1, P2, P3, P4, P5, P6) -> R).pipeLast(param: P6) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5) = this(p1, p2, p3, p4, p5, param)

////////////compose

/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <P, I, R> ((P) -> I).compose(other: (I) -> R) = fun(p: P) = other(this(p))

/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <I, R> (() -> I).compose(other: (I) -> R) = fun() = other(this())
