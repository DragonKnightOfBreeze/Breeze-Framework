@file:Reference("[Mermaid](https://mermaidjs.github.io)")
@file:NotImplemented

package com.windea.breezeframework.data.dsl.text

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.*
import com.windea.breezeframework.data.dsl.text.MermaidFlowConfig.indent
import com.windea.breezeframework.data.dsl.text.MermaidFlowConfig.quote

//////////Portal function

fun mermaidGraph(direction: MermaidFlowDirection, builder: MermaidFlow.() -> Unit) = MermaidFlow(direction).builder()

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
annotation class MermaidFlowDsl

@MermaidFlowDsl
interface MermaidFlowDslElement

interface MermaidFlowGraph : CanIndentContent {
	val nodes: MutableSet<MermaidFlowNode>
	val links: MutableSet<MermaidFlowLink>
	
	fun node() {
		TODO()
	}
	
	fun link() {
		TODO()
	}
}

////////////Dsl elements & build functions

class MermaidFlow @PublishedApi internal constructor(
	val direction: MermaidFlowDirection,
	override val nodes: MutableSet<MermaidFlowNode> = mutableSetOf(),
	override val links: MutableSet<MermaidFlowLink> = mutableSetOf(),
	val styles: MutableList<MermaidFlowNodeStyle> = mutableListOf(),
	val linkStyles: MutableList<MermaidFlowLinkStyle> = mutableListOf(),
	val classDefs: MutableSet<MermaidFlowClassDef> = mutableSetOf(),
	val classes: MutableSet<MermaidFlowClass> = mutableSetOf(),
	override var indentContent: Boolean = true
) : MermaidFlowGraph, Dsl {
	override fun toString(): String {
		val contentSnippet = buildString {
			append(nodes.joinToString("\n", "\n\n"))
			append(links.joinToString("\n", "\n\n"))
			append(styles.joinToString("\n", "\n\n"))
			append(linkStyles.joinToString("\n", "\n\n"))
			append(classDefs.joinToString("\n", "\n\n"))
			append(classes.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "graph ${direction.text}\n$indentedSnippet"
	}
	
	fun style() {
		TODO()
	}
	
	fun linkStyle() {
		TODO()
	}
	
	fun classDef() {
		TODO()
	}
	
	fun classRef() {
		TODO()
	}
}

class MermaidFlowSubGraph @PublishedApi internal constructor(
	val name: String,
	override val nodes: MutableSet<MermaidFlowNode>,
	override val links: MutableSet<MermaidFlowLink>,
	override var indentContent: Boolean = true
) : MermaidFlowGraph {
	override fun toString(): String {
		val contentSnippet = buildString {
			append(nodes.joinToString("\n", "\n\n"))
			append(links.joinToString("\n"))
		}
		val indentedSnippet = if(indentContent) contentSnippet.prependIndent(indent) else contentSnippet
		return "subgraph $name\n$indentedSnippet\nend"
	}
}

class MermaidFlowNode @PublishedApi internal constructor(
	val id: String,
	val text: String?,
	val shape: MermaidFlowNodeShape = MermaidFlowNodeShape.Rect
) : MermaidFlowDslElement {
	//TODO omit text, no double quote surround text if not necessary
	override fun toString(): String {
		val textSnippet = (text ?: id).let { "$quote$it$quote" }
		return "$id${shape.prefix}$textSnippet${shape.suffix}"
	}
}

class MermaidFlowLink @PublishedApi internal constructor(
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
		return "$sourceSnippet $linkSnippet $targetSnippet"
	}
}

class MermaidFlowNodeStyle @PublishedApi internal constructor(
	val nodeId: String,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "style $nodeId $styleMapSnippet"
	}
}

class MermaidFlowLinkStyle @PublishedApi internal constructor(
	val linkOrder: Int,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "style $linkOrder $styleMapSnippet"
	}
}

class MermaidFlowClassDef @PublishedApi internal constructor(
	val className: String,
	val styleMap: Map<String, String> = mapOf()
) : MermaidFlowDslElement {
	override fun toString(): String {
		val styleMapSnippet = styleMap.joinToString { (k, v) -> "$k: $v" }
		return "classDef $className $styleMapSnippet"
	}
}

class MermaidFlowClass @PublishedApi internal constructor(
	val nodeIds: Set<String>,
	val className: String
) : MermaidFlowDslElement {
	override fun toString(): String {
		val nodeIdsSnippet = nodeIds.joinToString()
		return "class $nodeIdsSnippet $className"
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


//////////Config object

object MermaidFlowConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
	
	inline operator fun invoke(builder: MermaidFlowConfig.() -> Unit) = this.builder()
}
