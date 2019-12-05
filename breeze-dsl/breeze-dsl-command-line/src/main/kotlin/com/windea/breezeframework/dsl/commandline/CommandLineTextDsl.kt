@file:Suppress("unused", "NOTHING_TO_INLINE", "CanBeParameter")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732
//https://blog.csdn.net/qq_37187976/article/details/79265667
//https://blog.csdn.net/felix_f/article/details/12433171

//NOTE not for all echo commands

//region top annotations and interfaces
/**命令行富文本的Dsl。*/
@DslMarker
@MustBeDocumented
internal annotation class CommandLineTextDsl

/**命令行富文本。*/
@CommandLineTextDsl
class CommandLineText @PublishedApi internal constructor() : DslBuilder, CommandLineTextDslInlineEntry {
	lateinit var text: String
	
	override fun toString(): String {
		return text
	}
}
//endregion

//region dsl interfaces
/**命令行富文本Dsl的内联入口。*/
@CommandLineTextDsl
interface CommandLineTextDslInlineEntry : DslEntry

/**命令行富文本Dsl的内联元素。*/
@CommandLineTextDsl
interface CommandLineTextDslInlineElement : DslElement
//endregion

//region dsl elements
@CommandLineTextDsl
sealed class CommandLineRichText(
	protected val codes: String,
	val text: String
) : CommandLineTextDslInlineElement {
	override fun toString(): String {
		return if(codes.isEmpty()) text else "${command(codes)}$text$defaultCommand"
	}
}

@CommandLineTextDsl
class CommandLineBoldText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.Bold.code.toString(), text)

@CommandLineTextDsl
class CommandLineLightColorText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.LightColor.code.toString(), text)

@CommandLineTextDsl
class CommandLineItalicText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.Italic.code.toString(), text)

@CommandLineTextDsl
class CommandLineUnderlineText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.Underline.code.toString(), text)

@CommandLineTextDsl
class CommandLineBlinkText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.Blink.code.toString(), text)

@CommandLineTextDsl
class CommandLineInvertText(
	text: String
) : CommandLineRichText(CommandLineDisplayMode.Invert.code.toString(), text)

@CommandLineTextDsl
class CommandLineColorfulText(
	val color: CommandLineColor? = null,
	val backgroundColor: CommandLineColor? = null,
	text: String
) : CommandLineRichText(listOfNotNull(color?.code, backgroundColor?.backgroundCode).joinToStringOrEmpty(";"), text)

@CommandLineTextDsl
class CommandLineAdvanceText(
	val color: CommandLineColor? = null,
	val backgroundColor: CommandLineColor? = null,
	val displayMode: Array<out CommandLineDisplayMode> = arrayOf(),
	text: String
) : CommandLineRichText((listOfNotNull(color?.code, backgroundColor?.backgroundCode) + displayMode.map { it.code }).joinToStringOrEmpty(";"), text)
//endregion

//region enumerations and constants
/**命令行富文本的颜色。*/
@CommandLineTextDsl
enum class CommandLineColor(val code: Int) {
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
	
	val backgroundCode = code + 10
}

/**命令行富文本的显示格式。*/
@CommandLineTextDsl
enum class CommandLineDisplayMode(val code: Int) {
	/**默认。*/
	Default(0),
	/**粗体。*/
	Bold(1),
	/**浅色。*/
	LightColor(2),
	/**下划线*/
	Italic(3),
	/**下划线。*/
	Underline(4),
	/**闪烁。*/
	Blink(5),
	/**反显。*/
	Invert(7);
	
	val disableCode = code + 20
}

private const val defaultCommand = "\u001B[0m"

private fun command(codes: String) = "\u001B[${codes}m"
//endregion

//region build extensions
@CommandLineTextDsl
inline fun commandLineText(block: CommandLineText.() -> String) = CommandLineText().also { it.text = it.block() }

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.bold(text: String) = CommandLineBoldText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.lightColor(text: String) = CommandLineLightColorText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.italic(text: String) = CommandLineItalicText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.underline(text: String) = CommandLineUnderlineText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.blink(text: String) = CommandLineBlinkText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.invert(text: String) = CommandLineInvertText(text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.color(color: CommandLineColor,
	text: String) = CommandLineColorfulText(color, null, text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.bgColor(backgroundColor: CommandLineColor, text: String) =
	CommandLineColorfulText(null, backgroundColor, text).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.colorful(
	color: CommandLineColor? = null,
	backgroundColor: CommandLineColor? = null,
	lazyText: () -> String
) = CommandLineColorfulText(color, backgroundColor, lazyText()).toString()

@InlineDsl
@CommandLineTextDsl
inline fun CommandLineTextDslInlineEntry.advance(
	color: CommandLineColor? = null,
	backgroundColor: CommandLineColor? = null,
	vararg displayMode: CommandLineDisplayMode,
	lazyText: () -> String
) = CommandLineAdvanceText(color, backgroundColor, displayMode, lazyText()).toString()
//endregion
