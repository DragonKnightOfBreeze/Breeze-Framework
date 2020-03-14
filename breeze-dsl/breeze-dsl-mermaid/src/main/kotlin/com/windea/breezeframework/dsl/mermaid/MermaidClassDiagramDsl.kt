@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.SystemProperties.lineSeparator
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidClassDiagram.*
import com.windea.breezeframework.dsl.mermaid.MermaidClassDiagram.Annotation

/**
 * Mermaid类图的Dsl。
 * 参见：[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidClassDiagramDsl

/**
 * Mermaid类图的入口。
 * 参见：[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)
 * @property classes 类一览。忽略重复的元素。
 * @property relations 关系一览。
 */
@MermaidClassDiagramDsl
interface MermaidClassDiagramEntry : MermaidEntry, CanSplitLine, WithTransition<Class, Relation> {
	val classes:MutableSet<Class>
	val relations:MutableList<Relation>

	override val contentString
		get() = buildString {
			if(classes.isNotEmpty()) appendJoin(classes, lineSeparator).append(splitSeparator)
			if(relations.isNotEmpty()) appendJoin(classes, lineSeparator).append(splitSeparator)
		}.trimEnd()

	@DslFunction
	@MermaidClassDiagramDsl
	override fun String.links(other:String) = relation(this, other, RelationType.Link)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.inheritedBy(other:String) = relation(this, other, RelationType.Inheritance)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.composedBy(other:String) = relation(this, other, RelationType.Composition)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.aggregatedBy(other:String) = relation(this, other, RelationType.Aggregation)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.associatedBy(other:String) = relation(this, other, RelationType.Association)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.inherits(other:String) = relation(this, other, RelationType.ReversedInheritance)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.composes(other:String) = relation(this, other, RelationType.ReversedComposition)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.aggregates(other:String) = relation(this, other, RelationType.ReversedAggregation)

	/**(No document.)*/
	@DslFunction
	@MermaidClassDiagramDsl
	infix fun String.associates(other:String) = relation(this, other, RelationType.ReversedAssociation)
}

/**
 * Mermaid类图Dsl元素。
 * 参见：[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)
 */
@MermaidClassDiagramDsl
interface MermaidClassDiagramElement : MermaidElement

/**
 * Mermaid类图。
 * 参见：[Mermaid Class Diagram](https://mermaidjs.github.io/#/classDiagram)
 */
@MermaidClassDiagramDsl
interface MermaidClassDiagram {
	/**Mermaid类图的文档。*/
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidClassDiagramEntry, CanIndent {
		override val classes:MutableSet<Class> = mutableSetOf()
		override val relations:MutableList<Relation> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "classDiagram$ls${contentString.doIndent(config.indent)}"
	}

	/**
	 * Mermaid类图类。
	 * @property name 类的名字。
	 * @property annotation （可选项）类的注解。
	 * @property statements 类中的声明一览。
	 */
	@MermaidClassDiagramDsl
	class Class @PublishedApi internal constructor(
		val name:String
	) : MermaidClassDiagramElement, CanIndent, WithId {
		var annotation:Annotation? = null
		val statements:MutableList<Statement> = mutableListOf()
		override var indentContent:Boolean = true
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString() = "class $name" + when {
			annotation == null && statements.isEmpty() -> ""
			else -> "{$ls${arrayOf(annotation, statements.joinToString(ls)).typingAll(ls).doIndent(config.indent)}$ls}"
		}

		/**(No document.)*/
		@InlineDslFunction
		@MermaidClassDiagramDsl
		operator fun String.invoke(vararg params:String) = "$this(${params.joinToString()})"

		/**(No document.)*/
		@InlineDslFunction
		@MermaidClassDiagramDsl
		infix fun String.type(type:String) = "$this: $type"
	}

	/**
	 * Mermaid类图的注解。
	 * @property name 注解的名字。可以自定义。
	 */
	@MermaidClassDiagramDsl
	inline class Annotation @PublishedApi internal constructor(
		val name:String
	) : MermaidClassDiagramElement {
		override fun toString() = "<<$name>>"
	}

	/**
	 * Mermaid类图的声明。
	 * @property expression 声明的表达式。可以使用`<br>`换行。
	 * @property visibility 表达式的可见性。默认不设置。
	 */
	@MermaidClassDiagramDsl
	class Statement @PublishedApi internal constructor(
		val expression:String
	) : MermaidClassDiagramElement {
		var visibility:Visibility = Visibility.None

		override fun toString() = "${visibility.text}${expression.doWrap()}"
	}

