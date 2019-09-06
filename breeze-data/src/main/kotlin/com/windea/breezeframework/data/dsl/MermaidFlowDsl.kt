@file:NotImplemented

package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.MermaidFlowConfig.indent
import com.windea.breezeframework.data.dsl.MermaidFlowConfig.quote

//////////Portal extensions

fun Dsl.Companion.mermaidGraph(direction: MermaidFlowDirection, builder: MermaidFlow.() -> Unit) = MermaidFlow(direction).builder()

fun DslConfig.Companion.mermaidGraph(builder: MermaidFlowConfig.() -> Unit) = MermaidFlowConfig.builder()

//////////Main class & Config object

class MermaidFlow(
	val direction: MermaidFlowDirection,
	val nodes: MutableSet<MermaidFlowNode> = mutableSetOf(),
	val links: MutableSet<MermaidFlowLink> = mutableSetOf(),
	val styles: MutableList<MermaidFlowNodeStyle> = mutableListOf(),
	val linkStyles: MutableList<MermaidFlowLinkStyle> = mutableListOf(),
	val classDefs: MutableSet<MermaidFlowClassDef> = mutableSetOf(),
	val classes: MutableSet<MermaidFlowClass> = mutableSetOf(),
	override var indentContent: Boolean = true
) : Dsl, MermaidFlowGraph {
	override fun toString(): String {
		val contentSnippet = buildString {
			append(nodes.joinToString("\n", "\n"))
			append(links.joinToString("\n", "\n"))
			append(styles.joinToString("\n", "\n"))
			append(linkStyles.joinToString("\n", "\n"))
			append(classDefs.joinToString("\n", "\n"))
			append(classes.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "graph ${direction.text}\n$indentedSnippet"
	}
}

object MermaidFlowConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
annotation class MermaidFlowDsl

interface MermaidFlowDslElement

interface MermaidFlowGraph : CanIndentContent

////////////Dsl elements & build functions

class MermaidFlowSubGraph(
	val name: String,
	val nodes: MutableSet<MermaidFlowNode> = mutableSetOf(),
	//TODO
	override var indentContent: Boolean = true
) : MermaidFlowGraph {
	override fun toString(): String {
		//TODO
		val contentSnippet = buildString {
			append(nodes.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "subgraph $name\n$indentedSnippet\nend"
	}
}

class MermaidFlowNode(
	val id: String,
	val text: String?,
	val shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect
) : MermaidFlowDslElement {
	//TODO omit text, no double quote surround text if not necessary
	override fun toString(): String {
		val textSnippet = (text ?: id).let { "$quote$it$quote" }
		return "$id${shape.prefix}$textSnippet${shape.suffix};"
	}
}

class MermaidFlowLink(
	val sourceNode: MermaidFlowNode,
	val targetNode: MermaidFlowNode,
	val linkText: String?,
	val linkShape: MermaidFlowLinkShape
) : MermaidFlowDslElement {
	//TODO escape if necessary
	override fun toString(): String {
		val sourceSnippet = sourceNode.text?.let { sourceNode.toString() } ?: sourceNode.id
		val targetSnippet = targetNode.text?.let { targetNode.toString() } ?: targetNode.id
		val linkSnippet = linkText?.let { "${linkShape.prefix} $linkText ${linkShape.suffix}" } ?: linkShape.delimiter
		return "$sourceSnippet $linkSnippet $targetSnippet;"
	}
}

class MermaidFlowNodeStyle(
	val nodeId: String,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeId $styleMapSnippet;"
	}
}

class MermaidFlowLinkStyle(
	val linkOrder: Int,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet;"
	}
}

class MermaidFlowClassDef(
	val className: String,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet;"
	}
}

class MermaidFlowClass(
	val nodeIds: Set<String>,
	val className: String
) : MermaidFlowDslElement {
	override fun toString(): String {
		val nodeIdsSnippet = nodeIds.joinToString()
		return "class $nodeIdsSnippet $className;"
	}
}

////////enumerations and constants

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
	val suffix: String,
	val prefix: String,
	val delimiter: String
) {
	Normal("---", "--", "---"),
	Arrow("-->", "--", "-->"),
	Dotted("-.->", "-.", ".->"),
	Thick("==>", "==", "==>")
}

//////////TODO Param handler extensions

@PublishedApi
internal fun String.fixMermaidFlowNodeId() = this.removeBlank()
