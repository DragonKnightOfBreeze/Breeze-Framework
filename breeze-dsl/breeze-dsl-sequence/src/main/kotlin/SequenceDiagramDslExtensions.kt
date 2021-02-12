// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SequenceDiagramDslExtensions")

package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.dsl.sequence.SequenceDiagramDsl.*

/**
 * 开始构建[SequenceDiagramDsl]。
 */
@SequenceDiagramDslMarker
inline fun sequenceDiagramDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

/**
 * 创建一个方位为[SequenceDiagramDsl.NotePosition.LeftOf]的[SequenceDiagramDsl.NoteLocation]。
 */
@SequenceDiagramDslMarker
fun DslEntry.leftOf(participantId: String): NoteLocation {
	return NoteLocation(NotePosition.LeftOf, participantId)
}

/**
 * 创建一个方位为[SequenceDiagramDsl.NotePosition.RightOf]的[SequenceDiagramDsl.NoteLocation]。
 */
@SequenceDiagramDslMarker
fun DslEntry.rightOf(participantId: String): NoteLocation {
	return NoteLocation(NotePosition.RightOf, participantId)
}

/**
 * 创建一个方位为[SequenceDiagramDsl.NotePosition.Over]的[SequenceDiagramDsl.NoteLocation]。
 */
@SequenceDiagramDslMarker
fun DslEntry.over(participantId1: String, participantId2: String): NoteLocation {
	return NoteLocation(NotePosition.Over, participantId1, participantId2)
}

/**
 * 创建一个[SequenceDiagramDsl.Title]并注册。
 */
@SequenceDiagramDslMarker
fun DslDocument.title(text: String): Title {
	return Title(text).also { title = it }
}

/**
 * 创建一个[SequenceDiagramDsl.Participant]并注册。
 */
@SequenceDiagramDslMarker
fun DslEntry.participant(name: String): Participant {
	return Participant(name).also { participants += it }
}

/**
 * 创建一个[SequenceDiagramDsl.Message]并注册。
 */
@SequenceDiagramDslMarker
fun DslEntry.message(fromParticipantId: String, toParticipantId: String): Message {
	return Message(fromParticipantId, toParticipantId).also { messages += it }
}

/**
 * 创建一个[SequenceDiagramDsl.Message]并注册。
 */
@SequenceDiagramDslMarker
fun DslEntry.message(fromParticipantId: String, arrowShape: ArrowShape, toParticipantId: String): Message {
	return Message(fromParticipantId, toParticipantId).apply { this.arrowShape = arrowShape }.also { messages += it }
}

/**
 * 创建一个[SequenceDiagramDsl.Note]并注册。
 */
@SequenceDiagramDslMarker
fun DslEntry.note(location: NoteLocation) = Note(location).also { notes += it }

/**
 * 配置[SequenceDiagramDsl.Participant]的别名。
 */
@SequenceDiagramDslMarker
infix fun Participant.alias(alias: String): Participant {
	return apply { this.alias = alias }
}

/**
 * 配置[SequenceDiagramDsl.Message]的文本。
 */
@SequenceDiagramDslMarker
infix fun Message.text(text: String): Message {
	return apply { this.text = text }
}

/**
 * 配置[SequenceDiagramDsl.Message]的箭头形状。
 */
@SequenceDiagramDslMarker
infix fun Message.arrowShape(arrowShape: ArrowShape): Message {
	return apply { this.arrowShape = arrowShape }
}

/**
 * 配置[SequenceDiagramDsl.Note]的文本。
 */
@SequenceDiagramDslMarker
infix fun Note.text(text: String): Note {
	return apply { this.text = text }
}

