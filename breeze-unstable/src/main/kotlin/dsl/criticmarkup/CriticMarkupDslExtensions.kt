// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CriticMarkupDslExtensions")

package icu.windea.breezeframework.dsl.criticmarkup

import icu.windea.breezeframework.dsl.criticmarkup.CriticMarkupDsl.*

/**
 * 开始构建[CriticMarkupDsl]。
 */
@CriticMarkupDslMarker
inline fun criticMarkupDsl(block: DslDocument.() -> CharSequence): DslDocument {
	return DslDocument().apply { text = block() }
}

/**
 * 创建一个[CriticMarkupDsl.Addition]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.append(text: CharSequence): Addition {
	return Addition(text)
}

/**
 * 创建一个[CriticMarkupDsl.Deletion]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.delete(text: CharSequence): Deletion {
	return Deletion(text)
}

/**
 * 创建一个[CriticMarkupDsl.Substitution]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.substitute(text: CharSequence, newText: CharSequence): Substitution {
	return Substitution(text, newText)
}

/**
 * 创建一个[CriticMarkupDsl.Comment]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.comment(text: CharSequence): Comment {
	return Comment(text)
}

/**
 * 创建一个[CriticMarkupDsl.Highlight]。
 */
@CriticMarkupDslMarker
fun InlineDslEntry.highlight(text: CharSequence): Highlight {
	return Highlight(text)
}