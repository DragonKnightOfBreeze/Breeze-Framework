/***********************************************************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 *
 *                                     ...]]]]]]..
 *                             ...,]OOOOOOOOOOOOOOO].
 *                            ./]/[[[[\OOOOOOOOOO@@@@O].
 *                                        .OOOOOO@@@@@@@@].
 *                                         =OOOOO@@@@@@@@@@@`.
 *                                          =@@OO@@@@@@@@@@@@@\.
 *                                          .\@@@@@@@@@@@@@@@@@@\.
 *                                   .. .    .O@@@@@@@@@@@@@@@@@@@@`.
 *                                    .``=\.  ,@@@@@@@^=@^O@@@@@@@@@@\.              .`  ..,]]]]]].
 *                      ... .....       .=OO` .O@@@@@^=^..O@@@@@@@@@@@.           .,@@]@@@@@@@@@@@.
 *               ........*[]],\OOOOO\]..  .\O^ O@@@@^O@`..@@@@@@@@@@@@@].      ....OO@@@@@@@@@@@@^
 *          ...*.......**[oOOOOOOOOO@@@@@`. =@`=@@@`=@@`.=@@@@@@@@@@@@@@\......./^.,@@@@@@@@@@@@^
 *                .........[\OOO@@@@@@@@@@@`]O@OO@`.=@@.`=@@@@@@@@@@@@@@@@^...,@`.=@@@@@@@@@@@@`
 *                            ......[[\OO@@@O\O@@`...@@@.@@@@@@@@O@@@@@@@@\..//..O@@@@@@O]`.\/.
 *                    ..,]/OOOO@@@@@@@@@@@^,`,`O@....@@\=@@OO@@@@@@@@@@@@@@]@^./@@@@@@@/`..OO@`
 *                 ..*...,]OO@@@@@@@@@@@O`.,,/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/O@@@@@@@@@@@@@@@@@.
 *               ....*[*=o/\OO@@@@@@@@@O@@@@@@@@@@@`.=[\O@@@@@@@@@@@@@@@@@@@@@@@@@@@@O[=@@@@@@@O.
 *             ...*....*]/OOO@@@@@[.,/@@@@@@@@@@@@^...........[OOO@@@@@@@@@@@@@@@/.    .O@@@@@@@^
 *           ........**`,OO/oO/. ,/@@@@@@@@@@@@O`***..........**......*....*.=O[.        .=@@@@/\^
 *           *..   ,`.,`**,.. .,@@@@@@@@@@@@@@@^.**.**.....*`...*...../`.........         .\/[..=^.
 *           .    ..*\*. .. ./O@@@@@@@@@@@@@@@O^/....*....=@@@[[^*...=@OO..*... ..        =@@...@@^
 * \.            .*,.     .,O@@@@@@@@@@@@@@@O@O/^*...*.....\]*....**./=.o`*^.... ..      ,@@@@\=@@@`
 *  .`.          ..      ,OOO@@@@@@@OOO/[[[[..*........................**==.* ..         /@@@@@@@@@@.
 *    .,.               ,/`..            ... .........*................*.**.. ..       ./@@@@@@@@@@@.
 *       ,`            ./.               ..  .........*...=*`......../O...... ..     .O@@@@@@@@@@@@^
 *         ,..    ..   =.                ..   ...`...**...,**,**..][=@@`*=@@@@]]]\.]@@@@@@@@@@@@@@@^
 *    ..     .*.  ..   =.                ...,/@@@@@@@O^`..=*****.,\\*./ooO@@@@@@@@@@@@@@@@@@@@@/[...
 *   ..        .\.......`.                 .,O@@@@@@@@\O`.*****...*^`*O\OO@@@@@@@@@@@@@@@@@@@@/.
 *  ...    ..... .\.....\^..                 ./@@@@@@@O@@**......`...`=@O@@@@@@@@@@@@@@@@@@@`
 *   ...  ..       ....  ....          ....*./@@@@@@@@@@@O\.....*.....*@@@@@@@@@@@@@@@@@@@/.
 *   .... ..        ...`  ................*.=@@@@@@@@@@@@@@@\...`.....**@@@@@@@@@@@@@@@@@@@@`
 *    .......         ..,`.        ....*....[[O@@@@@@@@@@@@@@@\@@@\*.....\@@@@@@@@@@@@@@@@@@@@\.
 *     .......          ..,................./@/=@@@@@@@@@@@@@@@@@@@@@]...*\@@@@@@@@@@@@@@@@@@@@@\.
 *       ........           .*.   .........=@^*\/=@@@@@@@@@@@@@@@@@@@@@@\./@@@@@@@@@@@@@@@@@@@@@@@\.
 *      .. ........           .\.....**]/]/\]o\]]@@@@@@@@@@@@@@@@@@OOOO@@@@@@^/@@@@@@@@@@@@@@@@@@@@@`                 ..
 *       ...............    ......,/@@@@O@@Oo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=@@@O*[@@@@@@@@@@@@@@@@@@@@.               =O`
 *            .......*..........  .[@@O@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O@\..\@@@@@@@@@@@@@@@@@@@@.              /\]
 *   =`                    ,]`    ,/@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@o@@OO\.,@@@@@@@@@@@@@@@@@@@@\.          ./O@O.
 *   =.       .O`        ,@@@@\.,@@@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@o@@OO@O.=@@@@@@@@@@@@@@@@@@@@\.        ,O@@^=.
 * . .\.   =OO@.O.     ,@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O/O@OOOOO`\@@@@@@@@@@@@@@@@@@@@O.     ./@@/=.=.
 * `  .,`. .@,\.O ....OOO@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O=..[\OOOOO`O@@@^ .,\@@@@@O@@@@@/\\]`./@@`. =^^
 * \`    ,\\/@@@`  ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/.=^...\oOOOO\@@@^          ./@@[`=@OO@@/.   .O.
 *  .\].     ./@@]]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`.......***OOOO@@@^        .,@@`  =/@@@/.    .OO.
 *      .[[[[`..\@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`..........**=OO@O@@`        /@@\]]/@@@@^O..  .OOO^
 *              .,O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\*..........*,/@@@O@^]].    ./@@@@@OO@@@@o@@`  =OOO^
 *               .,O@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@Oo*......,O@@@OO^.\@@@].,@@@@@@@O@@@\/O=`  .=OO`.
 * .......`.   .\@@@@@@@@@@@@@@@@@@@@@@@@@/` =@@@@@@@@@@@@@@@@@@@@@@@@@@@@OO\]/@@@OOOO@@@@@@@@@\@@@@@@@@@@@`.    ,OO`.
 *   ,O`. ,O\].. ,@@@@@@@@@@@@@@@@@@@@@/.    ./@@@@@@@@@@@@@\@@OO@***O@@`[\@@O@@@@@@@O/`*@@@@`/@@@@@@@@@@^=O.  ..=O.
 *     .\OOO@O@@@O]`\@@@@@@@@@@@@@@@O\O\]..  .@@@@@@@@@@@@@@\OOo`OO@@@`..,O@@@@//\..[*....O@@@@@@@@@@@@@^./@.   ...
 *           ..[[O@@@@@@\]`. .....,@@@\/@@@@O/@@@@@@@@@.,@@\O@@\=O@@[.../@@@@[.=^.\.......==@@@@@@@@@^.  ,@O.
 *    .,]OO]`..     ..[O@@@@@@@O].. ./@@@@@@@@@@@@@@@@@^O@@@@OOOOoO`*,O@@@O`...=\..,\...,/./@@@@@@@@/.  .@`
 *  .O@@@/\O`...  ..,`.    .,\@@@@@@@@@@OOO@@@@@@@@@@@@\@O@@@@@OoOO@@@@@O......@@`..O..../@@@@@@@@O^.  ,O.
 *  .     .....,@@@O`            .[@@@@@@OOO@@@@@@@@@@@@\@@@@@@@@@@@@/[*......=@@@`...]@@@@O@@@@@/.  .=/.
 *          ..=@@@\.               .O@@OoooO@@@@@@@@@@@@@@@@OO\@OO**..........=@@@@@@@@@@`./@@@@\.  ./^
 *            .*O@@@\.              ....OOO@@@@@@@@@@@@@@@@@OO@@O@\]]]]]]]/@@@@@@@@@/[..../^,@@O\^ =@`
 *            ... .[\O^.          ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@/,\@@@@@@@@@@@@@@]....../O`......,@O.
 *            ....                      ...\@@@@@@@@@@@@@@@@@@@`......[[`.@@@@@@@@/\OO[`......../@^
 *             ...                           .[\O^,@@@@@@@O@@@@..........=@@@@@@@@@`.........=@@@@].
 *             ...                            ...,O@OO@@@OOOO@@..........=@@@@@@@@@@^......,@@@@@@@^
 *             ..*.                           .,/OOO@@@@@OOOO@@`.........@@@@@@@@@@@@@@@@@@@@@@@@@@@\`..
 *             .....                          .OOOO@@@@OO@@@@@@@\......,@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@O\]..
 *              ......                      ./OOOOOOO@@OOO@@@@@@@@@@@@@@@@@@@^.    ./@@@@@@@@@@@@@@@@@@@@@@@@@@O]`..
 *               ......                  .]OOOOOO[./@OOOOO@@@@@@@@@@@@@@@@@/.  .,/@@/`O@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@\]
 *               .........               .......*.,OOOOOO@@@@@@@@@@@@@@@@@@^.]@@@/.   .@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                ...........                 .../OOOOO//@@@@@@@@@@@@@@@@O@@@/`.       =@O@@@@@@@@@@@@@...[\@@@@@@@@@@@O
 *                 .........................*`.]OOOO/`.=@@@@@@@@@@@@@@@@@/\O.          .O/@@@@@@@@@@@O^         .[\O@@@@
 *                   .....................*.,[[[......=@@@`.\@@@@@@@@@@@@^.^            ,^,@@@@@@@@@@.                ..
 *                    .................*.............=@@@`./@@@@@@@@@@@@^ ..             .. \@@@@@@@@^`.         ...*[`.
 *                     ............................*,@@@^/@@@@@@@@@@@@@@.                    =@@@@@@@@..\...*[..
 *                      ...........................=@@@O@@@@@@@@@@@@@@@^                     .@@@@@@@@^. .\.
 *                        ..........................\/@@@@@@@\@@@@@@@@O.                ....*[,@@@@@@@^    .*.
 *                          ........................O@@@@@@/..@@@@@@@@^         ...**[.       .@@@@@@@@.     .,`.
 *                            ..................../@@@@@@@@\..@@@@@@@@^ ...*,[..               =@@@@@@@.        ,`.
 *                              ................/@@@@@@@`@@@^ =@@@@@@@^                        .\@@@@@@^          ,*.
 *                                  ..........,O@@@@@@` ..[O[`=@@@@@@@^                         ,@@@@@@^            .\.
 *                                          ,OO@@@@@/`.       .@@@@@@@O.                         @@@@@@\.             .\
 *                                      ..,/O@@@@@/.          .O@@@@@@@.                         .@@@@@^
 *                             ....*[`. ./OOOO@@/.             =@@@@@@O.                          =@@@@@.
 *                     ....*[..       .,OOOOOOO`               .@@@@@@@.                          =@@@@@^
 *
 * Breeze is blowing ...
 **********************************************************************************************************************/

