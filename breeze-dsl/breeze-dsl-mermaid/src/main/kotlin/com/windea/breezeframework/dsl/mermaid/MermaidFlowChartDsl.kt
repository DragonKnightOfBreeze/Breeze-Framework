@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.quote

//region dsl top declarations
/**Mermaid流程图的Dsl。*/
@Reference("[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)")
@DslMarker
@MustBeDocumented
internal annotation class MermaidFlowChartDsl

/**Mermaid流程图。*/
@MermaidFlowChartDsl
class MermaidFlowChart @PublishedApi internal constructor(
	val direction: Direction
) : Mermaid(), MermaidFlowChartDslEntry, CanIndent {
	override val nodes: MutableSet<MermaidFlowChartNode> = mutableSetOf()
	override val links: MutableList<MermaidFlowChartLink> = mutableListOf()
	override val subGraphs: MutableList<MermaidFlowChartSubGraph> = mutableListOf()
	override val styles: MutableList<MermaidFlowChartNodeStyle> = mutableListOf()
	override val linkStyles: MutableList<MermaidFlowChartLinkStyle> = mutableListOf()
	override val classDefs: MutableSet<MermaidFlowChartClassDef> = mutableSetOf()
	override val classRefs: MutableSet<MermaidFlowChartClassRef> = mutableSetOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val directionText = direction.text
		val contentSnippet = toContentString().applyIndent(indent)
		return "graph $directionText\n$contentSnippet"
	}

	/**Mermaid流程图的方向。*/
	@MermaidFlowChartDsl
	enum class Direction(val text: String) {
		TB("TB"), BT("BT"), LR("LR"), RL("RL")
	}
}
//endregion

//region dsl interfaces
/**Mermaid流程图Dsl的入口。*/
@MermaidFlowChartDsl
interface MermaidFlowChartDslEntry : MermaidDslEntry, CanSplit, WithTransition<MermaidFlowChartNode, MermaidFlowChartLink> {
	val nodes: MutableSet<MermaidFlowChartNode>
	val links: MutableList<MermaidFlowChartLink>
	val subGraphs: MutableList<MermaidFlowChartSubGraph>
	val styles: MutableList<MermaidFlowChartNodeStyle>
	val linkStyles: MutableList<MermaidFlowChartLinkStyle>
	val classDefs: MutableSet<MermaidFlowChartClassDef>
	val classRefs: MutableSet<MermaidFlowChartClassRef>

	override fun toContentString(): String {
		return listOfNotNull(
			nodes.orNull()?.joinToString("\n"),
			links.orNull()?.joinToString("\n"),
			subGraphs.orNull()?.joinToString("\n"),
			styles.orNull()?.joinToString("\n"),
			linkStyles.orNull()?.joinToString("\n"),
			classDefs.orNull()?.joinToString("\n"),
			classRefs.orNull()?.joinToString("\n")
		).joinToString(split)
	}

	@MermaidFlowChartDsl
	override fun String.links(other: String) = link(this, other)
}

