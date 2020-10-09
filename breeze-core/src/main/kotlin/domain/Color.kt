// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.extensions.*
import java.text.*

/**
 * 颜色。
 *
 * 表示rgb颜色。
 *
 * 可以解构：`val (r,g,b,a) = color`
 */
class Color {
	constructor(hexValue: Int) {
		val numbers = hexValue.toDigitNumberArray(256)
		this.r = numbers.getOrElse(2){0}
		this.g = numbers.getOrElse(1){0}
		this.b =  numbers.getOrElse(0){0}
		this.a = 255
	}

	constructor(r: Int, g: Int, b: Int) {
		this.r = r
		this.g = g
		this.b = b
		this.a = 255
	}

	constructor(r: Int, g: Int, b: Int, a: Int) {
		this.r = r
		this.g = g
		this.b = b
		this.a = a
	}

	val r: Int
	val g: Int
	val b: Int
	val a: Int

	val expression get() = "#${r.toHexString()}${g.toHexString()}${b.toHexString()}${if(a != 255) a.toHexString() else ""}"
	val rgbExpression get() = "rgb($r, $g, $b)"
	val rgbaExpression get() = "rgba($r, $g, $b, $a)"

	private fun Int.toHexString(): String {
		return this.toString(16).padStart(2,'0')
	}

	operator fun component1()  = r

	operator fun component2() = g

	operator fun component3() = b

	operator fun component4() = a

	override fun equals(other: Any?): Boolean {
		return other is Color && this.r == other.r && this.g == other.g && this.b == other.b && this.a == other.a
	}

	override fun hashCode(): Int {
		return expression.hashCode()
	}

	override fun toString(): String {
		return "Color($expression)"
	}
}
