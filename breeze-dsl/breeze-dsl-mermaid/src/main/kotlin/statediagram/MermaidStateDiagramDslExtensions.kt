@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.statediagram.MermaidStateDiagramDslDefinitions.*

@MermaidStateDiagramDslMarker
inline fun mermaidStateDiagramDsl(block: MermaidStateDiagramDsl.() -> Unit) = MermaidStateDiagramDsl().apply(block)


@MermaidStateDiagramDslMarker
fun IDslEntry.initState() = "[*]"

@MermaidStateDiagramDslMarker
fun IDslEntry.finishState() = "[*]"

@MermaidStateDiagramDslMarker
@MermaidExtendedFeature
fun IDslEntry.anyState() = "<Any State>"

@MermaidStateDiagramDslMarker
fun IDslEntry.leftOf(stateName: String) = NoteLocation(NotePosition.LeftOf, stateName)

@MermaidStateDiagramDslMarker
fun IDslEntry.rightOf(stateName: String) = NoteLocation(NotePosition.RightOf, stateName)

@MermaidStateDiagramDslMarker
fun IDslEntry.state(name: String) = SimpleState(name).also { states += it }

@MermaidStateDiagramDslMarker
inline fun IDslEntry.compositedState(
	name: String,
	block: CompositedState.() -> Unit
) = CompositedState(name).apply(block).also { states += it }

@MermaidStateDiagramDslMarker
inline fun IDslEntry.concurrentState(
	name: String,
	block: ConcurrentState.() -> Unit
) = ConcurrentState(name).apply(block).also { states += it }

@MermaidStateDiagramDslMarker
fun IDslEntry.transition(fromStateId: String, toStateId: String) = Transition(fromStateId, toStateId).also { links += it }

@MermaidStateDiagramDslMarker
fun IDslEntry.note(location: NoteLocation, text: String = "") = Note(location, text).also { notes += it }

@MermaidStateDiagramDslMarker
infix fun State.text(text: String) = apply { this.text = text }

@MermaidStateDiagramDslMarker
infix fun SimpleState.type(type: StateType) = apply { this.type = type }

@MermaidStateDiagramDslMarker
inline fun ConcurrentState.section(block: ConcurrentSection.() -> Unit) = ConcurrentSection().apply(block).also { sections += it }

@MermaidStateDiagramDslMarker
infix fun Transition.text(text: String) = apply { this.text = text }

@MermaidStateDiagramDslMarker
infix fun Note.text(text: String) = apply { this.text = text }
