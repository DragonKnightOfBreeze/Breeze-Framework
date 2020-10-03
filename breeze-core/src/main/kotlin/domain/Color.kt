// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.extensions.*

class Color {
	constructor(hexValue: Int) {
		val numbers = hexValue.toDigitNumberArray()
		this.r = numbers.getOrElse(0){0}
		this.g = numbers.getOrElse(1){0}
		this.b =  numbers.getOrElse(2){0}
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

	val expression get() = "#${r.toString(16)}${g.toString(16)}${b.toString(16)}${if(a != 255) a.toString(16) else ""}"
	val rgbExpression get() = "rgb($r, $g, $b)"
	val rgbaExpression get() = "rgba($r, $g, $b, $a)"

	override fun equals(other: Any?): Boolean {
		return other is Color && this.r == other.r && this.g == other.g && this.b == other.b && this.a == other.a
	}

	override fun hashCode(): Int {
		return expression.hashCode()
	}

	override fun toString(): String {
		return "Color $expression"
	}
}
