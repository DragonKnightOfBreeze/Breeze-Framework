@file:Reference("[Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidSequenceConfig.indent

//REGION Dsl annotations

@DslMarker
annotation class MermaidSequenceDsl

//REGION Dsl elements & build functions

/**构建Mermaid序列图。*/
@MermaidSequenceDsl
fun mermaidSequence(builder: MermaidSequence.() -> Unit) = MermaidSequence().also { it.builder() }

/**Mermaid序列图Dsl的元素。*/
@MermaidSequenceDsl
interface MermaidSequenceDslElement

/**抽象的Mermaid序列图。*/
@MermaidSequenceDsl
abstract class AbstractMermaidSequence : MermaidSequenceDslElement, CanIndentContent {
	val actors: MutableSet<MermaidSequenceActor> = mutableSetOf()
	val messages: MutableList<MermaidSequenceMessage> = mutableListOf()
	val notes: MutableList<MermaidSequenceNote> = mutableListOf()
	val scopes: MutableList<MermaidSequenceScope> = mutableListOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		return arrayOf(
			actors.joinToString("\n"),
			messages.joinToString("\n"),
			notes.joinToString("\n"),
			scopes.joinToString("\n")
		).filterNotEmpty().joinToString("\n\n")
	}
	
	
	@MermaidSequenceDsl
	inline fun actor(name: String) =
		MermaidSequenceActor(name).also { actors += it }
	
	@MermaidSequenceDsl
	inline infix fun MermaidSequenceActor.alias(alias: String) = this.also { it.alias = alias }
	
	@MermaidSequenceDsl
	inline fun message(fromActorName: String, toActorName: String, messageText: String, messageArrow: MermaidSequenceMessageArrow = MermaidSequenceMessageArrow.Arrow) =
		MermaidSequenceMessage(fromActorName, toActorName, messageText, messageArrow).also { messages += it }
	
	@MermaidSequenceDsl
	inline infix fun MermaidSequenceMessage.activate(status: Boolean) = this.also { it.activateStatus = status }
	
	@MermaidSequenceDsl
	inline fun note(text: String) = MermaidSequenceNote(text).also { notes += it }
	
	@MermaidSequenceDsl
	inline infix fun MermaidSequenceNote.leftOf(actorName: String) =
		this.also { it.position = MermaidSequenceNodePosition.LeftOf }.also { it.targetActorName = actorName }
	
	@MermaidSequenceDsl
	inline infix fun MermaidSequenceNote.rightOf(actorName: String) =
		this.also { it.position = MermaidSequenceNodePosition.RightOf }.also { it.targetActorName = actorName }
	
	@MermaidSequenceDsl
	inline infix fun MermaidSequenceNote.over(actorNamePair: Pair<String, String>) =
		this.also { it.position = MermaidSequenceNodePosition.RightOf }
			.also { it.targetActorName = actorNamePair.first }.also { it.targetActor2Name = actorNamePair.second }
	
	@MermaidSequenceDsl
	inline fun loop(text: String, builder: MermaidSequenceLoop.() -> Unit) =
		MermaidSequenceLoop(text).also { it.builder() }.also { scopes += it }
	
	@MermaidSequenceDsl
	inline fun opt(text: String, builder: MermaidSequenceOptional.() -> Unit) =
		MermaidSequenceOptional(text).also { it.builder() }.also { scopes += it }
	
	@MermaidSequenceDsl
	inline fun alt(text: String, builder: MermaidSequenceAlternative.() -> Unit) =
		MermaidSequenceAlternative(text).also { it.builder() }.also { scopes += it }
	
	@MermaidSequenceDsl
	inline fun highlight(text: String, builder: MermaidSequenceHighlight.() -> Unit) =
		MermaidSequenceHighlight(text).also { it.builder() }.also { scopes += it }
}

/**Mermaid序列图。*/
@MermaidSequenceDsl
class MermaidSequence @PublishedApi internal constructor() : AbstractMermaidSequence(), Dsl {
	override fun toString(): String {
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "sequenceDiagram\n$indentedSnippet"
	}
}

/**Mermaid序列图角色。*/
@MermaidSequenceDsl
class MermaidSequenceActor @PublishedApi internal constructor(
	name: String
) : MermaidSequenceDslElement {
	val name: String = name //NOTE do not ensure argument is valid
	
	var alias: String? = null //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val aliasSnippet = if(alias.isNullOrEmpty()) "" else "$alias as "
		return "participant $aliasSnippet$name"
	}
}

