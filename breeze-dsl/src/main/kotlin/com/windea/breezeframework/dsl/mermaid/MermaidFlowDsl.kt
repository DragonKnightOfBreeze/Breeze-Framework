@file:Reference("[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)")
@file:Suppress("CanBePrimaryConstructorProperty", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowConfig.indent
import com.windea.breezeframework.dsl.mermaid.MermaidFlowConfig.quote

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
	val links: MutableSet<MermaidFlowLink> = mutableSetOf()
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
	inline fun node(id: String, text: String? = null, shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect) =
		MermaidFlowNode(id, text, shape).also { nodes += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNodeId: String, linkText: String? = null, linkArrow: MermaidFlowLinkArrow = MermaidFlowLinkArrow.Arrow) =
		MermaidFlowLink(sourceNodeId, targetNodeId, linkText, linkArrow).also { links += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNodeId: String, linkText: String? = null, linkArrow: MermaidFlowLinkArrow = MermaidFlowLinkArrow.Arrow) =
		MermaidFlowLink(sourceNode.id, targetNodeId, linkText, linkArrow).also { links += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNode: MermaidFlowNode, linkText: String? = null, linkArrow: MermaidFlowLinkArrow = MermaidFlowLinkArrow.Arrow) =
		MermaidFlowLink(sourceNodeId, targetNode.id, linkText, linkArrow).also { links += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNode: MermaidFlowNode, linkText: String? = null, linkArrow: MermaidFlowLinkArrow = MermaidFlowLinkArrow.Arrow) =
		MermaidFlowLink(sourceNode.id, targetNode.id, linkText, linkArrow).also { links += it }
	
	@MermaidFlowDsl
	inline fun subGraph(name: String, builder: MermaidFlowSubGraph.() -> Unit) =
		MermaidFlowSubGraph(name).also { it.builder() }.also { subGraphs += it }
	
	@MermaidFlowDsl
	inline fun style(nodeId: String, vararg styles: Pair<String, String>) =
		MermaidFlowNodeStyle(nodeId, *styles).also { this@AbstractMermaidFlow.styles += it }
	
	@MermaidFlowDsl
	inline fun linkStyle(linkOrder: Int, vararg styles: Pair<String, String>) =
		MermaidFlowLinkStyle(linkOrder, *styles).also { linkStyles += it }
	
	@MermaidFlowDsl
	inline fun classDef(className: String, vararg styles: Pair<String, String>) =
		MermaidFlowClassDef(className, *styles).also { classDefs += it }
	
	@MermaidFlowDsl
	inline fun classRef(vararg nodeIds: String, className: String) =
		MermaidFlowClassRef(*nodeIds, className = className).also { classRefs += it }
}

/**Mermaid流程图。*/
@MermaidFlowDsl
class MermaidFlow @PublishedApi internal constructor(
	direction: MermaidFlowDirection
) : AbstractMermaidFlow(), Mermaid {
	val direction: MermaidFlowDirection = direction
	
	override fun toString(): String {
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "graph ${direction.text}\n$indentedSnippet"
	}
}

/**Mermaid流程图节点。*/
@MermaidFlowDsl
class MermaidFlowNode @PublishedApi internal constructor(
	id: String,
	text: String? = null,
	shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect
) : MermaidFlowDslElement {
	val id: String = id //NOTE do not ensure argument is valid
	val text: String? = text?.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	val shape: MermaidFlowNodeShape = shape
	
	//TODO omit text, no double quote surround text if not necessary
	override fun toString(): String {
		val textSnippet = (text ?: id).wrapQuote(quote)
		return "$id${shape.prefix}$textSnippet${shape.suffix}"
	}
}

/**Mermaid流程图连接。*/
@MermaidFlowDsl
class MermaidFlowLink @PublishedApi internal constructor(
	sourceNodeId: String,
	targetNodeId: String,
	linkText: String? = null,
	linkArrow: MermaidFlowLinkArrow = MermaidFlowLinkArrow.Arrow
) : MermaidFlowDslElement {
	val sourceNodeId: String = sourceNodeId //NOTE do not ensure argument is valid
	val targetNodeId: String = targetNodeId //NOTE do not ensure argument is valid
	val linkText: String? = linkText?.replaceWithHtmlWrap() //NOTE do not ensure argument is valid
	val linkArrow: MermaidFlowLinkArrow = linkArrow
	
	//TODO escape if necessary
	override fun toString(): String {
		val linkSnippet = linkArrow.text
		val linkTextSnippet = linkText?.let { "|$linkText|" } ?: ""
		return "$sourceNodeId $linkSnippet$linkTextSnippet $targetNodeId"
	}
}

/**Mermaid流程图的子图。*/
@MermaidFlowDsl
class MermaidFlowSubGraph @PublishedApi internal constructor(
	name: String
) : AbstractMermaidFlow() {
	val name: String = name //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val contentSnippet = super.toString()
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "subgraph $name\n$indentedSnippet\nend"
	}
}

/**Mermaid流程图节点风格。*/
@MermaidFlowDsl
class MermaidFlowNodeStyle @PublishedApi internal constructor(
	nodeId: String,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val nodeId: String = nodeId //NOTE do not ensure argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeId $styleMapSnippet"
	}
}

/**Mermaid流程图连接风格。*/
@MermaidFlowDsl
class MermaidFlowLinkStyle @PublishedApi internal constructor(
	linkOrder: Int,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val linkOrder: Int = linkOrder //NOTE do not ensure argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet"
	}
}

/**Mermaid流程图类定义。*/
@MermaidFlowDsl
class MermaidFlowClassDef @PublishedApi internal constructor(
	className: String,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val className: String = className //NOTE do not ensure argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet"
	}
}

/**Mermaid流程图类引用。*/
@MermaidFlowDsl
class MermaidFlowClassRef @PublishedApi internal constructor(
	vararg nodeIds: String,
	className: String
) : MermaidFlowDslElement {
	val nodeIds = nodeIds.toSet() //NOTE do not ensure argument is valid
	val className = className //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val nodeIdSetSnippet = nodeIds.joinToString()
		return "class $nodeIdSetSnippet $className"
	}
}

//REGION Enumerations and constants

/**Mermaid流程图的方向。*/
enum class MermaidFlowDirection(
	val text: String
) {
	TB("TB"), BT("BT"), LR("LR"), RL("RL")
}

/**Mermaid流程图节点的形状。*/
enum class MermaidFlowNodeShape(
	val prefix: String,
	val suffix: String
) {
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

/**Mermaid流程图连接的箭头类型。*/
enum class MermaidFlowLinkArrow(
	val text: String
) {
	Arrow("-->"),
	DottedArrow("-.->"),
	BoldArrow("==>"),
	Line("---"),
	DottedLine("-.-"),
	BoldLine("===")
}

//REGION Config object

/**Mermaid流程图的配置。*/
object MermaidFlowConfig : MermaidConfig()
