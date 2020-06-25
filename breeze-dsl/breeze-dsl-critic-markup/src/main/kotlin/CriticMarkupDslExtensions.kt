@file:Suppress("unused")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.criticmarkup.CriticMarkupDslDefinitions.*

/**Build a [CriticMarkupDsl].*/
@CriticMarkupDslMarker
inline fun criticMarkupDsl(block: CriticMarkupDsl.() -> CharSequence): CriticMarkupDsl =
	CriticMarkupDsl().apply { text = block() }


/**Create a [CriticMarkupDslDefinitions.Addition].*/
@CriticMarkupDslMarker
fun InlineDslEntry.append(text: CharSequence): Addition = Addition(text)

/**Create a [CriticMarkupDslDefinitions.Deletion].*/
@CriticMarkupDslMarker
fun InlineDslEntry.delete(text: CharSequence): Deletion = Deletion(text)

/**Create a [CriticMarkupDslDefinitions.Substitution].*/
@CriticMarkupDslMarker
fun InlineDslEntry.substitute(text: CharSequence, newText: CharSequence): Substitution = Substitution(text, newText)

/**Create a [CriticMarkupDslDefinitions.Comment].*/
@CriticMarkupDslMarker
fun InlineDslEntry.comment(text: CharSequence): Comment = Comment(text)

/**Create a [CriticMarkupDslDefinitions.Highlight].*/
@CriticMarkupDslMarker
fun InlineDslEntry.highlight(text: CharSequence): Highlight = Highlight(text)
