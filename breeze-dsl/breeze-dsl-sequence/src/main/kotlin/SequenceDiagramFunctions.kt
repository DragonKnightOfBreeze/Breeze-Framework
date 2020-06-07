@file:Suppress("unused")

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.sequence.SequenceDiagram.*
@SequenceDiagramDsl
inline fun sequenceDiagram(block:Document.() -> Unit) = Document().apply(block)
@SequenceDiagramDsl
fun IDslEntry.leftOf(participantId:String) = NoteLocation(NotePosition.LeftOf, participantId)
@SequenceDiagramDsl
fun IDslEntry.rightOf(participantId:String) = NoteLocation(NotePosition.RightOf, participantId)
@SequenceDiagramDsl
fun IDslEntry.over(participantId1:String, participantId2:String) = NoteLocation(NotePosition.Over, participantId1, participantId2)
@SequenceDiagramDsl
fun Document.title(text:String) =
	Title(text).also { title = it }
@SequenceDiagramDsl
fun IDslEntry.participant(name:String) =
	Participant(name).also { participants += it }
@SequenceDiagramDsl
fun IDslEntry.message(fromParticipantId:String, toParticipantId:String) =
	Message(fromParticipantId, toParticipantId).also { messages += it }
@SequenceDiagramDsl
fun IDslEntry.message(
	fromParticipantId:String,
	arrowShape:ArrowShape,
	toParticipantId:String
) = Message(fromParticipantId, toParticipantId)
	.apply { this.arrowShape = arrowShape }
	.also { messages += it }
@SequenceDiagramDsl
fun IDslEntry.note(location:NoteLocation) =
	Note(location).also { notes += it }
@SequenceDiagramDsl
infix fun Participant.alias(alias:String) =
	apply { this.alias = alias }
@SequenceDiagramDsl
infix fun Message.text(text:String) =
	apply { this.text = text }
@SequenceDiagramDsl
infix fun Message.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }
@SequenceDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }

