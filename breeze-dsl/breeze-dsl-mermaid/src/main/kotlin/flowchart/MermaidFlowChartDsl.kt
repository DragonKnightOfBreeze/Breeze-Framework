// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.MermaidDsl.Companion.htmlWrap
import com.windea.breezeframework.dsl.mermaid.MermaidDsl.DslConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidDsl.DslConfig.quote

@MermaidFlowChartDslMarker
interface MermaidFlowChartDsl {
	@MermaidFlowChartDslMarker
	class DslDocument @PublishedApi internal constructor(
		val direction: Direction,
	) : MermaidDsl.DslDocument(), DslEntry, Indentable {
		override val nodes: MutableSet<Node> = mutableSetOf()
		override val links: MutableList<Link> = mutableListOf()
		override val subGraphs: MutableList<SubGraph> = mutableListOf()
		override val nodeStyles: MutableList<NodeStyle> = mutableListOf()
		override val linkStyles: MutableList<LinkStyle> = mutableListOf()
		override val classDefs: MutableList<ClassDef> = mutableListOf()
		override val classRefs: MutableList<ClassRef> = mutableListOf()
		override var indentContent: Boolean = true

		override fun toString(): String {
			val directionSnippet = direction.text
			val contentSnippet = toContentString().let { if(indentContent) it.prependIndent(indent) else it }
			return "graph $directionSnippet\n$contentSnippet"
		}
	}

	/**
	 * Mermaid流程图领域特定语言的入口。
	 * @property nodes 节点一览。忽略重复的元素。
	 * @property links 连接一览。
	 * @property subGraphs 子图一览。
	 * @property nodeStyles 节点风格一览。
	 * @property linkStyles 连接风格一览。
	 * @property classDefs CSS类定义一览。
	 * @property classRefs CSS类引用一览。
	 */
	@MermaidFlowChartDslMarker
	interface DslEntry : MermaidDsl.DslEntry, WithTransition<Node, Link> {
		val nodes: MutableSet<Node>
		val links: MutableList<Link>
		val subGraphs: MutableList<SubGraph>
		val nodeStyles: MutableList<NodeStyle>
		val linkStyles: MutableList<LinkStyle>
		val classDefs: MutableList<ClassDef>
		val classRefs: MutableList<ClassRef>

		override fun toContentString(): String {
			return arrayOf(
				nodes.joinToText("\n"), links.joinToText("\n"), subGraphs.joinToText("\n"), nodeStyles.joinToText("\n"),
				linkStyles.joinToText("\n"), classDefs.joinToText("\n"), classRefs.joinToText("\n")
			).joinToText("\n\n")
		}

		@MermaidFlowChartDslMarker
		override fun String.links(other: String) = link(this, other)
	}

	/**
	 * Mermaid流程图领域特定语言的元素。
	 */
	@MermaidFlowChartDslMarker
	interface DslElement : MermaidDsl.DslElement

