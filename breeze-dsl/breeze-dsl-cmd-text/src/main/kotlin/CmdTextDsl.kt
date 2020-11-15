// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.cmdtext

import com.windea.breezeframework.core.*
import com.windea.breezeframework.dsl.*

@CmdTextDslMarker
interface CmdTextDsl {
	@CmdTextDslMarker
	class Document @PublishedApi internal constructor() : DslDocument, InlineDslEntry {
		var text: CharSequence = ""
		override val inlineText: CharSequence get() = text

		override fun toString(): String = text.toString()
	}

	companion object {
		private fun richText(text: CharSequence, code: Any) = "\u001B[${code}m$text\u001B[0m"
	}

	@CmdTextDslMarker
	interface InlineDslElement : DslElement, Inlineable

	@CmdTextDslMarker
	interface InlineDslEntry : Inlineable

	@CmdTextDslMarker
	abstract class RichText : InlineDslElement {
		abstract val text: CharSequence
		abstract val code:String
		override val inlineText get() = text

		override fun toString(): String {
			return "\u001B[${code}m$text\u001B[0m"
		}
	}

	@CmdTextDslMarker
	class BoldText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Bold.code.toString()
	}

	@CmdTextDslMarker
	class LightText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Light.code.toString()
	}

	@CmdTextDslMarker
	class ItalicText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Italic.code.toString()
	}

	@CmdTextDslMarker
	class UnderlineText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Underline.code.toString()
	}

	@CmdTextDslMarker
	class BlinkText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Blink.code.toString()
	}

	@CmdTextDslMarker
	class InvertText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override val code = Style.Invert.code.toString()
	}

	@CmdTextDslMarker
	class ColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color
	) : RichText() {
		override val code =color.code.toString()
	}

	@CmdTextDslMarker
	class BgColoredText @PublishedApi internal constructor(
		override val text: CharSequence, val color: Color,
	) : RichText() {
		override val code =(color.code + 10).toString()
	}

	@CmdTextDslMarker
	class StyledText @PublishedApi internal constructor(
		override val text: CharSequence, vararg val styles: Style,
	) : RichText() {
		override val code =styles.joinToString(";") { it.code.toString() }
	}

	@CmdTextDslMarker
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

	@CmdTextDslMarker
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
