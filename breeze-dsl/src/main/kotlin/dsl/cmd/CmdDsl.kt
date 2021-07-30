// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.cmd

import icu.windea.breezeframework.dsl.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732
//https://blog.csdn.net/qq_37187976/article/details/79265667
//https://blog.csdn.net/felix_f/article/details/12433171

@DslMarker
@MustBeDocumented
annotation class CmdDslMarker

@CmdDslMarker
class CmdDslDocument @PublishedApi internal constructor() : DslDocument, CmdDslInlinePoint {
	var text: CharSequence = ""

	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append(text)
	}

	override fun toString() = render()
}

@CmdDslMarker
interface CmdDslInlineElement : DslInlineElement

@CmdDslMarker
interface CmdDslInlinePoint : DslInlinePoint

@CmdDslMarker
abstract class CmdRichText : CmdDslInlineElement {
	abstract val text: CharSequence
	abstract val code: String

	//注意：
	//linux使用echo命令输出时需要加上-e参数，windows不能直接通过echo命令输出
	//linux: "\e[${code}m${text}\e[0m"
	//windows: "\u001B[${code}m${text}\u001B[0m"

	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append("\u001B[").append(code).append("m")
		append(text)
		append("\u001B[0m")
	}

	override fun toString() = render()
}

@CmdDslMarker
class CmdBoldText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Bold.code.toString()
}

@CmdDslMarker
class CmdLightText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Light.code.toString()
}

@CmdDslMarker
class CmdItalicText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Italic.code.toString()
}

@CmdDslMarker
class CmdUnderlineText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Underline.code.toString()
}

@CmdDslMarker
class CmdBlinkText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Blink.code.toString()
}

@CmdDslMarker
class CmdInvertText @PublishedApi internal constructor(override val text: CharSequence) : CmdRichText() {
	override val code = CmdStyle.Invert.code.toString()
}

@CmdDslMarker
class CmdColoredText @PublishedApi internal constructor(
	override val text: CharSequence, val color: CmdColor
) : CmdRichText() {
	override val code = color.code.toString()
}

@CmdDslMarker
class CmdBgColoredText @PublishedApi internal constructor(
	override val text: CharSequence, val color: CmdColor
) : CmdRichText() {
	override val code = (color.code + 10).toString()
}

@CmdDslMarker
class CmdStyledText @PublishedApi internal constructor(
	override val text: CharSequence, vararg val styles: CmdStyle,
) : CmdRichText() {
	override val code = styles.joinToString(";") { it.code.toString() }
}

@CmdDslMarker
enum class CmdColor(val code: Int) {
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
enum class CmdStyle(val code: Int) {
	Default(0),
	Bold(1),
	Light(2),
	Italic(3),
	Underline(4),
	Blink(5),
	Invert(7)
}
