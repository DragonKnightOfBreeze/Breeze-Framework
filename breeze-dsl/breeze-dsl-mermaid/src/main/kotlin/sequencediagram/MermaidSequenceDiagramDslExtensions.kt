// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidSequenceDiagramDslExtensions")

package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.dsl.mermaid.sequencediagram.MermaidSequenceDiagramDsl.*

/**
 * 开始构建[MermaidSequenceDiagramDsl]。
 */
@MermaidSequenceDiagramDslMarker
fun mermaidSequenceDiagramDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.leftOf(participantId: String): NoteLocation {
	return NoteLocation(NotePosition.LeftOf, participantId, null)
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.rightOf(participantId: String): NoteLocation {
	return NoteLocation(NotePosition.RightOf, participantId, null)
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.over(participantId1: String, participantId2: String): NoteLocation {
	return NoteLocation(NotePosition.Over, participantId1, participantId2)
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.participant(name: String): Participant {
	return Participant(name).also { participants += it }
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.message(fromParticipantId: String, toParticipantId: String): Message {
	return Message(fromParticipantId, toParticipantId).also { messages += it }
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.message(fromParticipantId: String, arrowShape: ArrowShape, isActivated: Boolean = false, toParticipantId: String): Message {
	return Message(fromParticipantId, toParticipantId).apply { this.arrowShape = arrowShape; this.isActivated = isActivated }.also { messages += it }
}

@MermaidSequenceDiagramDslMarker
fun DslEntry.note(location: NoteLocation, text: String): Note {
	return Note(location, text).also { notes += it }
}

@MermaidSequenceDiagramDslMarker
inline fun DslEntry.loop(text: String, block: Loop.() -> Unit): Loop {
	return Loop(text).apply(block).also { scopes += it }
}

@MermaidSequenceDiagramDslMarker
inline fun DslEntry.opt(text: String, block: Optional.() -> Unit): Optional {
	return Optional(text).apply(block).also { scopes += it }
}

@MermaidSequenceDiagramDslMarker
inline fun DslEntry.alt(text: String, block: Alternative.() -> Unit): Alternative {
	return Alternative(text).apply(block).also { scopes += it }
}

@MermaidSequenceDiagramDslMarker
inline fun DslEntry.highlight(text: String, block: Highlight.() -> Unit): Highlight {
	return Highlight(text).apply(block).also { scopes += it }
}

@MermaidSequenceDiagramDslMarker
infix fun Participant.alias(alias: String): Participant {
	return apply { this.alias = alias }
}

@MermaidSequenceDiagramDslMarker
infix fun Message.text(text: String): Message {
	return apply { this.text = text }
}

@MermaidSequenceDiagramDslMarker
infix fun Message.arrowShape(arrowShape: ArrowShape): Message {
	return apply { this.arrowShape = arrowShape }
}

@MermaidSequenceDiagramDslMarker
infix fun Message.activate(isActivated: Boolean): Message {
	return apply { this.isActivated = isActivated }
}

@MermaidSequenceDiagramDslMarker
infix fun Note.text(text: String): Note {
	return apply { this.text = text }
}

@MermaidSequenceDiagramDslMarker
fun Alternative.`else`(text: String): Else {
	return Else(text).also { elseScopes += it }
}

@MermaidSequenceDiagramDslMarker
fun Alternative.`else`(): Else {
	return Else().also { elseScopes += it }
}
