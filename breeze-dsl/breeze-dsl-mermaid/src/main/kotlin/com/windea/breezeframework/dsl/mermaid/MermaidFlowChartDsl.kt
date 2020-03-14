package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.constants.SystemProperties.lineSeparator
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.config
import com.windea.breezeframework.dsl.mermaid.MermaidFlowChart.*

/**
 * Mermaid流程图的Dsl。
 * 参见：[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)
 */
@DslMarker
@MustBeDocumented
annotation class MermaidFlowChartDsl

/**
 * Mermaid流程图的入口。
 * 参见：[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)
 * @property nodes 节点一览。忽略重复的元素。
 * @property links 连接一览。
 * @property subGraphs 子图一览。
 * @property nodeStyles 节点风格一览。
 * @property linkStyles 连接风格一览。
 * @property classDefs CSS类定义一览。
 * @property classRefs CSS类引用一览。
 */
@MermaidFlowChartDsl
interface MermaidFlowChartEntry : MermaidEntry, CanSplitLine, WithTransition<Node, Link> {
	val nodes:MutableSet<Node>
	val links:MutableList<Link>
	val subGraphs:MutableList<SubGraph>
	val nodeStyles:MutableList<NodeStyle>
	val linkStyles:MutableList<LinkStyle>
	val classDefs:MutableList<ClassDef>
	val classRefs:MutableList<ClassRef>

	override val contentString
		get() = buildString {
			if(nodes.isNotEmpty()) appendJoin(nodes, lineSeparator).append(splitSeparator)
			if(links.isNotEmpty()) appendJoin(links, lineSeparator).append(splitSeparator)
			if(subGraphs.isNotEmpty()) appendJoin(subGraphs, lineSeparator).append(splitSeparator)
			if(nodeStyles.isNotEmpty()) appendJoin(nodeStyles, lineSeparator).append(splitSeparator)
			if(linkStyles.isNotEmpty()) appendJoin(linkStyles, lineSeparator).append(splitSeparator)
			if(classDefs.isNotEmpty()) appendJoin(classDefs, lineSeparator).append(splitSeparator)
			if(classRefs.isNotEmpty()) appendJoin(classRefs, lineSeparator).append(splitSeparator)
		}.trimEnd()

	@DslFunction
	@MermaidFlowChartDsl
	override fun String.links(other:String) = link(this, other)
}

/**
 * Mermaid流程图的元素。
 * 参见：[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)
 */
@MermaidFlowChartDsl
interface MermaidFlowChartElement : MermaidElement

/**
 * Mermaid流程图。
 * 参见：[Mermaid Flow Chart](https://mermaidjs.github.io/#/flowchart)
 */
@MermaidFlowChartDsl
interface MermaidFlowChart {
	/**
	 * Mermaid流程图的文档。
	 * @property direction 图表的方向。
	 */
	class Document @PublishedApi internal constructor(
		val direction:Direction
	) : Mermaid.Document(), MermaidFlowChartEntry, CanIndent {
		override val nodes:MutableSet<Node> = mutableSetOf()
		override val links:MutableList<Link> = mutableListOf()
		override val subGraphs:MutableList<SubGraph> = mutableListOf()
		override val nodeStyles:MutableList<NodeStyle> = mutableListOf()
		override val linkStyles:MutableList<LinkStyle> = mutableListOf()
		override val classDefs:MutableList<ClassDef> = mutableListOf()
		override val classRefs:MutableList<ClassRef> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "graph ${direction.text}$ls${contentString.doIndent(config.indent)}"
	}

	/**
	 * Mermaid流程图的节点。
	 * @property name 节点的名字。
	 * @property text （可选项）节点的文本。可以使用`<br>`换行。不能包含双引号。
	 * @property shape 节点的形状。默认为矩形。
	 */
	@MermaidFlowChartDsl
	class Node @PublishedApi internal constructor(
		val name:String
	) : MermaidFlowChartElement, WithId {
		var text:String? = null
		var shape:NodeShape = NodeShape.Rect
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString() = "$name${shape.prefix}${text?.doWrap()?.quote(config.quote) ?: name}${shape.suffix}"
	}

	/**
	 * Mermaid流程图的连接。
	 * @property fromNodeId 左节点的编号。
	 * @property toNodeId 右节点的编号。
	 * @property text （可选项）连接的文本。可以使用`<br>`换行。不能包含双引号。
	 * @property arrowShape 箭头的形状。默认为单向箭头。
	 */
	@MermaidFlowChartDsl
	class Link @PublishedApi internal constructor(
		val fromNodeId:String, val toNodeId:String
	) : MermaidFlowChartElement, WithNode {
		var text:String? = null
		var arrowShape:ArrowShape = ArrowShape.Arrow
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		override fun toString() = "$fromNodeId ${arrowShape.text}${text?.doWrap()?.quote(config.quote).typing { "|$it|" }} $toNodeId"
	}

