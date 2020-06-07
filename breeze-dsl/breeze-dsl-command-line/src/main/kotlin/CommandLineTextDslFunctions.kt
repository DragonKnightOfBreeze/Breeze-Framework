@file:Suppress("unused")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.commandline.CommandLineText.*

/**(No document.)*/
@CommandLineTextDsl
inline fun commandLineText(block:Document.() -> CharSequence) =
	Document().apply { text = block() }


/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.bold(text:CharSequence) = BoldText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.light(text:CharSequence) = LightText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.italic(text:CharSequence) = ItalicText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.underline(text:CharSequence) = UnderlineText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.blink(text:CharSequence) = BlinkText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.invert(text:CharSequence) = InvertText(text)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.color(text:CharSequence, color:Color) = ColoredText(text, color)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.bgColor(text:CharSequence, color:Color) = BgColoredText(text, color)

/**(No document.)*/
@CommandLineTextDsl
fun InlineDslEntry.style(text:CharSequence, vararg styles:Style) = StyledText(text, *styles)
