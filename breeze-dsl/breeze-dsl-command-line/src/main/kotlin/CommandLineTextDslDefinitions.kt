// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*

/**
 * Dsl definitions of [CommandLineTextDsl].
 */
@CommandLineTextDslMarker
interface CommandLineTextDslDefinitions {
	companion object {
		private fun richText(text: CharSequence, code: Any) = "\u001B[${code}m$text\u001B[0m"
	}


	/**
	 * Inline dsl element of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	interface InlineDslElement : DslElement, Inlineable

	/**
	 * Inline dsl entry of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	interface InlineDslEntry : DslEntry, Inlineable

	/**
	 * Rich text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	interface RichText : InlineDslElement {
		val text: CharSequence
		override val inlineText get() = text
	}

	/**
	 * Bold text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class BoldText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Bold.code)
	}

	/**
	 * Light text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class LightText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Light.code)
	}

	/**
	 * Italic text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class ItalicText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Italic.code)
	}

	/**
	 * Underline text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class UnderlineText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Underline.code)
	}

	/**
	 * Blink text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class BlinkText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Blink.code)
	}

	/**
	 * Invert text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class InvertText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString(): String = richText(text, Style.Invert.code)
	}

	/**
	 * Colored text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class ColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color,
	) : RichText {
		override fun toString(): String = richText(inlineText, color.code)
	}

	/**
	 * Background colored text of [CommandLineTextDsl].
	;	 */
	@CommandLineTextDslMarker
	class BgColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color,
	) : RichText {
		override fun toString(): String {
			return richText(text, color.code + 10)
		}
	}

	/**
	 * Styled text of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	class StyledText @PublishedApi internal constructor(
		override val text: CharSequence, vararg val styles: Style,
	) : RichText {
		override fun toString(): String {
			return richText(text, styles.joinToString(";") { it.code.toString() })
		}
	}


	/**
	 * Text color of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
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

	/**
	 * Text style of [CommandLineTextDsl].
	 */
	@CommandLineTextDslMarker
	enum class Style(internal val code: Int) {
		Default(0),
		Bold(1),
		Light(2),
		Italic(3),
		Underline(4),
		Blink(5),
		Invert(7);
	}
}
