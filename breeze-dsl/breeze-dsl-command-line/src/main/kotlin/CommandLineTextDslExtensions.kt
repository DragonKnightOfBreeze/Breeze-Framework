// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("unused")

package com.windea.breezeframework.dsl.commandline

import com.windea.breezeframework.dsl.commandline.CommandLineTextDslDefinitions.*

/**Build a [CommandLineTextDsl].*/
@CommandLineTextDslMarker
inline fun commandLineTextDsl(block: CommandLineTextDsl.() -> CharSequence) = CommandLineTextDsl().apply { text = block() }


/**Create a [CommandLineTextDslDefinitions.BoldText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.bold(text: CharSequence) = BoldText(text)

/**Create a [CommandLineTextDslDefinitions.LightText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.light(text: CharSequence) = LightText(text)

/**Create a [CommandLineTextDslDefinitions.ItalicText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.italic(text: CharSequence) = ItalicText(text)

/**Create a [CommandLineTextDslDefinitions.UnderlineText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.underline(text: CharSequence) = UnderlineText(text)

/**Create a [CommandLineTextDslDefinitions.BlinkText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.blink(text: CharSequence) = BlinkText(text)

/**Create a [CommandLineTextDslDefinitions.InvertText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.invert(text: CharSequence) = InvertText(text)

/**Create a [CommandLineTextDslDefinitions.ColoredText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.color(text: CharSequence, color: Color) = ColoredText(text, color)

/**Create a [CommandLineTextDslDefinitions.BgColoredText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.bgColor(text: CharSequence, color: Color) = BgColoredText(text, color)

/**Create a [CommandLineTextDslDefinitions.StyledText].*/
@CommandLineTextDslMarker
fun InlineDslEntry.style(text: CharSequence, vararg styles: Style) = StyledText(text, *styles)
