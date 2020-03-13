@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
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

/**命令行文本。*/
@CommandLineDsl
class CommandLine @PublishedApi internal constructor() : DslDocument, CommandLineInlineEntry {
	@PublishedApi internal lateinit var content: CharSequence

	override fun toString() = content.toString()

	/**命令行富文本。*/
	@CommandLineDsl
	interface RichText : CommandLineElement, HandledCharSequence

	/**命令行加粗文本。*/
	@CommandLineDsl
	inline class BoldText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Bold.code, text)
	}

	/**命令行浅色文本。*/
	@CommandLineDsl
	inline class LightText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Light.code, text)
	}

	/**命令行斜体文本。*/
	@CommandLineDsl
	inline class ItalicText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Italic.code, text)
	}

	/**命令行下划线文本。*/
	@CommandLineDsl
	inline class UnderlineText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Underline.code, text)
	}

	/**命令行闪烁文本。*/
	@CommandLineDsl
	inline class BlinkText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Blink.code, text)
	}

	/**命令行反显文本。*/
	@CommandLineDsl
	inline class InvertText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = richText(Style.Invert.code, text)
	}

	/**
	 * 命令行前景色文本。
	 * @property color 文本的前景色。
	 */
	@CommandLineDsl
	class ColoredText internal constructor(override val text: CharSequence, val color: Color) : RichText {
		override fun toString() = richText(color.code, text)
	}

	/**
	 * 命令行背景色文本。
	 * @property color 文本的背景色。
	 * */
	@CommandLineDsl
	class BgColoredText internal constructor(override val text: CharSequence, val color: Color) : RichText {
		override fun toString() = richText(color.code + 10, text)
	}

	/**
	 * 命令行格式文本。
	 * @property styles 文本的格式组。
	 * */
	@CommandLineDsl
	class StyledText internal constructor(override val text: CharSequence, vararg val styles: Style) : RichText {
		override fun toString() = richText(styles.joinToString(";") { it.code.toString() }, text)
	}

	/**命令行文本的颜色。*/
	@CommandLineDsl
	enum class Color(internal val code: Int) {
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
	enum class Style(internal val code: Int) {
		/**默认。*/
		Default(0),

		/**粗体。*/
		Bold(1),

		/**浅色。*/
		Light(2),

		/**下划线*/
		Italic(3),

		/**下划线。*/
		Underline(4),

		/**闪烁。*/
		Blink(5),

		/**反显。*/
		Invert(7);
	}
}

/**命令行文本的内联入口。*/
@CommandLineDsl
interface CommandLineInlineEntry : DslEntry {
	@InlineDslFunction
	@CommandLineDsl
	fun bold(text: CharSequence) = BoldText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun light(text: CharSequence) = LightText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun italic(text: CharSequence) = ItalicText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun underline(text: CharSequence) = UnderlineText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun blink(text: CharSequence) = BlinkText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun invert(text: CharSequence) = InvertText(text)

	@InlineDslFunction
	@CommandLineDsl
	fun color(text: CharSequence, color: Color) = ColoredText(text, color)

	@InlineDslFunction
	@CommandLineDsl
	fun bgColor(text: CharSequence, color: Color) = BgColoredText(text, color)

	@InlineDslFunction
	@CommandLineDsl
	fun style(text: CharSequence, vararg styles: Style) = StyledText(text, *styles)
}

/**命令行文本的元素。*/
@CommandLineDsl
interface CommandLineElement : DslElement


@TopDslFunction
@CommandLineDsl
inline fun commandLine(block: CommandLine.() -> CharSequence) = CommandLine().apply { content = block() }


private fun richText(code: Int, text: CharSequence) = text.toString().addSurrounding("\u001B[${code}m", "\u001B[0m")

private fun richText(codes: String, text: CharSequence) = text.toString().addSurrounding("\u001B[${codes}m", "\u001B[0m")

