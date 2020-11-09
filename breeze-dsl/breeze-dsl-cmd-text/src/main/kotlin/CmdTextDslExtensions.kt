// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("unused")

package com.windea.breezeframework.dsl.cmdtext

import com.windea.breezeframework.dsl.cmdtext.CmdTextDsl.*

/**
 * 开始构建[CmdTextDsl]。
 */
@CmdTextDslMarker
inline fun cmdTextDsl(block: Document.() -> CharSequence): Document {
	return Document().apply { text = block() }
}


/**
 * 创建一个[CmdTextDsl.BoldText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.bold(text: CharSequence): BoldText {
	return BoldText(text)
}

/**
 * 创建一个[CmdTextDsl.LightText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.light(text: CharSequence): LightText {
	return LightText(text)
}

/**
 * 创建一个[CmdTextDsl.ItalicText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.italic(text: CharSequence): ItalicText {
	return ItalicText(text)
}

/**
 * 创建一个[CmdTextDsl.UnderlineText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.underline(text: CharSequence): UnderlineText {
	return UnderlineText(text)
}

/**
 * 创建一个[CmdTextDsl.BlinkText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.blink(text: CharSequence): BlinkText {
	return BlinkText(text)
}

/**
 * 创建一个[CmdTextDsl.InvertText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.invert(text: CharSequence): InvertText {
	return InvertText(text)
}

/**
 * 创建一个[CmdTextDsl.ColoredText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.color(text: CharSequence, color: Color): ColoredText {
	return ColoredText(text, color)
}

/**
 * 创建一个[CmdTextDsl.BgColoredText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.bgColor(text: CharSequence, color: Color): BgColoredText {
	return BgColoredText(text, color)
}

/**
 * 创建一个[CmdTextDsl.StyledText]。
 */
@CmdTextDslMarker
fun InlineDslEntry.style(text: CharSequence, vararg styles: Style): StyledText {
	return StyledText(text, *styles)
}
