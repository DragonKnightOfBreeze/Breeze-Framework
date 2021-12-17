// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.mermaid.classdiagram

import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.dsl.api.*
import icu.windea.breezeframework.dsl.mermaid.*
import icu.windea.breezeframework.dsl.mermaid.MermaidDsl.Companion.htmlWrap
import icu.windea.breezeframework.dsl.mermaid.MermaidDsl.DslConfig.indent

@MermaidClassDiagramDslMarker
interface MermaidClassDiagramDsl {
	@MermaidClassDiagramDslMarker
	class DslDocument @PublishedApi internal constructor() : MermaidDsl.DslDocument(), DslEntry, Indentable {
		override val classes: MutableSet<Class> = mutableSetOf()
		override val relations: MutableList<Relation> = mutableListOf()
		override var indentContent: Boolean = true

		override fun toString(): String {
			return when {
				indentContent -> "classDiagram\n${toContentString().prependIndent(indent)}"
				else -> "classDiagram\n${toContentString()}"
			}
		}
	}

	/**
	 * Mermaid类图领域特定语言的入口。
	 * @property classes 类一览。忽略重复的元素。
	 * @property relations 关系一览。
	 */
	@MermaidClassDiagramDslMarker
	interface DslEntry : MermaidDsl.DslEntry, WithTransition<Class, Relation> {
		val classes: MutableSet<Class>
		val relations: MutableList<Relation>

		override fun toContentString(): String {
			return arrayOf(classes.joinToText("\n"), relations.joinToText("\n")).joinToText("\n\n")
		}

		@MermaidClassDiagramDslMarker
		override fun String.links(other: String) = relation(this, other, RelationType.Link)

		@MermaidClassDiagramDslMarker
		infix fun String.inheritedBy(other: String) = relation(this, other, RelationType.Inheritance)

		@MermaidClassDiagramDslMarker
		infix fun String.composedBy(other: String) = relation(this, other, RelationType.Composition)

		@MermaidClassDiagramDslMarker
		infix fun String.aggregatedBy(other: String) = relation(this, other, RelationType.Aggregation)

		@MermaidClassDiagramDslMarker
		infix fun String.associatedBy(other: String) = relation(this, other, RelationType.Association)

		@MermaidClassDiagramDslMarker
		infix fun String.inherits(other: String) = relation(this, other, RelationType.ReversedInheritance)

		@MermaidClassDiagramDslMarker
		infix fun String.composes(other: String) = relation(this, other, RelationType.ReversedComposition)

		@MermaidClassDiagramDslMarker
		infix fun String.aggregates(other: String) = relation(this, other, RelationType.ReversedAggregation)

		@MermaidClassDiagramDslMarker
		infix fun String.associates(other: String) = relation(this, other, RelationType.ReversedAssociation)
	}

	/**
	 * Mermaid类图领域特定语言的元素。
	 */
	@MermaidClassDiagramDslMarker
	interface DslElement : MermaidDsl.DslElement

	/**
	 * Mermaid类图类。
	 * @property name 类的名字。
	 * @property annotation （可选项）类的注解。
	 * @property statements 类中的声明一览。
	 */
	@MermaidClassDiagramDslMarker
	class Class @PublishedApi internal constructor(
		val name: String,
	) : DslElement, Indentable, WithId {
		var annotation: Annotation? = null
		val statements: MutableList<Statement> = mutableListOf()
		override var indentContent: Boolean = true
		override val id: String get() = name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val contentSnippet = when {
				annotation == null && statements.isEmpty() -> ""
				else -> {
					val content = arrayOf(annotation, statements.joinToText("\n")).joinToText("\n")
					val indentedContent = if(indentContent) content.prependIndent(indent) else content
					"{\n$indentedContent\n}"
				}
			}
			return "class $name$contentSnippet"
		}

		/**(No document.)*/
		@MermaidClassDiagramDslMarker
		operator fun String.invoke(vararg configParams: String) = "$this(${configParams.joinToString()})"

		/**(No document.)*/
		@MermaidClassDiagramDslMarker
		infix fun String.type(type: String) = "$this: $type"
	}

	/**
	 * Mermaid类图的注解。
	 * @property name 注解的名字。可以自定义。
	 */
	@MermaidClassDiagramDslMarker
	class Annotation @PublishedApi internal constructor(
		val name: String,
	) : DslElement {
		override fun toString(): String {
			return "<<$name>>"
		}
	}

	/**
	 * Mermaid类图的声明。
	 * @property expression 声明的表达式。可以使用`<br>`换行。
	 * @property visibility 表达式的可见性。默认不设置。
	 */
	@MermaidClassDiagramDslMarker
	class Statement @PublishedApi internal constructor(
		val expression: String,
	) : DslElement {
		var visibility: Visibility = Visibility.None

		override fun toString(): String {
			val visibilitySnippet = visibility.text
			val expressionSnippet = expression.htmlWrap()
			return "$visibilitySnippet$expressionSnippet"
		}
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
	@MermaidClassDiagramDslMarker
	class Relation @PublishedApi internal constructor(
		val fromClassId: String, val toClassId: String, val type: RelationType,
	) : DslElement, WithNode {
		var text: String? = null
		var fromCardinality: String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		var toCardinality: String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		override val sourceNodeId get() = fromClassId
		override val targetNodeId get() = toClassId

		override fun toString(): String {
			val fromCardinalitySnippet = fromCardinality.toText { it.quote('"') }
			val typeSnippet = type.text
			val toCardinalitySnippet = toCardinality.toText { it.quote('"') }
			val textSnippet = text?.htmlWrap().toText { ": $it" }
			return "$fromClassId $fromCardinalitySnippet$typeSnippet$toCardinalitySnippet $toClassId$textSnippet"
		}
	}

	/**
	 * Mermaid类图声明的可见性。
	 */
	@MermaidClassDiagramDslMarker
	enum class Visibility(val text: String) {
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

	/**
	 * Mermaid类图关系的类型。
	 */
	@MermaidClassDiagramDslMarker
	enum class RelationType(val text: String) {
		//不允许双向箭头
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

	/**
	 * Mermaid类图注解的类型。
	 */
	@MermaidClassDiagramDslMarker
	enum class AnnotationType(val text: String) {
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
