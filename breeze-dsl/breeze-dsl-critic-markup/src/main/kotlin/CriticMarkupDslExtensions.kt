// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("unused")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.criticmarkup.CriticMarkupDsl.*

/**
 * 开始构建[CriticMarkupDsl]。
 */
@CriticMarkupDslMarker
inline fun criticMarkupDsl(block: Document.() -> CharSequence): Document {
	return Document().apply { text = block() }
}

/**
 * 创建一个[CriticMarkupDsl.Addition]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.append(text: CharSequence): Addition = Addition(text)

/**
 * 创建一个[CriticMarkupDsl.Deletion]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.delete(text: CharSequence): Deletion = Deletion(text)

/**
 * 创建一个[CriticMarkupDsl.Substitution]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.substitute(text: CharSequence, newText: CharSequence): Substitution = Substitution(text, newText)

/**
 * 创建一个[CriticMarkupDsl.Comment]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.comment(text: CharSequence): Comment = Comment(text)

/**
 * 创建一个[CriticMarkupDsl.Highlight]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.highlight(text: CharSequence): Highlight = Highlight(text)
