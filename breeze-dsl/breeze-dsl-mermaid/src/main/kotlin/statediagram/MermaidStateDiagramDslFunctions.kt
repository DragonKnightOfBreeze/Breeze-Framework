@file:Suppress("unused")

package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.statediagram.MermaidStateDiagram.*

@MermaidStateDiagramDsl
inline fun mermaidStateDiagram(block:Document.() -> Unit) = Document().apply(block)
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.initState() = "[*]"
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.finishState() = "[*]"
@MermaidStateDiagramDsl
@MermaidExtendedFeature
fun MermaidStateDiagramDslEntry.anyState() = "<Any State>"
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.leftOf(stateName:String) = NoteLocation(NotePosition.LeftOf, stateName)
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.rightOf(stateName:String) = NoteLocation(NotePosition.RightOf, stateName)
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.state(name:String) =
	SimpleState(name).also { states += it }
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.compositedState(name:String, block:CompositedState.() -> Unit) =
	CompositedState(name).apply(block).also { states += it }
@MermaidStateDiagramDsl
inline fun MermaidStateDiagramDslEntry.concurrentState(name:String, block:ConcurrentState.() -> Unit) =
	ConcurrentState(name).apply(block).also { states += it }
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.transition(fromStateId:String, toStateId:String) =
	Transition(fromStateId, toStateId).also { links += it }
@MermaidStateDiagramDsl
fun MermaidStateDiagramDslEntry.note(location:NoteLocation, text:String = "") =
	Note(location, text).also { notes += it }
@MermaidStateDiagramDsl
infix fun State.text(text:String) =
	apply { this.text = text }
@MermaidStateDiagramDsl
infix fun SimpleState.type(type:StateType) =
	apply { this.type = type }
@MermaidStateDiagramDsl
inline fun ConcurrentState.section(block:ConcurrentSection.() -> Unit) =
	ConcurrentSection().apply(block).also { sections += it }
@MermaidStateDiagramDsl
infix fun Transition.text(text:String) =
	apply { this.text = text }
@MermaidStateDiagramDsl
infix fun Note.text(text:String) =
	apply { this.text = text }
