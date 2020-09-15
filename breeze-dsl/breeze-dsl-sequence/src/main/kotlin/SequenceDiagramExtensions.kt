/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:Suppress("unused")

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.dsl.sequence.SequenceDiagramDslDefinitions.*

@SequenceDiagramDslMarker
inline fun sequenceDiagramDsl(block: SequenceDiagramDsl.() -> Unit) = SequenceDiagramDsl().apply(block)


@SequenceDiagramDslMarker
fun IDslEntry.leftOf(participantId: String) = NoteLocation(NotePosition.LeftOf, participantId)

@SequenceDiagramDslMarker
fun IDslEntry.rightOf(participantId: String) = NoteLocation(NotePosition.RightOf, participantId)

@SequenceDiagramDslMarker
fun IDslEntry.over(participantId1: String, participantId2: String) = NoteLocation(NotePosition.Over, participantId1, participantId2)

@SequenceDiagramDslMarker
fun SequenceDiagramDsl.title(text: String) = Title(text).also { title = it }

@SequenceDiagramDslMarker
fun IDslEntry.participant(name: String) = Participant(name).also { participants += it }

@SequenceDiagramDslMarker
fun IDslEntry.message(
	fromParticipantId: String,
	toParticipantId: String
) = Message(fromParticipantId, toParticipantId).also { messages += it }

@SequenceDiagramDslMarker
fun IDslEntry.message(
	fromParticipantId: String,
	arrowShape: ArrowShape,
	toParticipantId: String
) = Message(fromParticipantId, toParticipantId).apply { this.arrowShape = arrowShape }.also { messages += it }

@SequenceDiagramDslMarker
fun IDslEntry.note(location: NoteLocation) = Note(location).also { notes += it }

@SequenceDiagramDslMarker
infix fun Participant.alias(alias: String) = apply { this.alias = alias }

@SequenceDiagramDslMarker
infix fun Message.text(text: String) = apply { this.text = text }

@SequenceDiagramDslMarker
infix fun Message.arrowShape(arrowShape: ArrowShape) = apply { this.arrowShape = arrowShape }

@SequenceDiagramDslMarker
infix fun Note.text(text: String) = apply { this.text = text }

