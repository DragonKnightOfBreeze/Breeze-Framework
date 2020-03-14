@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.commandline.CommandLine.*

//https://github.com/ziggy42/kolor
//https://msd.misuland.com/pd/3643782633961293732
//https://blog.csdn.net/qq_37187976/article/details/79265667
//https://blog.csdn.net/felix_f/article/details/12433171
//not for all echo commands

/**命令行文本的Dsl。*/
@DslMarker
@MustBeDocumented
annotation class CommandLineDsl

/**命令行文本的内联入口。*/
@CommandLineDsl
interface CommandLineInlineEntry : DslEntry {
	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun bold(text:CharSequence) = BoldText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun light(text:CharSequence) = LightText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun italic(text:CharSequence) = ItalicText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun underline(text:CharSequence) = UnderlineText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun blink(text:CharSequence) = BlinkText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun invert(text:CharSequence) = InvertText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun color(text:CharSequence, color:Color) = ColoredText(text, color)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun bgColor(text:CharSequence, color:Color) = BgColoredText(text, color)

	/**(No document.)*/
	@InlineDslFunction
	@CommandLineDsl
	fun style(text:CharSequence, vararg styles:Style) = StyledText(text, *styles)
}

/**命令行文本的元素。*/
@CommandLineDsl
interface CommandLineInlineElement : DslElement

/**命令行文本。*/
@CommandLineDsl
interface CommandLine {
	/**命令行文本的文档。*/
	class Document @PublishedApi internal constructor() : DslDocument, CommandLineInlineEntry {
		var text:CharSequence = ""

		override fun toString():String {
			return text.toString()
		}
	}

	/**命令行富文本。*/
	@CommandLineDsl
	interface RichText : CommandLineInlineElement, HandledCharSequence

	/**命令行加粗文本。*/
	@CommandLineDsl
	inline class BoldText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Bold.code)
	}

	/**命令行浅色文本。*/
	@CommandLineDsl
	inline class LightText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Light.code)
	}

	/**命令行斜体文本。*/
	@CommandLineDsl
	inline class ItalicText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Italic.code)
	}

	/**命令行下划线文本。*/
	@CommandLineDsl
	inline class UnderlineText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Underline.code)
	}

	/**命令行闪烁文本。*/
	@CommandLineDsl
	inline class BlinkText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Blink.code)
	}

	/**命令行反显文本。*/
	@CommandLineDsl
	inline class InvertText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = richText(text, Style.Invert.code)
	}

	/**
	 * 命令行前景色文本。
	 * @property color 文本的前景色。
	 */
	@CommandLineDsl
	class ColoredText @PublishedApi internal constructor(
		override val text:CharSequence, val color:Color
	) : RichText {
		override fun toString() = richText(text, color.code)
	}

	/**
	 * 命令行背景色文本。
	 * @property color 文本的背景色。
	 */
	@CommandLineDsl
	class BgColoredText @PublishedApi internal constructor(
		override val text:CharSequence, val color:Color
	) : RichText {
		override fun toString() = richText(text, color.code + 10)
	}

	/**
	 * 命令行格式文本。
	 * @property styles 文本的格式组。
	 */
	@CommandLineDsl
	class StyledText @PublishedApi internal constructor(
		override val text:CharSequence, vararg val styles:Style
	) : RichText {
		override fun toString() = richText(text, styles.joinToString(";") { it.code.toString() })
	}

	/**命令行文本的颜色。*/
	@CommandLineDsl
	enum class Color(
		internal val code:Int
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
		White(97)
	}

	/**命令行文本的风格。*/
	@CommandLineDsl
	enum class Style(
		internal val code:Int
	) {
		Default(0),
		Bold(1),
		Light(2),
		Italic(3),
		Underline(4),
		Blink(5),
		Invert(7);
	}

	companion object {
		private fun richText(text:CharSequence, code:Any) = "\u001B[${code}m$text\u001B[0m"
	}
}


/**
 * (No document.)
 */
@TopDslFunction
@CommandLineDsl
inline fun commandLine(block:Document.() -> CharSequence) = Document().apply { text = block() }