/**Mermaid流程图Dsl的元素。*/
@MermaidFlowChartDsl
interface MermaidFlowChartDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid流程图节点。*/
@MermaidFlowChartDsl
class MermaidFlowChartNode @PublishedApi internal constructor(
	val name: String
) : MermaidFlowChartDslElement, WithUniqueId {
	@MultilineProp("<br>")
	var text: String? = null
	var shape: Shape = Shape.Rect

	override val id: String get() = name

	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString(): String {
		val textSnippet = text?.replaceWithHtmlWrap()?.quote(quote) ?: name
		return "$name${shape.prefix}$textSnippet${shape.suffix}"
	}

	/**Mermaid流程图节点的形状。*/
	@MermaidFlowChartDsl
	enum class Shape(val prefix: String, val suffix: String) {
		/**矩形。*/
		Rect("[", "]"),
		/**圆角矩形。*/
		RoundedRect("(", ")"),
		/**圆角矩形。*/
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
}

/**Mermaid流程图连接。*/
@MermaidFlowChartDsl
class MermaidFlowChartLink @PublishedApi internal constructor(
	val fromNodeId: String,
	val toNodeId: String
) : MermaidFlowChartDslElement, WithNode<MermaidFlowChartNode> {
	@MultilineProp("<br>")
	var text: String? = null
	var arrowShape: ArrowShape = ArrowShape.Arrow

	override val sourceNodeId get() = fromNodeId
	override val targetNodeId get() = toNodeId

	override fun toString(): String {
		val arrowSnippet = arrowShape.text
		val textSnippet = text?.let { "|${it.replaceWithHtmlWrap()}|" }.orEmpty()
		return "$fromNodeId $arrowSnippet$textSnippet $toNodeId"
	}

	/**Mermaid流程图连接的箭头形状。*/
	@MermaidFlowChartDsl
	enum class ArrowShape(val text: String) {
		Arrow("-->"), DottedArrow("-.->"), BoldArrow("==>"), Line("---"), DottedLine("-.-"), BoldLine("===")
	}
}

/**Mermaid流程图的子图。*/
@MermaidFlowChartDsl
class MermaidFlowChartSubGraph @PublishedApi internal constructor(
	val name: String
) : MermaidDslElement, MermaidFlowChartDslEntry, CanIndent {
	override val nodes: MutableSet<MermaidFlowChartNode> = mutableSetOf()
	override val links: MutableList<MermaidFlowChartLink> = mutableListOf()
	override val subGraphs: MutableList<MermaidFlowChartSubGraph> = mutableListOf()
	override val styles: MutableList<MermaidFlowChartNodeStyle> = mutableListOf()
	override val linkStyles: MutableList<MermaidFlowChartLinkStyle> = mutableListOf()
	override val classDefs: MutableSet<MermaidFlowChartClassDef> = mutableSetOf()
	override val classRefs: MutableSet<MermaidFlowChartClassRef> = mutableSetOf()

	override var indentContent: Boolean = true
	override var splitContent: Boolean = true

	override fun toString(): String {
		val contentSnippet = toContentString().applyIndent(indent)
		return "subgraph $name\n$contentSnippet\nend"
	}
}

/**Mermaid流程图节点风格。*/
@MermaidFlowChartDsl
class MermaidFlowChartNodeStyle @PublishedApi internal constructor(
	val nodeName: String,
	val styles: Map<String, String>
) : MermaidFlowChartDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeName $styleMapSnippet"
	}
}

/**Mermaid流程图连接风格。*/
@MermaidFlowChartDsl
class MermaidFlowChartLinkStyle @PublishedApi internal constructor(
	val linkOrder: Int,
	val styles: Map<String, String>
) : MermaidFlowChartDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet"
	}
}

/**Mermaid流程图类定义。*/
@MermaidFlowChartDsl
class MermaidFlowChartClassDef @PublishedApi internal constructor(
	val className: String,
	val styles: Map<String, String>
) : MermaidFlowChartDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet"
	}
}

/**Mermaid流程图类引用。*/
@MermaidFlowChartDsl
class MermaidFlowChartClassRef @PublishedApi internal constructor(
	val nodeNames: Set<String>,
	val className: String
) : MermaidFlowChartDslElement {
	override fun toString(): String {
		val nodeNameSetSnippet = nodeNames.joinToString()
		return "class $nodeNameSetSnippet $className"
	}
}
//endregion

//region dsl build extensions
@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction: MermaidFlowChart.Direction, block: MermaidFlowChart.() -> Unit) =
	MermaidFlowChart(direction).also { it.block() }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.node(name: String) =
	MermaidFlowChartNode(name).also { nodes += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.link(fromNodeId: String, toNodeId: String) =
	MermaidFlowChartLink(fromNodeId, toNodeId).also { links += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.link(
	fromNodeId: String,
	arrowShape: MermaidFlowChartLink.ArrowShape,
	toNodeId: String
) = MermaidFlowChartLink(fromNodeId, toNodeId)
	.also { it.arrowShape = arrowShape }
	.also { links += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.subGraph(name: String, block: MermaidFlowChartSubGraph.() -> Unit) =
	MermaidFlowChartSubGraph(name).also { it.block() }.also { subGraphs += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.style(nodeName: String, vararg styles: Pair<String, String>) =
	MermaidFlowChartNodeStyle(nodeName, styles.toMap()).also { this.styles += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.linkStyle(linkOrder: Int, vararg styles: Pair<String, String>) =
	MermaidFlowChartLinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.classDef(className: String, vararg styles: Pair<String, String>) =
	MermaidFlowChartClassDef(className, styles.toMap()).also { classDefs += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.classRef(vararg nodeNames: String, className: String) =
	MermaidFlowChartClassRef(nodeNames.toSet(), className).also { classRefs += it }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartNode.text(text: String) =
	this.also { it.text = text }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartNode.shape(shape: MermaidFlowChartNode.Shape) =
	this.also { it.shape = shape }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.text(text: String) =
	this.also { it.text = text }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.arrowShape(arrowShape: MermaidFlowChartLink.ArrowShape) =
	this.also { it.arrowShape = arrowShape }
//endregion