	/**
	 * Mermaid类图的关系。
	 * @property fromClassId 左边类的编号。
	 * @property toClassId 右边类的编号。
	 * @property type 关系类型。
	 * @property text （可选的）关系的文本。可以使用`<br>`换行。
	 * @property fromCardinality （可选的）左边的基数。只能使用双引号包围，不能包含双引号，也不能进行转义。
	 * @property toCardinality （可选的）右边的基数。只能使用双引号包围，不能包含双引号，也不能进行转义。
	 */
	@MermaidClassDiagramDsl
	class Relation @PublishedApi internal constructor(
		val fromClassId:String, val toClassId:String, val type:RelationType
	) : MermaidClassDiagramElement, WithNode {
		var text:String? = null
		var fromCardinality:String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		var toCardinality:String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		override val sourceNodeId get() = fromClassId
		override val targetNodeId get() = toClassId

		//syntax: $fromClassId $fromCardinality? $relationType $toCardinality? $toClassId: $text?
		override fun toString() = "$fromClassId ${fromCardinality.typing { it.quote('"') }}${type.text}" +
		                          "${toCardinality.typing { it.quote('"') }} $toClassId" +
		                          text?.doWrap().typing { ": $it" }
	}

	/**Mermaid类图声明的可见性。*/
	@MermaidClassDiagramDsl
	enum class Visibility(internal val text:String) {
		/**无。*/
		None(""),

		/**公共的。*/
		Public("+"),

		/**私有的。*/
		Private("-"),

		/**受保护的。*/
		Protected("#"),

		/**包级的可见性。*/
		Package("~")
	}

	/**Mermaid类图关系的类型。*/
	@MermaidClassDiagramDsl
	enum class RelationType(internal val text:String) {
		//do not allow bidirectional arrows
		/**连接。*/
		Link("--"),

		/**继承。*/
		Inheritance("<|--"),

		/**组合。*/
		Composition("*--"),

		/**聚合。*/
		Aggregation("o--"),

		/**关联。*/
		Association("<--"),

		/**反向继承。*/
		ReversedInheritance("--|>"),

		/**反向组合。*/
		ReversedComposition("--*"),

		/**反向居合。*/
		ReversedAggregation("--o"),

		/**反向关联。*/
		ReversedAssociation("-->")
	}

	/**Mermaid类图注解的类型。*/
	@MermaidClassDiagramDsl
	enum class AnnotationType(@PublishedApi internal val text:String) {
		/**接口。*/
		Interface("interface"),

		/**抽象。*/
		Abstract("abstract"),

		/**服务。*/
		Service("service"),

		/**枚举。*/
		Enumeration("enumeration")
	}
}


/**
 * (No document.)
 */
@TopDslFunction
@MermaidClassDiagramDsl
inline fun mermaidClassDiagram(block:Document.() -> Unit) = Document().apply(block)

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
inline fun MermaidClassDiagramEntry.`class`(name:String, block:Class.() -> Unit = {}) =
	Class(name).apply(block).also { classes += it }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun MermaidClassDiagramEntry.relation(fromClassId:String, toClassId:String, type:RelationType) =
	Relation(fromClassId, toClassId, type).also { relations += it }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun MermaidClassDiagramEntry.relation(
	fromClassId:String,
	fromCardinality:String?,
	type:RelationType,
	toCardinality:String?,
	toClassId:String
) = Relation(fromClassId, toClassId, type)
	.apply { this.fromCardinality = fromCardinality }
	.apply { this.toCardinality = toCardinality }
	.also { relations += it }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.annotation(name:String) =
	Annotation(name).also { annotation = it }

/**
 *
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.annotation(type:AnnotationType) =
	Annotation(type.text).also { annotation = it }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.statement(expression:String) =
	Statement(expression).also { statements += it }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.`public`(statement:Statement) =
	statement.apply { visibility = Visibility.Public }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.`private`(statement:Statement) =
	statement.apply { visibility = Visibility.Private }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.`protected`(statement:Statement) =
	statement.apply { visibility = Visibility.Protected }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
fun Class.`package`(statement:Statement) =
	statement.apply { visibility = Visibility.Package }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
infix fun Relation.text(text:String) = apply { this.text = text }

/**
 * (No document.)
 */
@DslFunction
@MermaidClassDiagramDsl
infix fun Relation.cardinality(cardinalityPair:Pair<String, String>) =
	apply { fromCardinality = cardinalityPair.first }.apply { toCardinality = cardinalityPair.second }