/**Mermaid序列图消息。*/
@MermaidSequenceDsl
class MermaidSequenceMessage @PublishedApi internal constructor(
	fromActorName: String,
	toActorName: String,
	messageText: String,
	messageArrow: MermaidSequenceMessageArrow = MermaidSequenceMessageArrow.Arrow
) : MermaidSequenceDslElement {
	val fromActorName: String = fromActorName //NOTE do not ensure argument is valid
	val toActorName: String = toActorName //NOTE do not ensure argument is valid
	val messageText: String = messageText.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	val messageArrow: MermaidSequenceMessageArrow = messageArrow
	
	var activateStatus: Boolean? = null
	
	//TODO multiline & escaped message text
	override fun toString(): String {
		val activateSnippet = activateStatus?.let { if(it) "+ " else "- " } ?: ""
		return "$fromActorName ${messageArrow.text} $activateSnippet$toActorName: $messageText"
	}
}

/**Mermaid序列图注释。*/
@MermaidSequenceDsl
class MermaidSequenceNote @PublishedApi internal constructor(
	text: String
) : MermaidSequenceDslElement {
	val text: String = text.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	
	var position: MermaidSequenceNodePosition = MermaidSequenceNodePosition.RightOf
	var targetActorName: String = "Default"
	var targetActor2Name: String? = null
	
	//TODO multiline & escaped message text
	override fun toString(): String {
		val targetActor2NameSnippet = targetActor2Name?.let { ", $it" } ?: ""
		return "note $position $targetActorName$targetActor2NameSnippet: $text"
	}
}

/**Mermaid序列图作用域。*/
@MermaidSequenceDsl
abstract class MermaidSequenceScope @PublishedApi internal constructor(
	val type: String,
	text: String?
) : AbstractMermaidSequence() {
	val text: String? = text?.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val textSnippet = text?.let { " $it" } ?: ""
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "$type$textSnippet\n$indentedSnippet\nend"
	}
}

/**Mermaid序列图循环作用域。*/
@MermaidSequenceDsl
class MermaidSequenceLoop @PublishedApi internal constructor(
	text: String
) : MermaidSequenceScope("loop", text)

/**Mermaid序列图可选作用域。*/
@MermaidSequenceDsl
class MermaidSequenceOptional @PublishedApi internal constructor(
	text: String
) : MermaidSequenceScope("opt", text)

/**Mermaid序列图替代作用域。*/
@MermaidSequenceDsl
class MermaidSequenceAlternative @PublishedApi internal constructor(
	text: String
) : MermaidSequenceScope("alt", text) {
	val elseScopes: MutableList<MermaidSequenceElse> = mutableListOf()
	
	override fun toString(): String {
		val elseScopesSnippet = elseScopes.joinToString("\n").ifNotEmpty { "\n$it" }
		return "${super.toString()}$elseScopesSnippet"
	}
	
	
	@MermaidSequenceDsl
	inline fun `else`(text: String) = MermaidSequenceElse(text).also { elseScopes += it }
	
	@MermaidSequenceDsl
	inline fun `else`() = MermaidSequenceElse().also { elseScopes += it }
}

/**Mermaid序列图其余作用域。*/
@MermaidSequenceDsl
class MermaidSequenceElse @PublishedApi internal constructor(
	text: String? = null
) : MermaidSequenceScope("else", text)

/**Mermaid序列图颜色高亮作用域。*/
@MermaidSequenceDsl
class MermaidSequenceHighlight @PublishedApi internal constructor(
	colorText: String
) : MermaidSequenceScope("rect", colorText)

//REGION Enumerations and constants

/**Mermaid序列图消息的箭头类型。*/
enum class MermaidSequenceMessageArrow(
	val text: String
) {
	Arrow("->>"),
	DottedArrow("-->>"),
	Line("->"),
	DottedLine("-->"),
	Cross("-x"),
	DottedCross("--x")
}

/**Mermaid序列图注释的位置。*/
enum class MermaidSequenceNodePosition(
	val text: String
) {
	RightOf("right of"),
	LeftOf("left of"),
	Over("over")
}

//REGION Config object

/**Mermaid序列图的配置。*/
object MermaidSequenceConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}
