@file:Suppress("NOTHING_TO_INLINE", "unused", "GrazieInspection")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote

//NOTE unstable raw api

//region top annotations and interfaces
/**Mermaid类图的Dsl。*/
@ReferenceApi("[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidClassDiagramDsl

/**Mermaid类图。*/
@MermaidClassDiagramDsl
class MermaidClassDiagram @PublishedApi internal constructor() : Mermaid(), MermaidClassDiagramDslEntry, CanIndent {
	override val classes: MutableSet<MermaidClassDiagramClass> = mutableSetOf()
	override val relations: MutableList<MermaidClassDiagramRelation> = mutableListOf()
	
	override var indentContent: Boolean = true
	override var splitContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString().applyIndent(indent)
		return "classDiagram\n$contentSnippet"
	}
}
//endregion

//region dsl interfaces
/**Mermaid类图Dsl元素的入口。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramDslEntry : MermaidDslEntry, CanSplit,
	WithTransition<MermaidClassDiagramClass, MermaidClassDiagramRelation> {
	val classes: MutableSet<MermaidClassDiagramClass>
	val relations: MutableList<MermaidClassDiagramRelation>
	
	fun toContentString(): String {
		return arrayOf(
			classes.joinToStringOrEmpty("\n"),
			relations.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(split)
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Link)
	
	@GenericDsl
	infix fun String.inheritedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Inheritance)
	
	@GenericDsl
	infix fun String.composedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Composition)
	
	@GenericDsl
	infix fun String.aggregatedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Aggregation)
	
	@GenericDsl
	infix fun String.associatedBy(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.Association)
	
	@GenericDsl
	infix fun String.inherits(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedInheritance)
	
	@GenericDsl
	infix fun String.composes(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedComposition)
	
	@GenericDsl
	infix fun String.aggregates(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedAggregation)
	
	@GenericDsl
	infix fun String.associates(other: String) =
		relation(this, other, MermaidClassDiagramRelation.Type.ReversedAssociation)
}

/**Mermaid类图Dsl元素。*/
@MermaidClassDiagramDsl
interface MermaidClassDiagramDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid类图类。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramClass @PublishedApi internal constructor(
	val name: String
) : MermaidClassDiagramDslElement, CanIndent, WithUniqueId {
	var annotation: MermaidClassDiagramAnnotation? = null
	val properties: MutableSet<MermaidClassDiagramProperty> = mutableSetOf()
	val methods: MutableSet<MermaidClassDiagramMethod> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		val contentSnippet = arrayOf(
			annotation.toStringOrEmpty(),
			properties.joinToStringOrEmpty("\n"),
			methods.joinToStringOrEmpty("\n")
		).filterNotEmpty().ifEmpty { return "class $name" }.joinToStringOrEmpty("\n").applyIndent(indent)
		return "class $name {\n$contentSnippet\n}"
	}
	
	@Deprecated("""Use kotlin-style build extensions. e,g: public(property("name")) type "String".""", ReplaceWith(""))
	@MermaidClassDiagramDsl
	operator fun String.invoke(member: MermaidClassDiagramMember) = member.also { it.type = this }
	
	@MermaidClassDiagramDsl
	@Suppress("DEPRECATION")
	operator fun String.invoke(vararg params: String) = method(this, *params)
}

/**Mermaid类图注解。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramAnnotation @PublishedApi internal constructor(
	val name: String
) : MermaidClassDiagramDslElement, WithId {
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		return "<<$name>>"
	}
	
	/**Mermaid类图注解的类型。*/
	@MermaidClassDiagramDsl
	enum class Type(val text: String) {
		Interface("interface"), Abstract("abstract"), Service("service"), Enumeration("enumeration")
	}
}

/**Mermaid类图成员。*/
@MermaidClassDiagramDsl
sealed class MermaidClassDiagramMember(
	val name: String
) : MermaidClassDiagramDslElement, WithUniqueId {
	var visibility: Visibility? = null
	var type: String? = null
	
	/**Mermaid类图成员的可见性。*/
	@MermaidClassDiagramDsl
	enum class Visibility(val text: String) {
		Public("+"), Private("-"), Protected("#"), Package("~")
	}
}

