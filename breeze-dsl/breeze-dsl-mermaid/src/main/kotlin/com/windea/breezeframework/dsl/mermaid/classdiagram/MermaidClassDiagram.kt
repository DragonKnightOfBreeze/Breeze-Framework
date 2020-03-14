package com.windea.breezeframework.dsl.mermaid.classdiagram

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.htmlWrap

/**
 * Mermaid类图。
 */
@MermaidClassDiagramDsl
interface MermaidClassDiagram {
	/**Mermaid类图的文档。*/
	@MermaidClassDiagramDsl
	class Document @PublishedApi internal constructor() : Mermaid.Document(), MermaidClassDiagramDslEntry, CanIndent {
		override val classes:MutableSet<Class> = mutableSetOf()
		override val relations:MutableList<Relation> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "classDiagram${Mermaid.ls}${contentString().doIndent(Mermaid.config.indent)}"
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
	) : MermaidClassDiagramDslElement, CanIndent, WithId {
		var annotation:Annotation? = null
		val statements:MutableList<Statement> = mutableListOf()
		override var indentContent:Boolean = true
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString() = "class $name" + when {
			annotation == null && statements.isEmpty() -> ""
			else -> "{${Mermaid.ls}${arrayOf(annotation, statements.joinToString(Mermaid.ls)).typingAll(Mermaid.ls).doIndent(Mermaid.config.indent)}${Mermaid.ls}}"
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
	class Annotation @PublishedApi internal constructor(
		val name:String
	) : MermaidClassDiagramDslElement {
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
	) : MermaidClassDiagramDslElement {
		var visibility:Visibility = Visibility.None

		override fun toString() = "${visibility.text}${expression.htmlWrap()}"
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
	) : MermaidClassDiagramDslElement, WithNode {
		var text:String? = null
		var fromCardinality:String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		var toCardinality:String? = null //syntax: 0..1, 1, 0..*, 1..*, ls, 0..ls, 1..ls
		override val sourceNodeId get() = fromClassId
		override val targetNodeId get() = toClassId

		//syntax: $fromClassId $fromCardinality? $relationType $toCardinality? $toClassId: $text?
		override fun toString() = "$fromClassId ${fromCardinality.typing { it.quote('"') }}${type.text}" +
		                          "${toCardinality.typing { it.quote('"') }} $toClassId" +
		                          text?.htmlWrap().typing { ": $it" }
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
