// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("FunctionalExtensions")

package com.windea.breezeframework.functional.extensions

import com.windea.breezeframework.core.types.*

//https://github.com/MarioAriasC/funKTionale

//region curried & uncurried
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

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		this(p1, p2, p3, p4, p5, p6, p7)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		fun(p8: P8) = this(p1, p2, p3, p4, p5, p6, p7, p8)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		fun(p8: P8) = fun(p9: P9) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		fun(p8: P8) = fun(p9: P9) = fun(p10: P10) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)

/**柯里化当前函数。即，`fun(a,b,c) -> fun(a)(b)(c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).curried() =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		fun(p8: P8) = fun(p9: P9) = fun(p10: P10) = fun(p11: P11) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)


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

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> (P7) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7) = this(p1)(p2)(p3)(p4)(p5)(p6)(p7)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> (P7) -> (P8) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8) = this(p1)(p2)(p3)(p4)(p5)(p6)(p7)(p8)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> (P7) -> (P8) -> (P9) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9) =
		this(p1)(p2)(p3)(p4)(p5)(p6)(p7)(p8)(p9)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> (P7) -> (P8) -> (P9) -> (P10) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10) =
		this(p1)(p2)(p3)(p4)(p5)(p6)(p7)(p8)(p9)(p10)

/**反柯里化当前函数。即，`fun(a)(b)(c) -> fun(a,b,c)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1) -> (P2) -> (P3) -> (P4) -> (P5) -> (P6) -> (P7) -> (P8) -> (P9) -> (P10) -> (P11) -> R).uncurried() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10, p11: P11) =
		this(p1)(p2)(p3)(p4)(p5)(p6)(p7)(p8)(p9)(p10)(p11)
//endregion

//region reversed
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

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).reversed() =
	fun(p7: P7, p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) = this(p1, p2, p3, p4, p5, p6, p7)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).reversed() =
	fun(p8: P8, p7: P7, p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) = this(p1, p2, p3, p4, p5, p6, p7, p8)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).reversed() =
	fun(p9: P9, p8: P8, p7: P7, p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, p9)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).reversed() =
	fun(p10: P10, p9: P9, p8: P8, p7: P7, p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10)

/**反转当前函数的参数。即，`fun(a,b,c) -> fun(c,b,a)`。*/
fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).reversed() =
	fun(p11: P11, p10: P10, p9: P9, p8: P8, p7: P7, p6: P6, p5: P5, p4: P4, p3: P3, p2: P2, p1: P1) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)
//endregion

//region tupled & untupled
/**绑定当前函数的参数为元组。即，`fun(a,b) -> fun((a,b))`。*/
fun <P1, P2, R> ((P1, P2) -> R).tupled() =
	fun(tuple: Tuple2<P1, P2>) = this(tuple.first, tuple.second)

/**从元组解绑当前函数的参数。即，`fun((a,b)) -> fun(a,b)`。*/
fun <P1, P2, R> ((Tuple2<P1, P2>) -> R).untupled() =
	fun(p1: P1, p2: P2) = this(Tuple2(p1, p2))

/**绑定当前函数的参数为元组。即，`fun(a,b,c) -> fun((a,b,c))`。*/
fun <P1, P2, P3, R> ((P1, P2, P3) -> R).tupled() =
	fun(tuple: Tuple3<P1, P2, P3>) = this(tuple.first, tuple.second, tuple.third)

/**从元组解绑当前函数的参数。即，`fun((a,b,c)) -> fun(a,b,c)`。*/
fun <P1, P2, P3, R> ((Tuple3<P1, P2, P3>) -> R).untupled() =
	fun(p1: P1, p2: P2, p3: P3) = this(Tuple3(p1, p2, p3))

/**绑定当前函数的参数为元组。即，`fun(a,b,c,d) -> fun((a,b,c,d))`。*/
fun <P1, P2, P3, P4, R> ((P1, P2, P3, P4) -> R).tupled() =
	fun(tuple: Tuple4<P1, P2, P3, P4>) = this(tuple.first, tuple.second, tuple.third, tuple.fourth)

/**从元组解绑当前函数的参数。即，`fun((a,b,c,d)) -> fun(a,b,c,d)`。*/
fun <P1, P2, P3, P4, R> ((Tuple4<P1, P2, P3, P4>) -> R).untupled() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4) = this(Tuple4(p1, p2, p3, p4))

