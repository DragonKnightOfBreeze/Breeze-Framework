/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid.classdiagram

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDslDefinitions.*

/**
 * [Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram) dsl.
 */
@MermaidClassDiagramDslMarker
class MermaidClassDiagramDsl @PublishedApi internal constructor() : MermaidDsl(), IDslEntry, Indentable {
	override val classes: MutableSet<MermaidClassDiagramDslDefinitions.Class> = mutableSetOf()
	override val relations: MutableList<MermaidClassDiagramDslDefinitions.Relation> = mutableListOf()
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = toContentString().doIndent(MermaidDslConfig.indent)
		return "classDiagram${DslConstants.ls} $contentSnippet"
	}
}
