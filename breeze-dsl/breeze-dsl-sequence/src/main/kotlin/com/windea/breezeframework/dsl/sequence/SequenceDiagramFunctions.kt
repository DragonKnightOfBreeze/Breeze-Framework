@file:Suppress("unused")

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.sequence.SequenceDiagram.*

@TopDslFunction
@SequenceDiagramDsl
inline fun sequenceDiagram(block:Document.() -> Unit) = Document().apply(block)


@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.leftOf(participantId:String) = NoteLocation(NotePosition.LeftOf, participantId)

@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.rightOf(participantId:String) = NoteLocation(NotePosition.RightOf, participantId)

@InlineDslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.over(participantId1:String, participantId2:String) = NoteLocation(NotePosition.Over, participantId1, participantId2)


@DslFunction
@SequenceDiagramDsl
fun Document.title(text:String) =
	Title(text).also { title = it }

@DslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.participant(name:String) =
	Participant(name).also { participants += it }

@DslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.message(fromParticipantId:String, toParticipantId:String) =
	Message(fromParticipantId, toParticipantId).also { messages += it }

@DslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.message(
	fromParticipantId:String,
	arrowShape:ArrowShape,
	toParticipantId:String
) = Message(fromParticipantId, toParticipantId)
	.apply { this.arrowShape = arrowShape }
	.also { messages += it }

@DslFunction
@SequenceDiagramDsl
fun SequenceDiagramDslEntry.note(location:NoteLocation) =
	Note(location).also { notes += it }

@DslFunction
@SequenceDiagramDsl
infix fun Participant.alias(alias:String) =
	apply { this.alias = alias }

@DslFunction
@SequenceDiagramDsl
infix fun Message.text(text:String) =
	apply { this.text = text }

@DslFunction
@SequenceDiagramDsl
infix fun Message.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }

@DslFunction
@SequenceDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }

