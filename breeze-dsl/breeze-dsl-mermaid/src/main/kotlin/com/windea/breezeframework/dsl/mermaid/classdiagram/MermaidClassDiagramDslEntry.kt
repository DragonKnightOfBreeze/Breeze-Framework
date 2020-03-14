package com.windea.breezeframework.dsl.mermaid.classdiagram

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid类图领域特定语言的入口。
 * @property classes 类一览。忽略重复的元素。
 * @property relations 关系一览。
 */
@MermaidClassDiagramDsl
interface MermaidClassDiagramDslEntry : MermaidDslEntry, CanSplitLine, WithTransition<MermaidClassDiagram.Class, MermaidClassDiagram.Relation> {
	val classes:MutableSet<MermaidClassDiagram.Class>
	val relations:MutableList<MermaidClassDiagram.Relation>

	override fun contentString() = buildString {
		if(classes.isNotEmpty()) appendJoin(classes, SystemProperties.lineSeparator).append(splitSeparator)
		if(relations.isNotEmpty()) appendJoin(classes, SystemProperties.lineSeparator).append(splitSeparator)
	}.trimEnd()

	@DslFunction
	@MermaidClassDiagramDsl
	override fun String.links(other:String) = relation(this, other, MermaidClassDiagram.RelationType.Link)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.inheritedBy(other:String) = relation(this, other, MermaidClassDiagram.RelationType.Inheritance)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.composedBy(other:String) = relation(this, other, MermaidClassDiagram.RelationType.Composition)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.aggregatedBy(other:String) = relation(this, other, MermaidClassDiagram.RelationType.Aggregation)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.associatedBy(other:String) = relation(this, other, MermaidClassDiagram.RelationType.Association)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.inherits(other:String) = relation(this, other, MermaidClassDiagram.RelationType.ReversedInheritance)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.composes(other:String) = relation(this, other, MermaidClassDiagram.RelationType.ReversedComposition)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.aggregates(other:String) = relation(this, other, MermaidClassDiagram.RelationType.ReversedAggregation)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.associates(other:String) = relation(this, other, MermaidClassDiagram.RelationType.ReversedAssociation)
}