/**Mermaid类图属性。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramProperty @PublishedApi internal constructor(
	name: String
) : MermaidClassDiagramMember(name) {
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		return "${visibility?.text.orEmpty()}${type?.let { "$it " }.orEmpty()}$name"
	}
}

/**Mermaid类图方法。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramMethod @PublishedApi internal constructor(
	name: String,
	val params: Array<out String>
) : MermaidClassDiagramMember(name) {
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		return "${visibility?.text.orEmpty()}${type?.let { "$it " }.orEmpty()}$name(${params.joinToStringOrEmpty()})"
	}
}

/**Mermaid类图关系。*/
@MermaidClassDiagramDsl
class MermaidClassDiagramRelation @PublishedApi internal constructor(
	val fromClassId: String,
	val toClassId: String,
	val type: Type
) : MermaidClassDiagramDslElement, WithNode<MermaidClassDiagramClass> {
	@Multiline("<br>")
	var text: String? = null
	//NOTE syntax: 0..1, 1, 0..*, 1..*, n, 0..n, 1..n
	var fromCardinality: String? = null
	var toCardinality: String? = null
	
	override val sourceNodeId get() = fromClassId
	override val targetNodeId get() = toClassId
	
	//NOTE syntax: $fromClassId $fromCardinality? $relationType $toCardinality? $toClassId: $text?
	override fun toString(): String {
		return arrayOf(
			fromClassId, fromCardinality?.wrapQuote(quote), type.text, toCardinality?.wrapQuote(quote), toClassId
		).filterNotNull().joinToStringOrEmpty(" ", "", text?.let { ": $it" }.orEmpty())
	}
	
	/**Mermaid类图关系的类型。*/
	@MermaidClassDiagramDsl
	enum class Type(val text: String) {
		//NOTE do not allow bidirectional arrows
		Link("--"),
		Inheritance("<|--"), Composition("*--"), Aggregation("o--"), Association("<--"),
		ReversedInheritance("--|>"), ReversedComposition("--*"), ReversedAggregation("--o"), ReversedAssociation("-->")
	}
}
//endregion

//region build extensions
@MermaidClassDiagramDsl
inline fun mermaidClassDiagram(block: MermaidClassDiagram.() -> Unit) =
	MermaidClassDiagram().also { it.block() }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.`class`(name: String, block: MermaidClassDiagramClass.() -> Unit = {}) =
	MermaidClassDiagramClass(name).also { it.block() }.also { classes += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(fromClassId: String, toClassId: String, type: MermaidClassDiagramRelation.Type) =
	MermaidClassDiagramRelation(fromClassId, toClassId, type).also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(fromClass: MermaidClassDiagramClass, toClass: MermaidClassDiagramClass, type: MermaidClassDiagramRelation.Type) =
	MermaidClassDiagramRelation(fromClass.id, toClass.id, type).also { relations += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramDslEntry.relation(
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
inline fun MermaidClassDiagramClass.property(name: String) =
	MermaidClassDiagramProperty(name).also { properties += it }

@Deprecated("""Use code-like-style build extensions. e.g: method("getName"()).""", ReplaceWith("method(name(*params))"))
@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.method(name: String, vararg params: String) =
	MermaidClassDiagramMethod(name, params).also { methods += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.method(method: MermaidClassDiagramMethod) = method.also { methods += it }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`public`(member: MermaidClassDiagramMember) =
	member.also { it.visibility = MermaidClassDiagramMember.Visibility.Public }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`private`(member: MermaidClassDiagramMember) =
	member.also { it.visibility = MermaidClassDiagramMember.Visibility.Private }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`protected`(member: MermaidClassDiagramMember) =
	member.also { it.visibility = MermaidClassDiagramMember.Visibility.Protected }

@MermaidClassDiagramDsl
inline fun MermaidClassDiagramClass.`package`(member: MermaidClassDiagramMember) =
	member.also { it.visibility = MermaidClassDiagramMember.Visibility.Protected }

@Deprecated("""Use wrapped-style build extensions. e,g: public(property("name").""", ReplaceWith("public(this)"))
@MermaidClassDiagramDsl
inline infix fun <T : MermaidClassDiagramMember> T.visibility(visibility: MermaidClassDiagramMember.Visibility) =
	this.also { it.visibility = visibility }

@MermaidClassDiagramDsl
inline infix fun <T : MermaidClassDiagramMember> T.type(type: String) =
	this.also { it.type = type }

@MermaidClassDiagramDsl
inline infix fun MermaidClassDiagramRelation.text(text: String) =
	this.also { it.text = text }

@MermaidClassDiagramDsl
inline infix fun MermaidClassDiagramRelation.cardinality(cardinalityPair: Pair<String, String>) =
	this.also { it.fromCardinality = cardinalityPair.first;it.toCardinality = cardinalityPair.second }
//endregion