	/**
	 * Mermaid流程图的节点。
	 * @property name 节点的名字。
	 * @property text （可选项）节点的文本。可以使用`<br>`换行。不能包含双引号。
	 * @property shape 节点的形状。默认为矩形。
	 */
	@MermaidFlowChartDslMarker
	class Node @PublishedApi internal constructor(
		val name: String,
	) : DslElement, WithId {
		var text: String? = null
		var shape: NodeShape = NodeShape.Rect
		override val id: String get() = name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val shapePrefixSnippet = shape.prefix
			val textSnippet = text?.htmlWrap()?.quote(quote) ?: name
			val shapeSuffixSnippet = shape.suffix
			return "$name$shapePrefixSnippet$textSnippet$shapeSuffixSnippet"
		}
	}

	/**
	 * Mermaid流程图的连接。
	 * @property fromNodeId 左节点的编号。
	 * @property toNodeId 右节点的编号。
	 * @property text （可选项）连接的文本。可以使用`<br>`换行。不能包含双引号。
	 * @property arrowShape 箭头的形状。默认为单向箭头。
	 */
	@MermaidFlowChartDslMarker
	class Link @PublishedApi internal constructor(
		val fromNodeId: String, val toNodeId: String,
	) : DslElement, WithNode {
		var text: String? = null
		var arrowShape: ArrowShape = ArrowShape.Arrow
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		override fun toString(): String {
			val arrowShapeSnippet = arrowShape.text
			val textSnippet = text?.htmlWrap()?.quote(quote).toText { "|$it|" }
			return "$fromNodeId $arrowShapeSnippet$textSnippet $toNodeId"
		}
	}

	/**
	 * Mermaid流程图的子图。
	 * @property name 子图的名字。
	 */
	@MermaidFlowChartDslMarker
	class SubGraph @PublishedApi internal constructor(
		val name: String,
	) : DslElement, DslEntry, Indentable {
		override val nodes: MutableSet<Node> = mutableSetOf()
		override val links: MutableList<Link> = mutableListOf()
		override val subGraphs: MutableList<SubGraph> = mutableListOf()
		override val nodeStyles: MutableList<NodeStyle> = mutableListOf()
		override val linkStyles: MutableList<LinkStyle> = mutableListOf()
		override val classDefs: MutableList<ClassDef> = mutableListOf()
		override val classRefs: MutableList<ClassRef> = mutableListOf()
		override var indentContent: Boolean = true

		override fun toString(): String {
			val contentSnippet = toContentString().let { if(indentContent) it.prependIndent(indent) else it }
			return "subgraph $name\n$contentSnippet\nend"
		}
	}

	/**
	 * Mermaid流程图的节点风格。
	 * @property nodeId 节点的编号。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDslMarker
	class NodeStyle @PublishedApi internal constructor(
		val nodeId: String, val styles: Map<String, String>,
	) : DslElement {
		override fun toString(): String {
			val stylesSnippet = styles.joinToString { (k, v) -> "$k: $v" }
			return "style $nodeId $stylesSnippet"
		}
	}

	/**
	 * Mermaid流程图的连接风格。
	 * @property linkOrder 节点的序号。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDslMarker
	class LinkStyle @PublishedApi internal constructor(
		val linkOrder: Int, val styles: Map<String, String>,
	) : DslElement {
		override fun toString(): String {
			val stylesSnippet = styles.joinToString { (k, v) -> "$k: $v" }
			return "style $linkOrder $stylesSnippet"
		}
	}

	/**
	 * Mermaid流程图的CSS类定义。
	 * @property className CSS类的名字。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDslMarker
	class ClassDef @PublishedApi internal constructor(
		val className: String, val styles: Map<String, String>,
	) : DslElement {
		override fun toString(): String {
			val stylesSnippet = styles.joinToString { (k, v) -> "$k: $v" }
			return "classDef $className $stylesSnippet"
		}
	}

	/**
	 * Mermaid流程图的CSS类引用。
	 * @property className CSS类的名字。
	 * @property nodeIds 对应的节点编号组。
	 */
	@MermaidFlowChartDslMarker
	class ClassRef @PublishedApi internal constructor(
		val className: String, val nodeIds: Set<String>,
	) : DslElement {
		override fun toString(): String {
			val nodeIdsSnippet = nodeIds.joinToString()
			return "class $nodeIdsSnippet $className"
		}
	}

	/**
	 * Mermaid流程图的方向。
	 */
	@MermaidFlowChartDslMarker
	enum class Direction(val text: String) {
		TB("TB"), BT("BT"), LR("LR"), RL("RL")
	}

	/**
	 * Mermaid流程图节点的形状。
	 */
	@MermaidFlowChartDslMarker
	enum class NodeShape(val prefix: String, val suffix: String) {
		/**矩形。*/
		Rect("[", "]"),

		/**圆角矩形。*/
		RoundedRect("(", ")"),

		/**椭圆形。*/
		Ellipse("([", "])"),

		/**圆柱形*/
		Cylindrical("[(", ")]"),

		/**圆形*/
		Circle("((", "))"),

		/**非对称。*/
		Asymetric(">", "]"),

		/**菱形。*/
		Rhombus("{", "}"),

		/**六边形。*/
		Hexagon("{{", "}}"),

		/**平行四边形。*/
		Parallelogram("[/", "/]"),

		/**倒平行四边形。*/
		AltParallelogram("[\\", "\\]"),

		/**梯形。*/
		Trapezoid("[/", "\\]"),

		/**倒梯形。*/
		AltTrapezoid("[\\", "/]")
	}

	/**
	 * Mermaid流程图箭头的形状。
	 */
	@MermaidFlowChartDslMarker
	enum class ArrowShape(val text: String) {
		Arrow("-->"), DottedArrow("-.->"), BoldArrow("==>"), Line("---"), DottedLine("-.-"), BoldLine("===")
	}
}
