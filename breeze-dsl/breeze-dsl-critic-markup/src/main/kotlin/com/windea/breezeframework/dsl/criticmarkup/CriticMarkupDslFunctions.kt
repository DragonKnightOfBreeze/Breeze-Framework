@file:Suppress("unused")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.CriticMarkup.*

/**(No document.)*/
@TopDslFunction
@CriticMarkupDsl
inline fun criticMarkup(block:Document.() -> CharSequence) = Document().apply { text = block() }


/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupDslInlineEntry.append(text:CharSequence) = AppendText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupDslInlineEntry.delete(text:CharSequence) = DeleteText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupDslInlineEntry.replace(text:CharSequence, replacedText:CharSequence) = ReplaceText(text, replacedText)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupDslInlineEntry.comment(text:CharSequence) = CommentText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupDslInlineEntry.highlight(text:CharSequence) = HighlightText(text)
