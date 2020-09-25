/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.statediagram.MermaidStateDiagramDslDefinitions.*

/**
 * [Mermaid State Diagram](https://mermaidjs.github.io/#/stateDiagram) dsl.
 */
@MermaidStateDiagramDslMarker
class MermaidStateDiagramDsl @PublishedApi internal constructor() : MermaidDsl(), IDslEntry, Indentable {
	override val states: MutableSet<State> = mutableSetOf()
	override val links: MutableList<Transition> = mutableListOf()
	override val notes: MutableList<Note> = mutableListOf()
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
		return "stateDiagram${DslConstants.ls}$contentSnippet"
	}
}
