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
fun InlineDslEntry.append(text:CharSequence) = AppendText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun InlineDslEntry.delete(text:CharSequence) = DeleteText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun InlineDslEntry.replace(text:CharSequence, replacedText:CharSequence) = ReplaceText(text, replacedText)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun InlineDslEntry.comment(text:CharSequence) = CommentText(text)

/**(No document.)*/
@InlineDslFunction
@CriticMarkupDsl
fun InlineDslEntry.highlight(text:CharSequence) = HighlightText(text)