/**绑定当前函数的参数为元组。即，`fun(a,b,c,d,e) -> fun((a,b,c,d,e))`。*/
fun <P1, P2, P3, P4, P5, R> ((P1, P2, P3, P4, P5) -> R).tupled() =
	fun(tuple: Tuple5<P1, P2, P3, P4, P5>) = this(tuple.first, tuple.second, tuple.third, tuple.fourth, tuple.fifth)

/**从元组解绑当前函数的参数。即，`fun((a,b,c,d,e)) -> fun(a,b,c,d,e)`。*/
fun <P1, P2, P3, P4, P5, R> ((Tuple5<P1, P2, P3, P4, P5>) -> R).untupled() =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5) = this(Tuple5(p1, p2, p3, p4, p5))
//endregion

//region partial & partialLast
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

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		this(param, p2, p3, p4, p5, p6, p7)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) =
		this(param, p2, p3, p4, p5, p6, p7, p8)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).partial(param: P1) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) = fun(p9: P9) =
		this(param, p2, p3, p4, p5, p6, p7, p8, p9)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).partial(
	param: P1,
) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) = fun(p9: P9) =
		fun(p10: P10) = this(param, p2, p3, p4, p5, p6, p7, p8, p9, p10)

/**传入第一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & a -> fun(b)(c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).partial(
	param: P1,
) =
	fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) = fun(p9: P9) =
		fun(p10: P10) = fun(p11: P11) = this(param, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)


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

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).partialLast(param: P7) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) =
		this(p1, p2, p3, p4, p5, p6, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).partialLast(param: P8) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) =
		this(p1, p2, p3, p4, p5, p6, p7, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).partialLast(param: P9) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).partialLast(
	param: P10,
) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) =
		fun(p9: P9) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, param)

/**传入最后一个参数，得到当前函数的偏函数。即，`fun(a,b,c) & c -> fun(a)(b)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).partialLast(
	param: P11,
) =
	fun(p1: P1) = fun(p2: P2) = fun(p3: P3) = fun(p4: P4) = fun(p5: P5) = fun(p6: P6) = fun(p7: P7) = fun(p8: P8) =
		fun(p9: P9) = fun(p10: P10) = this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, param)
//endregion

//region pipe & pipeLast
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

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7) = this(param, p2, p3, p4, p5, p6, p7)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8) = this(param, p2, p3, p4, p5, p6, p7, p8)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).pipe(param: P1) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9) = this(param, p2, p3, p4, p5, p6, p7, p8, p9)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).pipe(
	param: P1,
) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10) =
		this(param, p2, p3, p4, p5, p6, p7, p8, p9, p10)

/**传入第一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & a -> fun(b,c)`。*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).pipes(
	param: P1,
) =
	fun(p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10, p11: P11) =
		this(param, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)


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

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, P7, R> ((P1, P2, P3, P4, P5, P6, P7) -> R).pipeLast(param: P7) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6) = this(p1, p2, p3, p4, p5, p6, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, R> ((P1, P2, P3, P4, P5, P6, P7, P8) -> R).pipeLast(param: P8) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7) = this(p1, p2, p3, p4, p5, p6, p7, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9) -> R).pipeLast(param: P9) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8) = this(p1, p2, p3, p4, p5, p6, p7, p8, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10) -> R).pipeLast(param: P10) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, p9, param)

/**传入最后一个参数，得到当前函数的重写函数。即，`fun(a,b,c) & c -> fun(a,b)`*/
infix fun <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> ((P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11) -> R).pipeLast(param: P11) =
	fun(p1: P1, p2: P2, p3: P3, p4: P4, p5: P5, p6: P6, p7: P7, p8: P8, p9: P9, p10: P10) =
		this(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, param)
//endregion

//region compose
/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <P, I, R> ((P) -> I).compose(other: (I) -> R) = fun(p: P) = other(this(p))

/**组合当前函数和另一个函数。即，`fun(a,b) & fun(b,c) -> fun(a,c)`。*/
infix fun <I, R> (() -> I).compose(other: (I) -> R) = fun() = other(this())
//endregion

