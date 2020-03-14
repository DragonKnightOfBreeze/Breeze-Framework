@file:Suppress("unused")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.commandline.CommandLine.*

/**(No document.)*/
@TopDslFunction
@CommandLineDsl
inline fun commandLine(block:Document.() -> CharSequence) = Document().apply { text = block() }


/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.bold(text:CharSequence) = BoldText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.light(text:CharSequence) = LightText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.italic(text:CharSequence) = ItalicText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.underline(text:CharSequence) = UnderlineText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.blink(text:CharSequence) = BlinkText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.invert(text:CharSequence) = InvertText(text)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.color(text:CharSequence, color:Color) = ColoredText(text, color)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.bgColor(text:CharSequence, color:Color) = BgColoredText(text, color)

/**(No document.)*/
@InlineDslFunction
@CommandLineDsl
fun CommandLineDslInlineEntry.style(text:CharSequence, vararg styles:Style) = StyledText(text, *styles)