	/**
	 * Mermaid流程图的子图。
	 * @property name 子图的名字。
	 */
	@MermaidFlowChartDsl
	class SubGraph @PublishedApi internal constructor(
		val name:String
	) : MermaidElement, MermaidFlowChartEntry, CanIndent {
		override val nodes:MutableSet<Node> = mutableSetOf()
		override val links:MutableList<Link> = mutableListOf()
		override val subGraphs:MutableList<SubGraph> = mutableListOf()
		override val nodeStyles:MutableList<NodeStyle> = mutableListOf()
		override val linkStyles:MutableList<LinkStyle> = mutableListOf()
		override val classDefs:MutableList<ClassDef> = mutableListOf()
		override val classRefs:MutableList<ClassRef> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "subgraph $name$ls${contentString.doIndent(config.indent)}${ls}end}"
	}

	/**
	 * Mermaid流程图的节点风格。
	 * @property nodeId 节点的编号。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDsl
	class NodeStyle @PublishedApi internal constructor(
		val nodeId:String, val styles:Map<String, String>
	) : MermaidFlowChartElement {
		override fun toString() = "style $nodeId ${styles.joinToString { (k, v) -> "$k: $v" }}"
	}

	/**
	 * Mermaid流程图的连接风格。
	 * @property linkOrder 节点的序号。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDsl
	class LinkStyle @PublishedApi internal constructor(
		val linkOrder:Int, val styles:Map<String, String>
	) : MermaidFlowChartElement {
		override fun toString() = "style $linkOrder ${styles.joinToString { (k, v) -> "$k: $v" }}"
	}

	/**
	 * Mermaid流程图的CSS类定义。
	 * @property className CSS类的名字。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDsl
	class ClassDef @PublishedApi internal constructor(
		val className:String, val styles:Map<String, String>
	) : MermaidFlowChartElement {
		override fun toString() = "classDef $className ${styles.joinToString { (k, v) -> "$k: $v" }}"
	}

	/**
	 * Mermaid流程图的CSS类引用。
	 * @property className CSS类的名字。
	 * @property nodeIds 对应的节点编号组。
	 */
	@MermaidFlowChartDsl
	class ClassRef @PublishedApi internal constructor(
		val className:String, val nodeIds:Set<String>
	) : MermaidFlowChartElement {
		override fun toString() = "class ${nodeIds.joinToString()} $className"
	}

	/**Mermaid流程图的方向。*/
	@MermaidFlowChartDsl
	enum class Direction(internal val text:String) {
		TB("TB"), BT("BT"), LR("LR"), RL("RL")
	}

	/**Mermaid流程图节点的形状。*/
	@MermaidFlowChartDsl
	enum class NodeShape(internal val prefix:String, internal val suffix:String) {
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

	/**Mermaid流程图箭头的形状。*/
	@MermaidFlowChartDsl
	enum class ArrowShape(internal val text:String) {
		Arrow("-->"), DottedArrow("-.->"), BoldArrow("==>"), Line("---"), DottedLine("-.-"), BoldLine("===")
	}
}


/**(No document.)*/
@TopDslFunction
@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction:Direction, block:Document.() -> Unit) = Document(direction).apply(block)

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.node(name:String) =
	Node(name).also { nodes += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.link(fromNodeId:String, toNodeId:String) =
	Link(fromNodeId, toNodeId).also { links += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.link(fromNodeId:String, arrowShape:ArrowShape, toNodeId:String) =
	Link(fromNodeId, toNodeId).apply { this.arrowShape = arrowShape }.also { links += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
inline fun MermaidFlowChartEntry.subGraph(name:String, block:SubGraph.() -> Unit) =
	SubGraph(name).apply(block).also { subGraphs += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.nodeStyle(nodeName:String, vararg styles:Pair<String, String>) =
	NodeStyle(nodeName, styles.toMap()).also { nodeStyles += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.linkStyle(linkOrder:Int, vararg styles:Pair<String, String>) =
	LinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.classDef(className:String, vararg styles:Pair<String, String>) =
	ClassDef(className, styles.toMap()).also { classDefs += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartEntry.classRef(className:String, vararg nodeNames:String) =
	ClassRef(className, nodeNames.toSet()).also { classRefs += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
infix fun Node.text(text:String) =
	apply { this.text = text }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
infix fun Node.shape(shape:NodeShape) =
	apply { this.shape = shape }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
infix fun Link.text(text:String) =
	apply { this.text = text }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
infix fun Link.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }
