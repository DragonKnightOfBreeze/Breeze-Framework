@file:Suppress("NOTHING_TO_INLINE", "unused", "GrazieInspection")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config

//unstable raw api

//region dsl top declarations
/**Mermaid类图的Dsl。*/
@Reference("[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidClassDiagramDsl

/**Mermaid类图。*/
@Reference("[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)")
@MermaidClassDiagramDsl
class MermaidClassDiagram @PublishedApi internal constructor() : Mermaid(), MermaidClassDiagramEntry, CanIndent {
	override val classes: MutableSet<MermaidClassDiagramClass> = mutableSetOf()
	override val relations: MutableList<MermaidClassDiagramRelation> = mutableListOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = contentString().applyIndent(config.indent)
		return "classDiagram\n$contentSnippet"
	}
}
//endregion

//region dsl declarations
/**Mermaid类图Dsl元素的入口。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramEntry : MermaidEntry, CanSplitLine,
	WithTransition<MermaidClassDiagramClass, MermaidClassDiagramRelation> {
	val classes: MutableSet<MermaidClassDiagramClass>
	val relations: MutableList<MermaidClassDiagramRelation>

	override fun contentString(): String {
		return listOfNotNull(
			classes.orNull()?.joinToString("\n"),
			relations.orNull()?.joinToString("\n")
		).joinToString(splitSeparator)
	}

	@MermaidClassDiagramDsl
	override fun String.links(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Link)

	@MermaidClassDiagramDsl
	infix fun String.inheritedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Inheritance)

	@MermaidClassDiagramDsl
	infix fun String.composedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Composition)

	@MermaidClassDiagramDsl
	infix fun String.aggregatedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Aggregation)

	@MermaidClassDiagramDsl
	infix fun String.associatedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Association)

	@MermaidClassDiagramDsl
	infix fun String.inherits(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedInheritance)

	@MermaidClassDiagramDsl
	infix fun String.composes(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedComposition)

	@MermaidClassDiagramDsl
	infix fun String.aggregates(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedAggregation)

	@MermaidClassDiagramDsl
	infix fun String.associates(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedAssociation)
}

/**Mermaid类图Dsl元素。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramElement : MermaidElement
//endregion

//region dsl elements
/**Mermaid类图类。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramClass @PublishedApi internal constructor(
	val name: String
) : MermaidClassDiagramElement, CanIndent, WithUniqueId {
	var annotation: MermaidClassDiagramAnnotation? = null
	val statements: MutableList<MermaidClassDiagramStatement> = mutableListOf()

	override var indentContent: Boolean = true

	override val id: String get() = name

	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString(): String {
		val contentSnippet = listOfNotNull(
			annotation?.toString(),
			statements.orNull()?.joinToString("\n")
		).orNull()?.joinToString("\n")?.applyIndent(config.indent).orEmpty()
		if(contentSnippet.isEmpty()) return "class $name"
		return "class $name {\n$contentSnippet\n}"
	}

	@InlineDslFunction
	@MermaidClassDiagramDsl
	operator fun String.invoke(vararg params: String) = "$this(${params.joinToString()})"

	@InlineDslFunction
	@MermaidClassDiagramDsl
	infix fun String.type(type: String) = "$this: $type"
}

/**Mermaid类图注解。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramAnnotation @PublishedApi internal constructor(
	val name: String
) : MermaidClassDiagramElement {
	override fun toString(): String {
		return "<<$name>>"
	}

	/**Mermaid类图注解的类型。*/
	@MermaidClassDiagramDsl
	enum class Type(val text: String) {
		Interface("interface"), Abstract("abstract"), Service("service"), Enumeration("enumeration")
	}
}

/**Mermaid类图声明。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramStatement @PublishedApi internal constructor(
	val expression: String
) : MermaidClassDiagramElement {
	var visibility: Visibility = Visibility.None

	override fun toString(): String {
		return "${visibility.text}$expression"
	}

	/**Mermaid类图声明的可见性。*/
	@MermaidClassDiagramDsl
	enum class Visibility(val text: String) {
		None(""), Public("+"), Private("-"), Protected("#"), Package("~")
	}
}

/**Mermaid类图关系。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramRelation @PublishedApi internal constructor(
	val fromClassId: String,
	val toClassId: String,
	val type: Type
) : MermaidClassDiagramElement, WithNode<MermaidClassDiagramClass> {
	var text: String? = null //换行符：<br>

	//syntax: 0..1, 1, 0..*, 1..*, n, 0..n, 1..n
	var fromCardinality: String? = null
	var toCardinality: String? = null

	override val sourceNodeId get() = fromClassId
	override val targetNodeId get() = toClassId

	//syntax: $fromClassId $fromCardinality? $relationType $toCardinality? $toClassId: $text?
	override fun toString(): String {
		return listOfNotNull(
			fromClassId, fromCardinality?.quote(config.quote), type.text, toCardinality?.quote(config.quote), toClassId
		).orNull()?.joinToString(" ", "", text?.let { ": $it" }.orEmpty()).orEmpty()
	}

	/**Mermaid类图关系的类型。*/
	@MermaidClassDiagramDsl
	enum class Type(val text: String) {
		//do not allow bidirectional arrows
		Link("--"),
		Inheritance("<|--"), Composition("*--"), Aggregation("o--"), Association("<--"),
		ReversedInheritance("--|>"), ReversedComposition("--*"), ReversedAggregation("--o"), ReversedAssociation("-->")
	}
}
//endregion

//region dsl build extensions
@MermaidClassDiagramDsl
inline fun mermaidClassDiagram(block: MermaidClassDiagram.() -> Unit) =
	MermaidClassDiagram().apply(block)

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramEntry.`class`(name: String, block: MermaidClassDiagramClass.() -> Unit = {}) =
	MermaidClassDiagramClass(name).apply(block).also { classes += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramEntry.relation(fromClassId: String, toClassId: String, type: MermaidClassDiagramRelation.Type) =
	MermaidClassDiagramRelation(fromClassId, toClassId, type).also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramEntry.relation(
	fromClassId: String,
	fromCardinality: String?,
	type: MermaidClassDiagramRelation.Type,
	toCardinality: String?,
	toClassId: String
) = MermaidClassDiagramRelation(fromClassId, toClassId, type)
	.also { it.fromCardinality = fromCardinality;it.toCardinality = toCardinality }
	.also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.annotation(name: String) =
	MermaidClassDiagramAnnotation(name).also { annotation = it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.annotation(type: MermaidClassDiagramAnnotation.Type) =
	MermaidClassDiagramAnnotation(type.text).also { annotation = it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.statement(expression: String) =
	MermaidClassDiagramStatement(expression).also { statements += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`public`(statement: MermaidClassDiagramStatement) =
	statement.also { it.visibility = MermaidClassDiagramStatement.Visibility.Public }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`private`(statement: MermaidClassDiagramStatement) =
	statement.also { it.visibility = MermaidClassDiagramStatement.Visibility.Private }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`protected`(statement: MermaidClassDiagramStatement) =
	statement.also { it.visibility = MermaidClassDiagramStatement.Visibility.Protected }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`package`(statement: MermaidClassDiagramStatement) =
	statement.also { it.visibility = MermaidClassDiagramStatement.Visibility.Package }

@MermaidClassDiagramDsl
inline infix fun MermaidClassDiagramRelation.text(text: String) =
	this.also { it.text = text }

@MermaidClassDiagramDsl
inline infix fun MermaidClassDiagramRelation.cardinality(cardinalityPair: Pair<String, String>) =
	this.also { it.fromCardinality = cardinalityPair.first;it.toCardinality = cardinalityPair.second }
//endregion
