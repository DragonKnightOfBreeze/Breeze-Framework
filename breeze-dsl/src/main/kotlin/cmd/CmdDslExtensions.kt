// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CmdDslExtensions")

package icu.windea.breezeframework.dsl.cmd

import icu.windea.breezeframework.dsl.cmd.CmdDsl.*

/**
 * 开始构建[CmdDsl]。
 */
@CmdDslMarker
inline fun cmdDsl(block: DslDocument.() -> CharSequence): DslDocument {
	return DslDocument().apply { text = block() }
}

/**
 * 创建一个[CmdDsl.BoldText]。
 */
@CmdDslMarker
fun DslInlineEntry.bold(text: CharSequence): BoldText {
	return BoldText(text)
}

/**
 * 创建一个[CmdDsl.LightText]。
 */
@CmdDslMarker
fun DslInlineEntry.light(text: CharSequence): LightText {
	return LightText(text)
}

/**
 * 创建一个[CmdDsl.ItalicText]。
 */
@CmdDslMarker
fun DslInlineEntry.italic(text: CharSequence): ItalicText {
	return ItalicText(text)
}

/**
 * 创建一个[CmdDsl.UnderlineText]。
 */
@CmdDslMarker
fun DslInlineEntry.underline(text: CharSequence): UnderlineText {
	return UnderlineText(text)
}

/**
 * 创建一个[CmdDsl.BlinkText]。
 */
@CmdDslMarker
fun DslInlineEntry.blink(text: CharSequence): BlinkText {
	return BlinkText(text)
}

/**
 * 创建一个[CmdDsl.InvertText]。
 */
@CmdDslMarker
fun DslInlineEntry.invert(text: CharSequence): InvertText {
	return InvertText(text)
}

/**
 * 创建一个[CmdDsl.ColoredText]。
 */
@CmdDslMarker
fun DslInlineEntry.color(text: CharSequence, color: Color): ColoredText {
	return ColoredText(text, color)
}

/**
 * 创建一个[CmdDsl.BgColoredText]。
 */
@CmdDslMarker
fun DslInlineEntry.bgColor(text: CharSequence, color: Color): BgColoredText {
	return BgColoredText(text, color)
}

/**
 * 创建一个[CmdDsl.StyledText]。
 */
@CmdDslMarker
fun DslInlineEntry.style(text: CharSequence, vararg styles: Style): StyledText {
	return StyledText(text, *styles)
}
