// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.cmd

import icu.windea.breezeframework.dsl.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732
//https://blog.csdn.net/qq_37187976/article/details/79265667
//https://blog.csdn.net/felix_f/article/details/12433171

@CmdDslMarker
interface CmdDsl {
	@CmdDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, DslInlineEntry {
		var text: CharSequence = ""

		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append(text)
		}

		override fun toString() = render()
	}

	@CmdDslMarker
	interface DslInlineElement : IDslInlineElement

	@CmdDslMarker
	interface DslInlineEntry : IDslInlineEntry

	@CmdDslMarker
	abstract class RichText : DslInlineElement {
		abstract val text: CharSequence
		abstract val code: String

		//linux使用echo命令输出时需要加上-e参数，windows不能直接通过echo命令输出
		//linux: "\e[${code}m${text}\e[0m"
		//windows: "\u001B[${code}m${text}\u001B[0m"

		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append("\u001B[").append(code).append("m")
			append(text)
			append("\u001B[0m")
		}

		override fun toString(): String = render()
	}

	@CmdDslMarker
	class BoldText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Bold.code.toString()
	}

	@CmdDslMarker
	class LightText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Light.code.toString()
	}

	@CmdDslMarker
	class ItalicText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Italic.code.toString()
	}

	@CmdDslMarker
	class UnderlineText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Underline.code.toString()
	}

	@CmdDslMarker
	class BlinkText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Blink.code.toString()
	}

	@CmdDslMarker
	class InvertText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Invert.code.toString()
	}

	@CmdDslMarker
	class ColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color
	) : RichText() {
		override val code = color.code.toString()
	}

	@CmdDslMarker
	class BgColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color,
	) : RichText() {
		override val code = (color.code + 10).toString()
	}

	@CmdDslMarker
	class StyledText @PublishedApi internal constructor(
		override val text: CharSequence, vararg val styles: Style,
	) : RichText() {
		override val code = styles.joinToString(";") { it.code.toString() }
	}

	@CmdDslMarker
	enum class Color(val code: Int) {
		Black(30),
		Red(31),
		Green(32),
		Yellow(33),
		Blue(34),
		Magenta(35),
		Cyan(36),
		LightGray(37),
		DarkGray(90),
		LightRed(91),
		LightGreen(92),
		LightYellow(93),
		LightBlue(94),
		LightMagenta(95),
		LightCyan(96),
		White(97);
	}

	@CmdDslMarker
	enum class Style(val code: Int) {
		Default(0),
		Bold(1),
		Light(2),
		Italic(3),
		Underline(4),
		Blink(5),
		Invert(7)
	}
}
