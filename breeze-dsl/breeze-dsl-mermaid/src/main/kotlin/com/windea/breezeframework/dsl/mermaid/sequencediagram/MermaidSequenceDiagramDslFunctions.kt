@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.sequencediagram.MermaidSequenceDiagram.*
import com.windea.breezeframework.dsl.mermaid.statediagram.*


@TopDslFunction
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(block:Document.() -> Unit) = Document().apply(block)


@InlineDslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.leftOf(participantId:String) = NoteLocation(NotePosition.LeftOf, participantId, null)

@InlineDslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.rightOf(participantId:String) = NoteLocation(NotePosition.RightOf, participantId, null)

@InlineDslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.over(participantId1:String, participantId2:String) = NoteLocation(NotePosition.Over, participantId1, participantId2)


@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.participant(name:String) =
	Participant(name).also { participants += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.message(fromParticipantId:String, toParticipantId:String) =
	Message(fromParticipantId, toParticipantId).also { messages += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.message(
	fromParticipantId:String,
	arrowShape:ArrowShape,
	isActivated:Boolean = false,
	toParticipantId:String
) = Message(fromParticipantId, toParticipantId)
	.apply { this.arrowShape = arrowShape }
	.apply { this.isActivated = isActivated }
	.also { messages += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.note(location:NoteLocation, text:String) =
	Note(location, text).also { notes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.loop(text:String, block:Loop.() -> Unit) =
	Loop(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.opt(text:String, block:Optional.() -> Unit) =
	Optional(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.alt(text:String, block:Alternative.() -> Unit) =
	Alternative(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.highlight(text:String, block:Highlight.() -> Unit) =
	Highlight(text).apply(block).also { scopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Participant.alias(alias:String) =
	apply { this.alias = alias }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.text(text:String) =
	apply { this.text = text }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }

@DslFunction
@MermaidSequenceDiagramDsl
infix fun Message.activate(isActivated:Boolean) =
	apply { this.isActivated = isActivated }

@MermaidStateDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }

@DslFunction
@MermaidSequenceDiagramDsl
fun Alternative.`else`(text:String) =
	Else(text).also { elseScopes += it }

@DslFunction
@MermaidSequenceDiagramDsl
fun Alternative.`else`() =
	Else().also { elseScopes += it }
