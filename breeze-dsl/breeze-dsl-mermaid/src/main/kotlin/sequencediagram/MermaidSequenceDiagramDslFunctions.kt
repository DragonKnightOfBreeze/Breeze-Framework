@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.sequencediagram.MermaidSequenceDiagram.*
import com.windea.breezeframework.dsl.mermaid.statediagram.*
@MermaidSequenceDiagramDsl
fun mermaidSequenceDiagram(block:Document.() -> Unit) = Document().apply(block)
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.leftOf(participantId:String) = NoteLocation(NotePosition.LeftOf, participantId, null)
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.rightOf(participantId:String) = NoteLocation(NotePosition.RightOf, participantId, null)
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.over(participantId1:String, participantId2:String) = NoteLocation(NotePosition.Over, participantId1, participantId2)
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.participant(name:String) =
	Participant(name).also { participants += it }
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.message(fromParticipantId:String, toParticipantId:String) =
	Message(fromParticipantId, toParticipantId).also { messages += it }
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
@MermaidSequenceDiagramDsl
fun MermaidSequenceDiagramDslEntry.note(location:NoteLocation, text:String) =
	Note(location, text).also { notes += it }
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.loop(text:String, block:Loop.() -> Unit) =
	Loop(text).apply(block).also { scopes += it }
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.opt(text:String, block:Optional.() -> Unit) =
	Optional(text).apply(block).also { scopes += it }
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.alt(text:String, block:Alternative.() -> Unit) =
	Alternative(text).apply(block).also { scopes += it }
@MermaidSequenceDiagramDsl
inline fun MermaidSequenceDiagramDslEntry.highlight(text:String, block:Highlight.() -> Unit) =
	Highlight(text).apply(block).also { scopes += it }
@MermaidSequenceDiagramDsl
infix fun Participant.alias(alias:String) =
	apply { this.alias = alias }
@MermaidSequenceDiagramDsl
infix fun Message.text(text:String) =
	apply { this.text = text }
@MermaidSequenceDiagramDsl
infix fun Message.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }
@MermaidSequenceDiagramDsl
infix fun Message.activate(isActivated:Boolean) =
	apply { this.isActivated = isActivated }

@MermaidStateDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }
@MermaidSequenceDiagramDsl
fun Alternative.`else`(text:String) =
	Else(text).also { elseScopes += it }
@MermaidSequenceDiagramDsl
fun Alternative.`else`() =
	Else().also { elseScopes += it }
