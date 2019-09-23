@file:Reference("[Mermaid](https://mermaidjs.github.io)")
@file:NotImplemented
@file:Suppress("CanBePrimaryConstructorProperty", "SimpleRedundantLet", "NOTHING_TO_INLINE")

package com.windea.breezeframework.data.dsl.text

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.*
import com.windea.breezeframework.data.dsl.text.MermaidFlowConfig.indent
import com.windea.breezeframework.data.dsl.text.MermaidFlowConfig.quote


//REGION Dsl marker annotations & Dsl element interfaces

@DslMarker
annotation class MermaidFlowDsl

@MermaidFlowDsl
interface MermaidFlowDslElement

@MermaidFlowDsl
abstract class MermaidFlowGraph : CanIndentContent {
	val nodes: MutableSet<MermaidFlowNode> = mutableSetOf()
	val links: MutableSet<MermaidFlowLink> = mutableSetOf()
	
	
	@MermaidFlowDsl
	inline fun node(id: String, text: String? = null, shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect) =
		MermaidFlowNode(id, text, shape).also { nodes += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNodeId: String, linkShape: MermaidFlowLinkShape = MermaidFlowLinkShape.Arrow, linkText: String? = null) =
		MermaidFlowLink(sourceNodeId, targetNodeId, linkShape, linkText).also { links += it }
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNodeId: String, linkShape: MermaidFlowLinkShape = MermaidFlowLinkShape.Arrow, linkText: String? = null) = run {
		if(sourceNode !in nodes) nodes += sourceNode
		MermaidFlowLink(sourceNode.id, targetNodeId, linkShape, linkText).also { links += it }
	}
	
	@MermaidFlowDsl
	inline fun link(sourceNodeId: String, targetNode: MermaidFlowNode, linkShape: MermaidFlowLinkShape = MermaidFlowLinkShape.Arrow, linkText: String? = null) = run {
		if(targetNode !in nodes) nodes += targetNode
		MermaidFlowLink(sourceNodeId, targetNode.id, linkShape, linkText).also { links += it }
	}
	
	@MermaidFlowDsl
	inline fun link(sourceNode: MermaidFlowNode, targetNode: MermaidFlowNode, linkShape: MermaidFlowLinkShape = MermaidFlowLinkShape.Arrow, linkText: String? = null) = run {
		if(sourceNode !in nodes) nodes += sourceNode
		if(targetNode !in nodes) nodes += targetNode
		MermaidFlowLink(sourceNode.id, targetNode.id, linkShape, linkText).also { links += it }
	}
}

//REGION Dsl elements & build functions

@MermaidFlowDsl
fun mermaidGraph(direction: MermaidFlowDirection, builder: MermaidFlow.() -> Unit) =
	MermaidFlow(direction).also { it.builder() }

