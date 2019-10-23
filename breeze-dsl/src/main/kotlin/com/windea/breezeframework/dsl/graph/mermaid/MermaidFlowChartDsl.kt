@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.interfaces.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.graph.mermaid.MermaidConfig.quote

//REGION top annotations and interfaces

/**Mermaid流程图的Dsl。*/
@ReferenceApi("[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)")
@DslMarker
private annotation class MermaidFlowChartDsl

/**Mermaid流程图。*/
@MermaidFlowChartDsl
class MermaidFlowChart @PublishedApi internal constructor(
	val direction: MermaidFlowChartDirection
) : Mermaid(), MermaidFlowChartDslEntry, IndentContent {
	override val nodes: MutableSet<MermaidFlowChartNode> = mutableSetOf()
	override val links: MutableList<MermaidFlowChartLink> = mutableListOf()
	override val subGraphs: MutableList<MermaidFlowChartSubGraph> = mutableListOf()
	override val styles: MutableList<MermaidFlowChartNodeStyle> = mutableListOf()
	override val linkStyles: MutableList<MermaidFlowChartLinkStyle> = mutableListOf()
	override val classDefs: MutableSet<MermaidFlowChartClassDef> = mutableSetOf()
	override val classRefs: MutableSet<MermaidFlowChartClassRef> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
		return "graph ${direction.text}\n$contentSnippet"
	}
}

//REGION dsl interfaces

/**Mermaid流程图Dsl的入口。*/
@MermaidFlowChartDsl
interface MermaidFlowChartDslEntry : MermaidDslEntry, WithTransition<MermaidFlowChartNode, MermaidFlowChartLink> {
	val nodes: MutableSet<MermaidFlowChartNode>
	val links: MutableList<MermaidFlowChartLink>
	val subGraphs: MutableList<MermaidFlowChartSubGraph>
	val styles: MutableList<MermaidFlowChartNodeStyle>
	val linkStyles: MutableList<MermaidFlowChartLinkStyle>
	val classDefs: MutableSet<MermaidFlowChartClassDef>
	val classRefs: MutableSet<MermaidFlowChartClassRef>
	
	override val MermaidFlowChartNode._nodeName get() = this.name
	override val MermaidFlowChartLink._toNodeName get() = this.targetNodeName
	
	fun toContentString(): String {
		return arrayOf(
			nodes.joinToStringOrEmpty("\n"),
			links.joinToStringOrEmpty("\n"),
			subGraphs.joinToStringOrEmpty("\n"),
			styles.joinToStringOrEmpty("\n"),
			linkStyles.joinToStringOrEmpty("\n"),
			classDefs.joinToStringOrEmpty("\n"),
			classRefs.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty("\n\n")
	}
	
	@GenericDsl
	override fun String.fromTo(other: String) = link(this, other)
}

/**Mermaid流程图Dsl的元素。*/
@MermaidFlowChartDsl
interface MermaidFlowChartDslElement : MermaidDslElement

//REGION dsl elements

/**Mermaid流程图节点。*/
@MermaidFlowChartDsl
class MermaidFlowChartNode @PublishedApi internal constructor(
	val name: String,
	val text: String? = null //NOTE can wrap by "<br>"
) : MermaidFlowChartDslElement, CanEqual {
	var shape: MermaidFlowChartNodeShape = MermaidFlowChartNodeShape.Rect
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	override fun toString(): String {
		val textSnippet = text?.replaceWithHtmlWrap()?.wrapQuote(quote) ?: name
		return "$name${shape.prefix}$textSnippet${shape.suffix}"
	}
}

/**Mermaid流程图连接。*/
@MermaidFlowChartDsl
class MermaidFlowChartLink @PublishedApi internal constructor(
	val sourceNodeName: String,
	val targetNodeName: String,
	var text: String? = null //NOTE can wrap by "<br>"
) : MermaidFlowChartDslElement {
	var arrowShape: MermaidFlowChartLinkArrowShape = MermaidFlowChartLinkArrowShape.Arrow
	
	override fun toString(): String {
		val arrowSnippet = arrowShape.text
		val textSnippet = text?.let { "|${it.replaceWithHtmlWrap()}|" }.orEmpty()
		return "$sourceNodeName $arrowSnippet$textSnippet $targetNodeName"
	}
}

/**Mermaid流程图的子图。*/
@MermaidFlowChartDsl
class MermaidFlowChartSubGraph @PublishedApi internal constructor(
	val name: String
) : MermaidDslElement, MermaidFlowChartDslEntry, IndentContent {
	override val nodes: MutableSet<MermaidFlowChartNode> = mutableSetOf()
	override val links: MutableList<MermaidFlowChartLink> = mutableListOf()
	override val subGraphs: MutableList<MermaidFlowChartSubGraph> = mutableListOf()
	override val styles: MutableList<MermaidFlowChartNodeStyle> = mutableListOf()
	override val linkStyles: MutableList<MermaidFlowChartLinkStyle> = mutableListOf()
	override val classDefs: MutableSet<MermaidFlowChartClassDef> = mutableSetOf()
	override val classRefs: MutableSet<MermaidFlowChartClassRef> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = toContentString()
			.let { if(indentContent) it.prependIndent(indent) else it }
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

//REGION enumerations and constants

/**Mermaid流程图的方向。*/
@MermaidFlowChartDsl
enum class MermaidFlowChartDirection(val text: String) {
	TB("TB"), BT("BT"), LR("LR"), RL("RL")
}

/**Mermaid流程图节点的形状。*/
@MermaidFlowChartDsl
enum class MermaidFlowChartNodeShape(val prefix: String, val suffix: String) {
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

/**Mermaid流程图连接的箭头形状。*/
@MermaidFlowChartDsl
enum class MermaidFlowChartLinkArrowShape(val text: String) {
	Arrow("-->"), DottedArrow("-.->"), BoldArrow("==>"), Line("---"), DottedLine("-.-"), BoldLine("===")
}

//REGION build extensions

@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction: MermaidFlowChartDirection, builder: MermaidFlowChart.() -> Unit) =
	MermaidFlowChart(direction).also { it.builder() }

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
	arrowShape: MermaidFlowChartLinkArrowShape,
	targetNodeName: String,
	text: String? = null
) = MermaidFlowChartLink(sourceNodeName, targetNodeName, text)
	.also { it.arrowShape = arrowShape }
	.also { links += it }

@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.subGraph(name: String, builder: MermaidFlowChartSubGraph.() -> Unit) =
	MermaidFlowChartSubGraph(name).also { it.builder() }.also { subGraphs += it }

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
inline infix fun MermaidFlowChartNode.shape(shape: MermaidFlowChartNodeShape) =
	this.also { it.shape = shape }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.text(text: String) =
	this.also { it.text = text }

@MermaidFlowChartDsl
inline infix fun MermaidFlowChartLink.arrowShape(arrowShape: MermaidFlowChartLinkArrowShape) =
	this.also { it.arrowShape = arrowShape }
