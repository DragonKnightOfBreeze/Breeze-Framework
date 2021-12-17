// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidStateDiagramDslExtensions")

package icu.windea.breezeframework.dsl.mermaid.statediagram

import icu.windea.breezeframework.dsl.mermaid.*
import icu.windea.breezeframework.dsl.mermaid.statediagram.MermaidStateDiagramDsl.*

/**
 * 开始构建[MermaidStateDiagramDsl]。
 */
@MermaidStateDiagramDslMarker
inline fun mermaidStateDiagramDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

@MermaidStateDiagramDslMarker
fun DslEntry.initState(): String {
	return "[*]"
}

@MermaidStateDiagramDslMarker
fun DslEntry.finishState(): String {
	return "[*]"
}

@MermaidStateDiagramDslMarker
@MermaidDslExtendedFeature
fun DslEntry.anyState(): String {
	return "<Any State>"
}

@MermaidStateDiagramDslMarker
fun DslEntry.leftOf(stateName: String): NoteLocation {
	return NoteLocation(NotePosition.LeftOf, stateName)
}

@MermaidStateDiagramDslMarker
fun DslEntry.rightOf(stateName: String): NoteLocation {
	return NoteLocation(NotePosition.RightOf, stateName)
}

@MermaidStateDiagramDslMarker
fun DslEntry.state(name: String): SimpleState {
	return SimpleState(name).also { states += it }
}

@MermaidStateDiagramDslMarker
inline fun DslEntry.compositedState(name: String, block: CompositedState.() -> Unit): CompositedState {
	return CompositedState(name).apply(block).also { states += it }
}

@MermaidStateDiagramDslMarker
inline fun DslEntry.concurrentState(name: String, block: ConcurrentState.() -> Unit): ConcurrentState {
	return ConcurrentState(name).apply(block).also { states += it }
}

@MermaidStateDiagramDslMarker
fun DslEntry.transition(fromStateId: String, toStateId: String): Transition {
	return Transition(fromStateId, toStateId).also { links += it }
}

@MermaidStateDiagramDslMarker
fun DslEntry.note(location: NoteLocation, text: String = ""): Note {
	return Note(location, text).also { notes += it }
}

@MermaidStateDiagramDslMarker
infix fun State.text(text: String): State {
	return apply { this.text = text }
}

@MermaidStateDiagramDslMarker
infix fun SimpleState.type(type: StateType): SimpleState {
	return apply { this.type = type }
}

@MermaidStateDiagramDslMarker
inline fun ConcurrentState.section(block: ConcurrentSection.() -> Unit): ConcurrentSection {
	return ConcurrentSection().apply(block).also { sections += it }
}

@MermaidStateDiagramDslMarker
infix fun Transition.text(text: String): Transition {
	return apply { this.text = text }
}

@MermaidStateDiagramDslMarker
infix fun Note.text(text: String): Note {
	return apply { this.text = text }
}
