@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote

//region top annotations and interfaces
/**Mermaid流程图的Dsl。*/
@ReferenceApi("[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)")
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
	
	fun toContentString(): String {
		return arrayOf(
			nodes.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n"),
			subGraphs.joinToStringOrEmpty("\n"),
			styles.joinToStringOrEmpty("\n"),
			linkStyles.joinToStringOrEmpty("\n"),
			classDefs.joinToStringOrEmpty("\n"),
			classRefs.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(split)
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = link(this, other)
}

/**Mermaid流程图Dsl的元素。*/
@MermaidFlowChartDsl
interface MermaidFlowChartDslElement : MermaidDslElement
//endregion

//region dsl elements
/**Mermaid流程图节点。*/
@MermaidFlowChartDsl
class MermaidFlowChartNode @PublishedApi internal constructor(
	val name: String,
	@Multiline("<br>")
	val text: String? = null
) : MermaidFlowChartDslElement, WithUniqueId {
	var shape: Shape = Shape.Rect
	
	override val id: String get() = name
	
	override fun equals(other: Any?) = equalsBySelectId(this, other) { id }
	
	override fun hashCode() = hashCodeBySelectId(this) { id }
	
	override fun toString(): String {
		val textSnippet = text?.replaceWithHtmlWrap()?.wrapQuote(quote) ?: name
		return "$name${shape.prefix}$textSnippet${shape.suffix}"
	}
	
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
		Rhombus("{", "}")
	}
}

/**Mermaid流程图连接。*/
@MermaidFlowChartDsl
class MermaidFlowChartLink @PublishedApi internal constructor(
	val sourceNodeName: String,
	val targetNodeName: String,
	@Multiline("<br>")
	var text: String? = null
) : MermaidFlowChartDslElement, WithNode<MermaidFlowChartNode> {
	var arrowShape: ArrowShape = ArrowShape.Arrow
	
	override val fromNodeId get() = sourceNodeName
	override val toNodeId get() = targetNodeName
	
	override fun toString(): String {
		val arrowSnippet = arrowShape.text
		val textSnippet = text?.let { "|${it.replaceWithHtmlWrap()}|" }.orEmpty()
		return "$sourceNodeName $arrowSnippet$textSnippet $targetNodeName"
	}
	
	/**Mermaid流程图连接的箭头形状。*/
	@MermaidFlowChartDsl
	enum class ArrowShape(internal val text: String) {
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
		val styleMapSnippet = styles.joinToStringOrEmpty { (k, v) -> "$k: $v" }
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
		val styleMapSnippet = styles.joinToStringOrEmpty { (k, v) -> "$k: $v" }
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
		val styleMapSnippet = styles.joinToStringOrEmpty { (k, v) -> "$k: $v" }
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
		val nodeNameSetSnippet = nodeNames.joinToStringOrEmpty()
		return "class $nodeNameSetSnippet $className"
	}
}
//endregion

//region build extensions
@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction: MermaidFlowChart.Direction, block: MermaidFlowChart.() -> Unit) =
	MermaidFlowChart(direction).also { it.block() }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.node(name: String, text: String? = null) =
	MermaidFlowChartNode(name, text).also { if(text != null) nodes += it } //NOTE registered only if text is not null

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.link(sourceNodeName: String, targetNodeName: String, text: String? = null) =
	MermaidFlowChartLink(sourceNodeName, targetNodeName, text).also { links += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.link(sourceNode: MermaidFlowChartNode, targetNode: MermaidFlowChartNode,
	text: String? = null) =
	MermaidFlowChartLink(sourceNode.name, targetNode.name, text).also { links += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.link(
	sourceNodeName: String,
	arrowShape: MermaidFlowChartLink.ArrowShape,
	targetNodeName: String,
	text: String? = null
) = MermaidFlowChartLink(sourceNodeName, targetNodeName, text)
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
inline infix fun MermaidFlowChartNode.shape(shape: MermaidFlowChartNode.Shape) =
	this.also { it.shape = shape }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.text(text: String) =
	this.also { it.text = text }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.arrowShape(arrowShape: MermaidFlowChartLink.ArrowShape) =
	this.also { it.arrowShape = arrowShape }
//endregion
