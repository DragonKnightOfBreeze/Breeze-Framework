@file:Suppress("unused")

package com.windea.breezeframework.dsl.criticmarkup

/**Build a [Critic Markup Dsl](http://criticmarkup.com).*/
@CriticMarkupDslMarker
inline fun criticMarkupDsl(block:CriticMarkupDsl.() -> CharSequence):CriticMarkupDsl =
	CriticMarkupDsl().apply { text = block()}


/**Create a Critic Markup addition.*/
@CriticMarkupDslMarker
fun CriticMarkupDslEntry.append(text:CharSequence):Addition =
	Addition(text)

/**Create a Critic Markup deletion.*/
@CriticMarkupDslMarker
fun CriticMarkupDslEntry.delete(text:CharSequence):Deletion =
	Deletion(text)

/**Create a Critic Markup substitution.*/
@CriticMarkupDslMarker
fun CriticMarkupDslEntry.substitute(text:CharSequence, newText:CharSequence):Substitution =
	Substitution(text, newText)

/**Create a Critic Markup comment.*/
@CriticMarkupDslMarker
fun CriticMarkupDslEntry.comment(text:CharSequence):Comment =
	Comment(text)

/**Create a Critic Markup highlight.*/
@CriticMarkupDslMarker
fun CriticMarkupDslEntry.highlight(text:CharSequence):Highlight =
	Highlight(text)
