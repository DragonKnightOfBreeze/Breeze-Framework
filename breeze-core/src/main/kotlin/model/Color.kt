// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.extension.*

/**
 * 颜色。
 *
 * 颜色可以解构：`val (r,g,b,a) = color`
 */
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
		return Integer.toHexString(this).padStart(2, '0')
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
		private val colors = mutableMapOf<String, Color>()

		init {
			addCssColors()
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
					else -> colors[optimizeName(expression)] ?: throwException(expression)
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
				else -> colors[optimizeName(expression)]
			}
		}

		private fun throwException(expression: String, cause: Throwable? = null): Nothing {
			throw IllegalArgumentException("Cannot parse '$expression' to color.", cause)
		}

		private fun optimizeName(expression: String): String {
			return expression.split(' ', '-', '_').filterNotEmpty().joinToString("").toLowerCase()
		}

		//148种

		private fun addCssColors() {
			colors["aliceblue"] = Color(0xf0f8ff)
			colors["antiquewhite"] = Color(0xfaebd7)
			colors["aqua"] = Color(0x00ffff)
			colors["aquamarine"] = Color(0x7fffd4)
			colors["azure"] = Color(0xf0ffff)
			colors["beige"] = Color(0xf5f5dc)
			colors["bisque"] = Color(0xffe4c4)
			colors["black"] = Color(0x000000)
			colors["blanchedalmond"] = Color(0xffebcd)
			colors["blue"] = Color(0x0000ff)
			colors["blueviolet"] = Color(0x8a2be2)
			colors["brown"] = Color(0xa52a2a)
			colors["burlywood"] = Color(0xdeb887)
			colors["cadetblue"] = Color(0x5f9ea0)
			colors["chartreuse"] = Color(0x7fff00)
			colors["chocolate"] = Color(0xd2691e)
			colors["coral"] = Color(0xff7f50)
			colors["cornflowerblue"] = Color(0x6495ed)
			colors["cornsilk"] = Color(0xfff8dc)
			colors["crimson"] = Color(0xdc143c)
			colors["cyan"] = Color(0x00ffff)
			colors["darkblue"] = Color(0x00008b)
			colors["darkcyan"] = Color(0x008b8b)
			colors["darkgoldenrod"] = Color(0xb8860b)
			colors["darkgray"] = Color(0xa9a9a9)
			colors["darkgreen"] = Color(0x006400)
			colors["darkgrey"] = Color(0xa9a9a9)
			colors["darkkhaki"] = Color(0xbdb76b)
			colors["darkmagenta"] = Color(0x8b008b)
			colors["darkolivegreen"] = Color(0x556b2f)
			colors["darkorange"] = Color(0xff8c00)
			colors["darkorchid"] = Color(0x9932cc)
			colors["darkred"] = Color(0x8b0000)
			colors["darksalmon"] = Color(0xe9967a)
			colors["darkseagreen"] = Color(0x8fbc8f)
			colors["darkslateblue"] = Color(0x483d8b)
			colors["darkslategray"] = Color(0x2f4f4f)
			colors["darkslategrey"] = Color(0x2f4f4f)
			colors["darkturquoise"] = Color(0x00ced1)
			colors["darkviolet"] = Color(0x9400d3)
			colors["deeppink"] = Color(0xff1493)
			colors["deepskyblue"] = Color(0x00bfff)
			colors["dimgray"] = Color(0x696969)
			colors["dimgrey"] = Color(0x696969)
			colors["dodgerblue"] = Color(0x1e90ff)
			colors["firebrick"] = Color(0xb22222)
			colors["floralwhite"] = Color(0xfffaf0)
			colors["forestgreen"] = Color(0x228b22)
			colors["fuchsia"] = Color(0xff00ff)
			colors["gainsboro"] = Color(0xdcdcdc)
			colors["ghostwhite"] = Color(0xf8f8ff)
			colors["gold"] = Color(0xffd700)
			colors["goldenrod"] = Color(0xdaa520)
			colors["gray"] = Color(0x808080)
			colors["green"] = Color(0x008000)
			colors["greenyellow"] = Color(0xadff2f)
			colors["grey"] = Color(0x808080)
			colors["honeydew"] = Color(0xf0fff0)
			colors["hotpink"] = Color(0xff69b4)
			colors["indianred"] = Color(0xcd5c5c)
			colors["indigo"] = Color(0x4b0082)
			colors["ivory"] = Color(0xfffff0)
			colors["khaki"] = Color(0xf0e68c)
			colors["lavender"] = Color(0xe6e6fa)
			colors["lavenderblush"] = Color(0xfff0f5)
			colors["lawngreen"] = Color(0x7cfc00)
			colors["lemonchiffon"] = Color(0xfffacd)
			colors["lightblue"] = Color(0xadd8e6)
			colors["lightcoral"] = Color(0xf08080)
			colors["lightcyan"] = Color(0xe0ffff)
			colors["lightgoldenrodyellow"] = Color(0xfafad2)
			colors["lightgray"] = Color(0xd3d3d3)
			colors["lightgreen"] = Color(0x90ee90)
			colors["lightgrey"] = Color(0xd3d3d3)
			colors["lightpink"] = Color(0xffb6c1)
			colors["lightsalmon"] = Color(0xffa07a)
			colors["lightseagreen"] = Color(0x20b2aa)
			colors["lightskyblue"] = Color(0x87cefa)
			colors["lightslategray"] = Color(0x778899)
			colors["lightslategrey"] = Color(0x778899)
			colors["lightsteelblue"] = Color(0xb0c4de)
			colors["lightyellow"] = Color(0xffffe0)
			colors["lime"] = Color(0x00ff00)
			colors["limegreen"] = Color(0x32cd32)
			colors["linen"] = Color(0xfaf0e6)
			colors["magenta"] = Color(0xff00ff)
			colors["maroon"] = Color(0x800000)
			colors["mediumaquamarine"] = Color(0x66cdaa)
			colors["mediumblue"] = Color(0x0000cd)
			colors["mediumorchid"] = Color(0xba55d3)
			colors["mediumpurple"] = Color(0x9370db)
			colors["mediumseagreen"] = Color(0x3cb371)
			colors["mediumslateblue"] = Color(0x7b68ee)
			colors["mediumspringgreen"] = Color(0x00fa9a)
			colors["mediumturquoise"] = Color(0x48d1cc)
			colors["mediumvioletred"] = Color(0xc71585)
			colors["midnightblue"] = Color(0x191970)
			colors["mintcream"] = Color(0xf5fffa)
			colors["mistyrose"] = Color(0xffe4e1)
			colors["moccasin"] = Color(0xffe4b5)
			colors["navajowhite"] = Color(0xffdead)
			colors["navy"] = Color(0x000080)
			colors["oldlace"] = Color(0xfdf5e6)
			colors["olive"] = Color(0x808000)
			colors["olivedrab"] = Color(0x6b8e23)
			colors["orange"] = Color(0xffa500)
			colors["orangered"] = Color(0xff4500)
			colors["orchid"] = Color(0xda70d6)
			colors["palegoldenrod"] = Color(0xeee8aa)
			colors["palegreen"] = Color(0x98fb98)
			colors["paleturquoise"] = Color(0xafeeee)
			colors["palevioletred"] = Color(0xdb7093)
			colors["papayawhip"] = Color(0xffefd5)
			colors["peachpuff"] = Color(0xffdab9)
			colors["peru"] = Color(0xcd853f)
			colors["pink"] = Color(0xffc0cb)
			colors["plum"] = Color(0xdda0dd)
			colors["powderblue"] = Color(0xb0e0e6)
			colors["purple"] = Color(0x800080)
			colors["rebeccapurple"] = Color(0x663399)
			colors["red"] = Color(0xff0000)
			colors["rosybrown"] = Color(0xbc8f8f)
			colors["royalblue"] = Color(0x4169e1)
			colors["saddlebrown"] = Color(0x8b4513)
			colors["salmon"] = Color(0xfa8072)
			colors["sandybrown"] = Color(0xf4a460)
			colors["seagreen"] = Color(0x2e8b57)
			colors["seashell"] = Color(0xfff5ee)
			colors["sienna"] = Color(0xa0522d)
			colors["silver"] = Color(0xc0c0c0)
			colors["skyblue"] = Color(0x87ceeb)
			colors["slateblue"] = Color(0x6a5acd)
			colors["slategray"] = Color(0x708090)
			colors["slategrey"] = Color(0x708090)
			colors["snow"] = Color(0xfffafa)
			colors["springgreen"] = Color(0x00ff7f)
			colors["steelblue"] = Color(0x4682b4)
			colors["tan"] = Color(0xd2b48c)
			colors["teal"] = Color(0x008080)
			colors["thistle"] = Color(0xd8bfd8)
			colors["tomato"] = Color(0xff6347)
			colors["turquoise"] = Color(0x40e0d0)
			colors["violet"] = Color(0xee82ee)
			colors["wheat"] = Color(0xf5deb3)
			colors["white"] = Color(0xffffff)
			colors["whitesmoke"] = Color(0xf5f5f5)
			colors["yellow"] = Color(0xffff00)
			colors["yellowgreen"] = Color(0x9acd32)
		}
	}
}
