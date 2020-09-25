/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.dsl.mermaid.sequencediagram.MermaidSequenceDiagramDslDefinitions.*
import com.windea.breezeframework.dsl.mermaid.statediagram.*

@MermaidSequenceDiagramDslMarker
fun mermaidSequenceDiagramDsl(block: MermaidSequenceDiagramDsl.() -> Unit) = MermaidSequenceDiagramDsl().apply(block)


@MermaidSequenceDiagramDslMarker
fun IDslEntry.leftOf(participantId: String) = NoteLocation(NotePosition.LeftOf, participantId, null)

@MermaidSequenceDiagramDslMarker
fun IDslEntry.rightOf(participantId: String) = NoteLocation(NotePosition.RightOf, participantId, null)

@MermaidSequenceDiagramDslMarker
fun IDslEntry.over(participantId1: String, participantId2: String) = NoteLocation(NotePosition.Over, participantId1, participantId2)

@MermaidSequenceDiagramDslMarker
fun IDslEntry.participant(name: String) = Participant(name).also { participants += it }

@MermaidSequenceDiagramDslMarker
fun IDslEntry.message(
	fromParticipantId: String,
	toParticipantId: String
) = Message(fromParticipantId, toParticipantId).also { messages += it }

@MermaidSequenceDiagramDslMarker
fun IDslEntry.message(
	fromParticipantId: String,
	arrowShape: ArrowShape,
	isActivated: Boolean = false,
	toParticipantId: String
) = Message(fromParticipantId, toParticipantId).apply { this.arrowShape = arrowShape; this.isActivated = isActivated }.also { messages += it }

@MermaidSequenceDiagramDslMarker
fun IDslEntry.note(location: NoteLocation, text: String) = Note(location, text).also { notes += it }

@MermaidSequenceDiagramDslMarker
inline fun IDslEntry.loop(text: String, block: Loop.() -> Unit) = Loop(text).apply(block).also { scopes += it }

@MermaidSequenceDiagramDslMarker
inline fun IDslEntry.opt(text: String, block: Optional.() -> Unit) = Optional(text).apply(block).also { scopes += it }

@MermaidSequenceDiagramDslMarker
inline fun IDslEntry.alt(text: String, block: Alternative.() -> Unit) = Alternative(text).apply(block).also { scopes += it }

@MermaidSequenceDiagramDslMarker
inline fun IDslEntry.highlight(text: String, block: Highlight.() -> Unit) = Highlight(text).apply(block).also { scopes += it }

@MermaidSequenceDiagramDslMarker
infix fun Participant.alias(alias: String) = apply { this.alias = alias }

@MermaidSequenceDiagramDslMarker
infix fun Message.text(text: String) = apply { this.text = text }

@MermaidSequenceDiagramDslMarker
infix fun Message.arrowShape(arrowShape: ArrowShape) = apply { this.arrowShape = arrowShape }

@MermaidSequenceDiagramDslMarker
infix fun Message.activate(isActivated: Boolean) = apply { this.isActivated = isActivated }

@MermaidStateDiagramDslMarker
infix fun Note.text(text: String) = apply { this.text = text }

@MermaidSequenceDiagramDslMarker
fun Alternative.`else`(text: String) = Else(text).also { elseScopes += it }

@MermaidSequenceDiagramDslMarker
fun Alternative.`else`() = Else().also { elseScopes += it }