@file:JvmName("MathExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import kotlin.math.*

/**进行乘方运算。*/
fun Int.pow(n: Int): Float = this.toFloat().pow(n)

/**进行乘方运算。*/
fun Int.pow(x: Float): Float = this.toFloat().pow(x)

/**进行乘方运算。*/
fun Int.pow(x: Double): Double = this.toDouble().pow(x)

/**进行乘方运算。*/
fun Float.pow(x: Double): Float = this.pow(x.toFloat())

/**进行乘方运算。*/
fun Double.pow(x: Float): Double = this.pow(x.toDouble())


/**进行正乘方运算。*/
fun Int.positivePow(x: Int): Int {
	require(x >= 0) { "Number 'x' for positive pow operation must be non-negative, but was $x." }
	if(x == 0) return 1
	var temp = this
	for(i in 1 until x) temp *= this
	return temp
}

/**进行正乘方运算。*/
fun Long.positivePow(x: Int): Long {
	require(x >= 0) { "Number 'x' for positive pow operation must be non-negative, but was $x." }
	if(x == 0) return 1
	var temp = this
	for(i in 1 until x) temp *= this
	return temp
}


/**精确到基于位数的不小于0的精确度，返回向下取整后的结果。用0表示个位，用较大数表示较高位。*/
fun ceil(x: Int, precision: Int): Int {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { ceil(x / it.toFloat()).toInt() * it }
		else -> throw IllegalArgumentException("Precision for int ceil operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的不小于0的精确度，返回向下取整后的结果。用0表示个位，用较大数表示较高位。*/
fun ceil(x: Long, precision: Int): Long {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { ceil(x / it.toFloat()).toLong() * it }
		else -> throw IllegalArgumentException("Precision for long ceil operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的精确度，返回向下取整后的结果。用0表示个位，用较大数表示较高位。*/
fun ceil(x: Float, precision: Int): Float {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { ceil(x / it) * it }
		else -> 10.positivePow(-precision).let { ceil(x * it) / it }
	}
}

/**精确到基于位数的精确度，返回向下取整后的结果。用0表示个位，用较大数表示较高位。*/
fun ceil(x: Double, precision: Int): Double {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { ceil(x / it) * it }
		else -> 10.positivePow(-precision).let { ceil(x * it) / it }
	}
}


/**精确到基于位数的不小于0的精确度，返回向上取整后的结果。用0表示个位，用较大数表示较高位。*/
fun floor(x: Int, precision: Int): Int {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { floor(x / it.toFloat()).toInt() * it }
		else -> throw IllegalArgumentException("Precision for int floor operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的不小于0的精确度，返回向上取整后的结果。用0表示个位，用较大数表示较高位。*/
fun floor(x: Long, precision: Int): Long {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { floor(x / it.toFloat()).toLong() * it }
		else -> throw IllegalArgumentException("Precision for long floor operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的精确度，返回向上取整后的结果。用0表示个位，用较大数表示较高位。*/
fun floor(x: Float, precision: Int): Float {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { floor(x / it) * it }
		else -> 10.positivePow(-precision).let { floor(x * it) / it }
	}
}

/**精确到基于位数的精确度，返回向上取整后的结果。用0表示个位，用较大数表示较高位。*/
fun floor(x: Double, precision: Int): Double {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { floor(x / it) * it }
		else -> 10.positivePow(-precision).let { floor(x * it) / it }
	}
}


/**精确到基于位数的不小于0的精确度，返回舍去小数部分后的结果。用0表示个位，用较大数表示较高位。*/
fun truncate(x: Int, precision: Int): Int {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { truncate(x / it.toFloat()).toInt() * it }
		else -> throw IllegalArgumentException("Precision for int truncate operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的不小于0的精确度，返回舍去小数部分后的结果。用0表示个位，用较大数表示较高位。*/
fun truncate(x: Long, precision: Int): Long {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { truncate(x / it.toFloat()).toLong() * it }
		else -> throw IllegalArgumentException("Precision for long truncate operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的精确度，返回舍去小数部分后的结果。用0表示个位，用较大数表示较高位。*/
fun truncate(x: Float, precision: Int): Float {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { truncate(x / it) * it }
		else -> 10.positivePow(-precision).let { truncate(x * it) / it }
	}
}

/**精确到基于位数的精确度，返回舍去小数部分后的结果。用0表示个位，用较大数表示较高位。*/
fun truncate(x: Double, precision: Int): Double {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { truncate(x / it) * it }
		else -> 10.positivePow(-precision).let { truncate(x * it) / it }
	}
}


/**精确到基于位数的不小于0的精确度，返回四舍五入后的结果。用0表示个位，用较大数表示较高位。*/
fun round(x: Int, precision: Int): Int {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { round(x / it.toFloat()).toInt() * it }
		else -> throw IllegalArgumentException("Precision for int round operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的不小于0的精确度，返回四舍五入后的结果。用0表示个位，用较大数表示较高位。*/
fun round(x: Long, precision: Int): Long {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { round(x / it.toFloat()).toLong() * it }
		else -> throw IllegalArgumentException("Precision for long round operation must be non-negative, but was $precision.")
	}
}

/**精确到基于位数的精确度，返回四舍五入后的结果。用0表示个位，用较大数表示较高位。*/
fun round(x: Float, precision: Int): Float {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { round(x / it) * it }
		else -> 10.positivePow(-precision).let { round(x * it) / it }
	}
}

/**精确到基于位数的精确度，返回四舍五入后的结果。用0表示个位，用较大数表示较高位。*/
fun round(x: Double, precision: Int): Double {
	return when {
		precision == 0 -> x
		precision > 0 -> 10.positivePow(precision).let { round(x / it) * it }
		else -> 10.positivePow(-precision).let { round(x * it) / it }
	}
}
