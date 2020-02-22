@file:Suppress("unused", "CanBeParameter")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.commandline.CommandLineText.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732
//https://blog.csdn.net/qq_37187976/article/details/79265667
//https://blog.csdn.net/felix_f/article/details/12433171

//not for all echo commands

//region dsl top declarations
/**命令行富文本的Dsl。*/
@DslMarker
@MustBeDocumented
internal annotation class CommandLineTextDsl

/**命令行富文本。*/
@CommandLineTextDsl
class CommandLineText @PublishedApi internal constructor() : DslDocument, CommandLineTextDslInlineEntry {
	lateinit var text: String

	override fun toString(): String {
		return text
	}

	/**命令行富文本的颜色。*/
	@CommandLineTextDsl
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
		White(97)
	}

	/**命令行富文本的风格。*/
	@CommandLineTextDsl
	enum class Style(val code: Int) {
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
		Invert(7)
	}
}
//endregion

//region dsl declarations
/**命令行富文本Dsl的内联入口。*/
@CommandLineTextDsl
interface CommandLineTextDslInlineEntry : DslEntry
//endregion

//region dsl build extensions
@CommandLineTextDsl
inline fun commandLineText(block: CommandLineText.() -> String) = CommandLineText().also { it.text = it.block() }

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.bold(text: String): String {
	return richText(Style.Bold.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.lightColor(text: String): String {
	return richText(Style.LightColor.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.italic(text: String): String {
	return richText(Style.Italic.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.underline(text: String): String {
	return richText(Style.Underline.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.blink(text: String): String {
	return richText(Style.Blink.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.invert(text: String): String {
	return richText(Style.Invert.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.color(color: Color, text: String): String {
	return richText(color.code, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.bgColor(backgroundColor: Color, text: String): String {
	return richText(backgroundColor.code + 10, text)
}

@InlineDslFunction
@CommandLineTextDsl
fun CommandLineTextDslInlineEntry.style(vararg styles: Style, lazyText: () -> String): String {
	return richText(styles.joinToString(";") { it.code.toString() }, lazyText())
}

private fun richText(code: Int, text: String): String {
	return text.addSurrounding("\u001B[${code}m", "\u001B[0m")
}

private fun richText(codes: String, text: String): String {
	return text.addSurrounding("\u001B[${codes}m", "\u001B[0m")
}
//endregion
