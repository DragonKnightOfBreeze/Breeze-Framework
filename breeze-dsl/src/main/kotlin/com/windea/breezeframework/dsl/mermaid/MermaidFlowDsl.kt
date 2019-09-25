@file:Reference("[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidConfig.quote

//REGION Dsl annotations

@DslMarker
annotation class MermaidFlowDsl

//REGION Dsl elements & build functions

/**构建Mermaid流程图。*/
@MermaidFlowDsl
fun mermaidFlow(direction: MermaidFlowDirection, builder: MermaidFlow.() -> Unit) =
	MermaidFlow(direction).also { it.builder() }

/**Mermaid流程图Dsl的元素。*/
@MermaidFlowDsl
interface MermaidFlowDslElement : MermaidDslElement

/**抽象的Mermaid流程图。*/
@MermaidFlowDsl
sealed class AbstractMermaidFlow : MermaidFlowDslElement, CanIndentContent {
	val nodes: MutableSet<MermaidFlowNode> = mutableSetOf()
	val links: MutableList<MermaidFlowLink> = mutableListOf()
	val subGraphs: MutableList<MermaidFlowSubGraph> = mutableListOf()
	val styles: MutableList<MermaidFlowNodeStyle> = mutableListOf()
	val linkStyles: MutableList<MermaidFlowLinkStyle> = mutableListOf()
	val classDefs: MutableSet<MermaidFlowClassDef> = mutableSetOf()
	val classRefs: MutableSet<MermaidFlowClassRef> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		return arrayOf(
			nodes.joinToString("\n"),
			links.joinToString("\n"),
			subGraphs.joinToString("\n"),
			styles.joinToString("\n"),
			linkStyles.joinToString("\n"),
			classDefs.joinToString("\n"),
			classRefs.joinToString("\n")
		).filterNotEmpty().joinToString("\n\n")
	}
	
	
	@MermaidFlowDsl
	inline fun node(name: String, text: String? = null) = MermaidFlowNode(name, text).also { nodes += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNodeId: String, text: String? = null) =
		MermaidFlowLink(sourceNodeId, targetNodeId, text).also { links += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNodeId: String, text: String? = null) =
		link(sourceNode.name, targetNodeId, text)
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNode: MermaidFlowNode, text: String? = null) =
		link(sourceNodeId, targetNode.name, text)
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNode: MermaidFlowNode, text: String? = null) =
		link(sourceNode.name, targetNode.name, text)
	
	@MermaidFlowDsl
	inline fun subGraph(name: String, builder: MermaidFlowSubGraph.() -> Unit) =
		MermaidFlowSubGraph(name).also { it.builder() }.also { subGraphs += it }
	
	@MermaidFlowDsl
	inline fun style(nodeId: String, vararg styles: Pair<String, String>) =
		MermaidFlowNodeStyle(nodeId, styles.toMap()).also { this@AbstractMermaidFlow.styles += it }
	
	@MermaidFlowDsl
	inline fun linkStyle(linkOrder: Int, vararg styles: Pair<String, String>) =
		MermaidFlowLinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }
	
	@MermaidFlowDsl
	inline fun classDef(className: String, vararg styles: Pair<String, String>) =
		MermaidFlowClassDef(className, styles.toMap()).also { classDefs += it }
	
	@MermaidFlowDsl
	inline fun classRef(vararg nodeIds: String, className: String) =
		MermaidFlowClassRef(nodeIds.toSet(), className).also { classRefs += it }
}

/**Mermaid流程图。*/
@MermaidFlowDsl
class MermaidFlow @PublishedApi internal constructor(
	val direction: MermaidFlowDirection
) : AbstractMermaidFlow(), Mermaid {
	override fun toString(): String {
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "graph ${direction.text}\n$indentedSnippet"
	}
}

/**Mermaid流程图节点。*/
@MermaidFlowDsl
class MermaidFlowNode @PublishedApi internal constructor(
	val name: String,
	val text: String? = null //NOTE can wrap by "<br>"
) : MermaidFlowDslElement {
	var shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect
	
	override fun equals(other: Any?) = this === other || (other is MermaidFlowNode && other.name == name)
	
	override fun hashCode() = name.hashCode()
	
	//TODO omit text, no double quote surround text if not necessary
	override fun toString(): String {
		val textSnippet = (text?.replaceWithEscapedWrap() ?: name).wrapQuote(quote)
		return "$name${shape.prefix}$textSnippet${shape.suffix}"
	}
	
	
	inline infix fun shape(shape: MermaidFlowNodeShape) = this.also { it.shape = shape }
}

/**Mermaid流程图连接。*/
@MermaidFlowDsl
class MermaidFlowLink @PublishedApi internal constructor(
	val sourceNodeId: String,
	val targetNodeId: String,
	val text: String? = null //NOTE can wrap by "<br>"
) : MermaidFlowDslElement {
	var arrowShape: MermaidFlowLinkArrowShape = MermaidFlowLinkArrowShape.Arrow
	
	//TODO escape if necessary
	override fun toString(): String {
		val arrowSnippet = arrowShape.text
		val textSnippet = text?.let { "|${text.replaceWithHtmlWrap()}|" } ?: ""
		return "$sourceNodeId $arrowSnippet$textSnippet $targetNodeId"
	}
	
	
	@MermaidFlowDsl
	inline infix fun arrowShape(arrowShape: MermaidFlowLinkArrowShape) = this.also { it.arrowShape = arrowShape }
}

/**Mermaid流程图的子图。*/
@MermaidFlowDsl
class MermaidFlowSubGraph @PublishedApi internal constructor(
	val name: String
) : AbstractMermaidFlow() {
	override fun toString(): String {
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "subgraph $name\n$indentedSnippet\nend"
	}
}

/**Mermaid流程图节点风格。*/
@MermaidFlowDsl
class MermaidFlowNodeStyle @PublishedApi internal constructor(
	val nodeId: String,
	val styles: Map<String, String>
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeId $styleMapSnippet"
	}
}

/**Mermaid流程图连接风格。*/
@MermaidFlowDsl
class MermaidFlowLinkStyle @PublishedApi internal constructor(
	val linkOrder: Int,
	val styles: Map<String, String>
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet"
	}
}

/**Mermaid流程图类定义。*/
@MermaidFlowDsl
class MermaidFlowClassDef @PublishedApi internal constructor(
	val className: String,
	val styles: Map<String, String>
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet"
	}
}

/**Mermaid流程图类引用。*/
@MermaidFlowDsl
class MermaidFlowClassRef @PublishedApi internal constructor(
	val nodeIds: Set<String>,
	val className: String
) : MermaidFlowDslElement {
	override fun toString(): String {
		val nodeIdSetSnippet = nodeIds.joinToString()
		return "class $nodeIdSetSnippet $className"
	}
}

//REGION Enumerations and constants

/**Mermaid流程图的方向。*/
enum class MermaidFlowDirection(val text: String) {
	TB("TB"), BT("BT"), LR("LR"), RL("RL")
}

/**Mermaid流程图节点的形状。*/
enum class MermaidFlowNodeShape(val prefix: String, val suffix: String) {
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
enum class MermaidFlowLinkArrowShape(val text: String) {
	Arrow("-->"), DottedArrow("-.->"), BoldArrow("==>"), Line("---"), DottedLine("-.-"), BoldLine("===")
}
