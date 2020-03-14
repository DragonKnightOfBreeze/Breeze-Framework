package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.Mermaid.Companion.htmlWrap

/**
 * Mermaid流程图。
 */
@MermaidFlowChartDsl
interface MermaidFlowChart {
	/**
	 * Mermaid流程图的文档。
	 * @property direction 图表的方向。
	 */
	@MermaidFlowChartDsl
	class Document @PublishedApi internal constructor(
		val direction:Direction
	) : Mermaid.Document(), MermaidFlowChartDslEntry, CanIndent {
		override val nodes:MutableSet<Node> = mutableSetOf()
		override val links:MutableList<Link> = mutableListOf()
		override val subGraphs:MutableList<SubGraph> = mutableListOf()
		override val nodeStyles:MutableList<NodeStyle> = mutableListOf()
		override val linkStyles:MutableList<LinkStyle> = mutableListOf()
		override val classDefs:MutableList<ClassDef> = mutableListOf()
		override val classRefs:MutableList<ClassRef> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "graph ${direction.text}${Mermaid.ls}${contentString().doIndent(Mermaid.config.indent)}"
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
	) : MermaidFlowChartDslElement, WithId {
		var text:String? = null
		var shape:NodeShape = NodeShape.Rect
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }
		override fun toString() = "$name${shape.prefix}${text?.htmlWrap()?.quote(Mermaid.config.quote) ?: name}${shape.suffix}"
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
	) : MermaidFlowChartDslElement, WithNode {
		var text:String? = null
		var arrowShape:ArrowShape = ArrowShape.Arrow
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		override fun toString() = "$fromNodeId ${arrowShape.text}${text?.htmlWrap()?.quote(Mermaid.config.quote).typing { "|$it|" }} $toNodeId"
	}

	/**
	 * Mermaid流程图的子图。
	 * @property name 子图的名字。
	 */
	@MermaidFlowChartDsl
	class SubGraph @PublishedApi internal constructor(
		val name:String
	) : MermaidDslElement, MermaidFlowChartDslEntry, CanIndent {
		override val nodes:MutableSet<Node> = mutableSetOf()
		override val links:MutableList<Link> = mutableListOf()
		override val subGraphs:MutableList<SubGraph> = mutableListOf()
		override val nodeStyles:MutableList<NodeStyle> = mutableListOf()
		override val linkStyles:MutableList<LinkStyle> = mutableListOf()
		override val classDefs:MutableList<ClassDef> = mutableListOf()
		override val classRefs:MutableList<ClassRef> = mutableListOf()
		override var indentContent:Boolean = true
		override var splitContent:Boolean = true

		override fun toString() = "subgraph $name${Mermaid.ls}${contentString().doIndent(Mermaid.config.indent)}${Mermaid.ls}end}"
	}

	/**
	 * Mermaid流程图的节点风格。
	 * @property nodeId 节点的编号。
	 * @property styles 对应的格式映射。
	 */
	@MermaidFlowChartDsl
	class NodeStyle @PublishedApi internal constructor(
		val nodeId:String, val styles:Map<String, String>
	) : MermaidFlowChartDslElement {
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
	) : MermaidFlowChartDslElement {
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
	) : MermaidFlowChartDslElement {
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
	) : MermaidFlowChartDslElement {
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