@MermaidFlowDsl
class MermaidFlow @PublishedApi internal constructor(
	direction: MermaidFlowDirection
) : MermaidFlowGraph(), DslBuilder {
	val direction: MermaidFlowDirection = direction
	val styles: MutableList<MermaidFlowNodeStyle> = mutableListOf()
	val linkStyles: MutableList<MermaidFlowLinkStyle> = mutableListOf()
	val classDefs: MutableSet<MermaidFlowClassDef> = mutableSetOf()
	val classRefs: MutableSet<MermaidFlowClassRef> = mutableSetOf()
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = buildString {
			append(nodes.joinToString("\n", "\n\n"))
			append(links.joinToString("\n", "\n\n"))
			append(styles.joinToString("\n", "\n\n"))
			append(linkStyles.joinToString("\n", "\n\n"))
			append(classDefs.joinToString("\n", "\n\n"))
			append(classRefs.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "graph ${direction.text}\n$indentedSnippet"
	}
	
	
	@MermaidFlowDsl
	inline fun style(nodeId: String, vararg styles: Pair<String, String>) =
		MermaidFlowNodeStyle(nodeId, *styles).also { this@MermaidFlow.styles += it }
	
	@MermaidFlowDsl
	inline fun linkStyle(linkOrder: Int, vararg styles: Pair<String, String>) =
		MermaidFlowLinkStyle(linkOrder, *styles).also { this@MermaidFlow.linkStyles += it }
	
	@MermaidFlowDsl
	inline fun classDef(className: String, vararg styles: Pair<String, String>) =
		MermaidFlowClassDef(className, *styles).also { this@MermaidFlow.classDefs += it }
	
	@MermaidFlowDsl
	inline fun classRef(vararg nodeIds: String, className: String) =
		MermaidFlowClassRef(*nodeIds, className = className).also { this@MermaidFlow.classRefs += it }
}

@MermaidFlowDsl
class MermaidFlowSubGraph @PublishedApi internal constructor(
	name: String
) : MermaidFlowGraph() {
	val name: String = name //NOTE do not ensure that argument is valid
	
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val contentSnippet = buildString {
			append(nodes.joinToString("\n", "\n\n"))
			append(links.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "subgraph $name\n$indentedSnippet\nend"
	}
}

@MermaidFlowDsl
class MermaidFlowNode @PublishedApi internal constructor(
	id: String,
	text: String? = null,
	shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect
) : MermaidFlowDslElement {
	val id: String = id //NOTE do not ensure that argument is valid
	val text: String? = text //NOTE do not ensure that argument is valid
	val shape: MermaidFlowNodeShape = shape
	
	//TODO omit text, no double quote surround text if not necessary
	override fun toString(): String {
		val textSnippet = (text ?: id).wrapQuote(quote)
		return "$id${shape.prefix}$textSnippet${shape.suffix}"
	}
}

@MermaidFlowDsl
class MermaidFlowLink @PublishedApi internal constructor(
	sourceNodeId: String,
	targetNodeId: String,
	linkShape: MermaidFlowLinkShape = MermaidFlowLinkShape.Arrow,
	linkText: String? = null
) : MermaidFlowDslElement {
	val sourceNodeId: String = sourceNodeId //NOTE do not ensure that argument is valid
	val targetNodeId: String = targetNodeId //NOTE do not ensure that argument is valid
	val linkShape: MermaidFlowLinkShape = linkShape
	val linkText: String? = linkText //NOTE do not ensure that argument is valid
	
	//TODO escape if necessary
	override fun toString(): String {
		val linkSnippet = linkShape.delimiter
		val linkTextSnippet = linkText?.let { "|$linkText|" } ?: ""
		return "$sourceNodeId $linkSnippet$linkTextSnippet $targetNodeId"
	}
}

@MermaidFlowDsl
class MermaidFlowNodeStyle @PublishedApi internal constructor(
	nodeId: String,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val nodeId: String = nodeId //NOTE do not ensure that argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure that argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeId $styleMapSnippet"
	}
}

@MermaidFlowDsl
class MermaidFlowLinkStyle @PublishedApi internal constructor(
	linkOrder: Int,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val linkOrder: Int = linkOrder //NOTE do not ensure that argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure that argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet"
	}
}

@MermaidFlowDsl
class MermaidFlowClassDef @PublishedApi internal constructor(
	className: String,
	vararg styles: Pair<String, String>
) : MermaidFlowDslElement {
	val className: String = className //NOTE do not ensure that argument is valid
	val styles: Map<String, String> = styles.toMap() //NOTE do not ensure that argument is valid
	
	override fun toString(): String {
		val styleMapSnippet = styles.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet"
	}
}

@MermaidFlowDsl
class MermaidFlowClassRef @PublishedApi internal constructor(
	vararg nodeIds: String,
	className: String
) : MermaidFlowDslElement {
	val nodeIds = nodeIds.toSet() //NOTE do not ensure that argument is valid
	val className = className //NOTE do not ensure that argument is valid
	
	override fun toString(): String {
		val nodeIdSetSnippet = nodeIds.joinToString()
		return "class $nodeIdSetSnippet $className"
	}
}

//REGION Enumerations and constants

enum class MermaidFlowDirection(
	val text: String
) {
	TB("TB"), BT("BT"), LR("LR"), RL("RL")
}

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

enum class MermaidFlowLinkShape(
	val delimiter: String
) {
	Arrow("-->"),
	Dotted("-.->"),
	Thick("==>"),
	Normal("---")
}

//REGION Config object

object MermaidFlowConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}
