// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

//参考：
//java.awt.Color
//javafx.scene.paint.Color

/**
 * 颜色。
 *
 * 颜色可以解构：`val (r,g,b,a) = color`
 */
@ComponentMarker
class Color {
	constructor(hexValue: Int) {
		val numbers = hexValue.toDigitNumberArray(256)
		this.r = numbers.getOrElse(2) { 0 }
		this.g = numbers.getOrElse(1) { 0 }
		this.b = numbers.getOrElse(0) { 0 }
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
		return this.toString(16).padStart(2, '0')
	}

	operator fun component1() = r

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

	companion object {
		private val colorRegistry = mutableMapOf<String, Color>()

		init {
			registerCssColors()
		}

		/**
		 * 注册颜色。
		 */
		@JvmStatic fun register(name: String, color: Color) {
			colorRegistry[name] = color
		}

		/**
		 * 解析颜色。如果解析失败，则抛出异常。
		 */
		@JvmStatic fun parse(expression: String): Color {
			return try {
				when {
					expression.startsWith("#") -> {
						val string = expression.drop(1)
						val length = string.length
						if(length != 3 && length != 4 && length != 6 && length != 8) throwException(expression)
						val fullString = if(length < 6) string.repeatOrdinal(2) else string
						val r = fullString.substring(0, 2).toInt(16)
						val g = fullString.substring(2, 4).toInt(16)
						val b = fullString.substring(4, 6).toInt(16)
						val a = if(length == 4 || length == 8) fullString.substring(6, 8).toInt(16) else 255
						Color(r, g, b, a)
					}
					expression.startsWith("rgba") -> {
						val string = expression.substringIn('(', ')')
						val numbers = string.split(' ', ',').filterNotEmpty().map { it.toInt() }
						if(numbers.size != 4) throwException(expression)
						val (r, g, b, a) = numbers
						Color(r, g, b, a)
					}
					expression.startsWith("rgb") -> {
						val string = expression.substringIn('(', ')')
						val numbers = string.split(' ', ',').filterNotEmpty().map { it.toInt() }
						if(numbers.size != 3) throwException(expression)
						val (r, g, b) = numbers
						Color(r, g, b)
					}
					else -> colorRegistry[optimizeName(expression)] ?: throwException(expression)
				}
			} catch(e: Exception) {
				throwException(expression, e)
			}
		}

		/**
		 * 解析颜色。如果解析失败，这返回null。
		 */
		@JvmStatic fun parseOrNull(expression: String): Color? {
			return when {
				expression.startsWith("#") -> {
					val string = expression.drop(1)
					val length = string.length
					if(length != 3 && length != 4 && length != 6 && length != 8) return null
					val fullString = if(length < 6) string.repeatOrdinal(2) else string
					val r = fullString.substring(0, 2).toIntOrNull(16) ?: return null
					val g = fullString.substring(2, 4).toIntOrNull(16) ?: return null
					val b = fullString.substring(4, 6).toIntOrNull(16) ?: return null
					val a = if(length == 4 || length == 8) fullString.substring(6, 8).toIntOrNull(16) ?: 255 else 255
					Color(r, g, b, a)
				}
				expression.startsWith("rgba") -> {
					val string = expression.substringIn('(', ')')
					val numbers = string.split(' ', ',').filterNotEmpty().map { it.toIntOrNull() ?: return null }
					if(numbers.size != 4) return null
					val (r, g, b, a) = numbers
					Color(r, g, b, a)
				}
				expression.startsWith("rgb") -> {
					val string = expression.substringIn('(', ')')
					val numbers = string.split(' ', ',').filterNotEmpty().map { it.toIntOrNull() ?: return null }
					if(numbers.size != 3) return null
					val (r, g, b) = numbers
					Color(r, g, b)
				}
				else -> colorRegistry[optimizeName(expression)]
			}
		}

		private fun throwException(expression: String, cause: Throwable? = null): Nothing {
			throw IllegalArgumentException("Cannot parse '$expression' to color.", cause)
		}

		private fun optimizeName(expression: String): String {
			return expression.split(' ', '-', '_').filterNotEmpty().joinToString("").toLowerCase()
		}

		//148种
		//language=css prefix=*{color: suffix=;}

		private fun registerCssColors() {
			register("aliceblue", Color(0xf0f8ff))
			register("antiquewhite", Color(0xfaebd7))
			register("aqua", Color(0x00ffff))
			register("aquamarine", Color(0x7fffd4))
			register("azure", Color(0xf0ffff))
			register("beige", Color(0xf5f5dc))
			register("bisque", Color(0xffe4c4))
			register("black", Color(0x000000))
			register("blanchedalmond", Color(0xffebcd))
			register("blue", Color(0x0000ff))
			register("blueviolet", Color(0x8a2be2))
			register("brown", Color(0xa52a2a))
			register("burlywood", Color(0xdeb887))
			register("cadetblue", Color(0x5f9ea0))
			register("chartreuse", Color(0x7fff00))
			register("chocolate", Color(0xd2691e))
			register("coral", Color(0xff7f50))
			register("cornflowerblue", Color(0x6495ed))
			register("cornsilk", Color(0xfff8dc))
			register("crimson", Color(0xdc143c))
			register("cyan", Color(0x00ffff))
			register("darkblue", Color(0x00008b))
			register("darkcyan", Color(0x008b8b))
			register("darkgoldenrod", Color(0xb8860b))
			register("darkgray", Color(0xa9a9a9))
			register("darkgreen", Color(0x006400))
			register("darkgrey", Color(0xa9a9a9))
			register("darkkhaki", Color(0xbdb76b))
			register("darkmagenta", Color(0x8b008b))
			register("darkolivegreen", Color(0x556b2f))
			register("darkorange", Color(0xff8c00))
			register("darkorchid", Color(0x9932cc))
			register("darkred", Color(0x8b0000))
			register("darksalmon", Color(0xe9967a))
			register("darkseagreen", Color(0x8fbc8f))
			register("darkslateblue", Color(0x483d8b))
			register("darkslategray", Color(0x2f4f4f))
			register("darkslategrey", Color(0x2f4f4f))
			register("darkturquoise", Color(0x00ced1))
			register("darkviolet", Color(0x9400d3))
			register("deeppink", Color(0xff1493))
			register("deepskyblue", Color(0x00bfff))
			register("dimgray", Color(0x696969))
			register("dimgrey", Color(0x696969))
			register("dodgerblue", Color(0x1e90ff))
			register("firebrick", Color(0xb22222))
			register("floralwhite", Color(0xfffaf0))
			register("forestgreen", Color(0x228b22))
			register("fuchsia", Color(0xff00ff))
			register("gainsboro", Color(0xdcdcdc))
			register("ghostwhite", Color(0xf8f8ff))
			register("gold", Color(0xffd700))
			register("goldenrod", Color(0xdaa520))
			register("gray", Color(0x808080))
			register("green", Color(0x008000))
			register("greenyellow", Color(0xadff2f))
			register("grey", Color(0x808080))
			register("honeydew", Color(0xf0fff0))
			register("hotpink", Color(0xff69b4))
			register("indianred", Color(0xcd5c5c))
			register("indigo", Color(0x4b0082))
			register("ivory", Color(0xfffff0))
			register("khaki", Color(0xf0e68c))
			register("lavender", Color(0xe6e6fa))
			register("lavenderblush", Color(0xfff0f5))
			register("lawngreen", Color(0x7cfc00))
			register("lemonchiffon", Color(0xfffacd))
			register("lightblue", Color(0xadd8e6))
			register("lightcoral", Color(0xf08080))
			register("lightcyan", Color(0xe0ffff))
			register("lightgoldenrodyellow", Color(0xfafad2))
			register("lightgray", Color(0xd3d3d3))
			register("lightgreen", Color(0x90ee90))
			register("lightgrey", Color(0xd3d3d3))
			register("lightpink", Color(0xffb6c1))
			register("lightsalmon", Color(0xffa07a))
			register("lightseagreen", Color(0x20b2aa))
			register("lightskyblue", Color(0x87cefa))
			register("lightslategray", Color(0x778899))
			register("lightslategrey", Color(0x778899))
			register("lightsteelblue", Color(0xb0c4de))
			register("lightyellow", Color(0xffffe0))
			register("lime", Color(0x00ff00))
			register("limegreen", Color(0x32cd32))
			register("linen", Color(0xfaf0e6))
			register("magenta", Color(0xff00ff))
			register("maroon", Color(0x800000))
			register("mediumaquamarine", Color(0x66cdaa))
			register("mediumblue", Color(0x0000cd))
			register("mediumorchid", Color(0xba55d3))
			register("mediumpurple", Color(0x9370db))
			register("mediumseagreen", Color(0x3cb371))
			register("mediumslateblue", Color(0x7b68ee))
			register("mediumspringgreen", Color(0x00fa9a))
			register("mediumturquoise", Color(0x48d1cc))
			register("mediumvioletred", Color(0xc71585))
			register("midnightblue", Color(0x191970))
			register("mintcream", Color(0xf5fffa))
			register("mistyrose", Color(0xffe4e1))
			register("moccasin", Color(0xffe4b5))
			register("navajowhite", Color(0xffdead))
			register("navy", Color(0x000080))
			register("oldlace", Color(0xfdf5e6))
			register("olive", Color(0x808000))
			register("olivedrab", Color(0x6b8e23))
			register("orange", Color(0xffa500))
			register("orangered", Color(0xff4500))
			register("orchid", Color(0xda70d6))
			register("palegoldenrod", Color(0xeee8aa))
			register("palegreen", Color(0x98fb98))
			register("paleturquoise", Color(0xafeeee))
			register("palevioletred", Color(0xdb7093))
			register("papayawhip", Color(0xffefd5))
			register("peachpuff", Color(0xffdab9))
			register("peru", Color(0xcd853f))
			register("pink", Color(0xffc0cb))
			register("plum", Color(0xdda0dd))
			register("powderblue", Color(0xb0e0e6))
			register("purple", Color(0x800080))
			register("rebeccapurple", Color(0x663399))
			register("red", Color(0xff0000))
			register("rosybrown", Color(0xbc8f8f))
			register("royalblue", Color(0x4169e1))
			register("saddlebrown", Color(0x8b4513))
			register("salmon", Color(0xfa8072))
			register("sandybrown", Color(0xf4a460))
			register("seagreen", Color(0x2e8b57))
			register("seashell", Color(0xfff5ee))
			register("sienna", Color(0xa0522d))
			register("silver", Color(0xc0c0c0))
			register("skyblue", Color(0x87ceeb))
			register("slateblue", Color(0x6a5acd))
			register("slategray", Color(0x708090))
			register("slategrey", Color(0x708090))
			register("snow", Color(0xfffafa))
			register("springgreen", Color(0x00ff7f))
			register("steelblue", Color(0x4682b4))
			register("tan", Color(0xd2b48c))
			register("teal", Color(0x008080))
			register("thistle", Color(0xd8bfd8))
			register("tomato", Color(0xff6347))
			register("turquoise", Color(0x40e0d0))
			register("violet", Color(0xee82ee))
			register("wheat", Color(0xf5deb3))
			register("white", Color(0xffffff))
			register("whitesmoke", Color(0xf5f5f5))
			register("yellow", Color(0xffff00))
			register("yellowgreen", Color(0x9acd32))
		}
	}
}
