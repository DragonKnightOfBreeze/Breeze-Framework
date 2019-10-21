@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.dsl.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732

//REGION top annotations and interfaces

@DslMarker
private annotation class CommandLineTextDsl

/**命令行富文本。*/
@CommandLineTextDsl
class CommandLineText @PublishedApi internal constructor() : DslBuilder, CommandLineTextDslInlineEntry {
	lateinit var text: String
	
	override fun toString(): String {
		return text
	}
}

//REGION dsl interfaces

/**命令行富文本Dsl的内联入口。*/
@CommandLineTextDsl
interface CommandLineTextDslInlineEntry : DslEntry

/**命令行富文本Dsl的内联元素。*/
@CommandLineTextDsl
interface CommandLineTextDslInlineElement : DslElement

//REGION dsl elements

sealed class CommandLineRichText(
	val text: String
) : CommandLineTextDslInlineElement

/**命令行颜色文本。*/
@CommandLineTextDsl
class CommandLineColorfulText(
	val color: CommandLineColor,
	text: String,
	val isBackgroundColorful: Boolean = false
) : CommandLineRichText(text) {
	override fun toString(): String {
		val codeSnippet = if(isBackgroundColorful) color.backgroundCode else color.foregroundCode
		val prefixSnippet = "\u001b[${codeSnippet}m"
		val suffixSnippet = "\u001b[0m"
		return "$prefixSnippet$text$suffixSnippet"
	}
}

//REGION enumerations and constants

/**命令行富文本的颜色。*/
@CommandLineTextDsl
enum class CommandLineColor(
	private val code: Int
) {
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
	
	val foregroundCode: Int = code
	val backgroundCode: Int = code + 10
}

//REGION build extensions

@CommandLineTextDsl
inline fun commandLineText(builder: CommandLineText.() -> String) =
	CommandLineText().also { it.text = it.builder() }

@CommandLineTextDsl
inline fun commandLine(builder: CommandLineText.() -> CommandLineRichText) =
	CommandLineText().also { it.text = it.builder().toString() }

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.color(color: CommandLineColor, text: String) =
	CommandLineColorfulText(color, text)

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.color(color: CommandLineColor, text: CommandLineRichText) =
	this.color(color, text.toString())

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.bgColor(color: CommandLineColor, text: String) =
	CommandLineColorfulText(color, text, true)

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.bgColor(color: CommandLineColor, text: CommandLineRichText) =
	this.bgColor(color, text.toString())
