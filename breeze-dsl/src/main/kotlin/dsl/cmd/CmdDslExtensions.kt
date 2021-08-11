// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CmdDslExtensions")

package icu.windea.breezeframework.dsl.cmd

@CmdDslMarker
inline fun cmdDsl(block: CmdDslDocument.() -> CharSequence): CmdDslDocument {
	return CmdDslDocument().apply { text = block() }
}

@CmdDslMarker
fun CmdDslInlinePoint.bold(text: CharSequence): CmdBoldText {
	return CmdBoldText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.light(text: CharSequence): CmdLightText {
	return CmdLightText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.italic(text: CharSequence): CmdItalicText {
	return CmdItalicText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.underline(text: CharSequence): CmdUnderlineText {
	return CmdUnderlineText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.blink(text: CharSequence): CmdBlinkText {
	return CmdBlinkText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.invert(text: CharSequence): CmdInvertText {
	return CmdInvertText(text)
}

@CmdDslMarker
fun CmdDslInlinePoint.color(text: CharSequence, color: CmdColor): CmdColoredText {
	return CmdColoredText(text, color)
}

@CmdDslMarker
fun CmdDslInlinePoint.bgColor(text: CharSequence, color: CmdColor): CmdBgColoredText {
	return CmdBgColoredText(text, color)
}

@CmdDslMarker
fun CmdDslInlinePoint.style(text: CharSequence, vararg styles: CmdStyle): CmdStyledText {
	return CmdStyledText(text, *styles)
}
