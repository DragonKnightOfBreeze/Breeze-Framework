/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.sequencediagram.MermaidSequenceDiagramDslDefinitions.*

//can have a title by `title: inlineText`, but it is not introduced in official api

/**
 * [Mermaid Sequence Diagram](https://mermaidjs.github.io/#/sequenceDiagram) dsl.
 */
@MermaidSequenceDiagramDslMarker
class MermaidSequenceDiagramDsl @PublishedApi internal constructor() : MermaidDsl(), IDslEntry, Indentable {
	override val participants: MutableSet<Participant> = mutableSetOf()
	override val messages: MutableList<Message> = mutableListOf()
	override val notes: MutableList<Note> = mutableListOf()
	override val scopes: MutableList<Scope> = mutableListOf()
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
		return "sequenceDiagram${DslConstants.ls}$contentSnippet"
	}
}
