// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidFlowChartDslExtensions")

package icu.windea.breezeframework.dsl.mermaid.flowchart

import icu.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChartDsl.*

/**
 * 开始构建[MermaidFlowChartDsl]。
 */
@MermaidFlowChartDslMarker
inline fun mermaidFlowChartDsl(direction: Direction, block: DslDocument.() -> Unit): DslDocument {
	return DslDocument(direction).apply(block)
}

@MermaidFlowChartDslMarker
fun DslEntry.node(name: String): Node {
	return Node(name).also { nodes += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.link(fromNodeId: String, toNodeId: String): Link {
	return Link(fromNodeId, toNodeId).also { links += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.link(fromNodeId: String, arrowShape: ArrowShape, toNodeId: String): Link {
	return Link(fromNodeId, toNodeId).apply { this.arrowShape = arrowShape }.also { links += it }
}

@MermaidFlowChartDslMarker
inline fun DslEntry.subGraph(name: String, block: SubGraph.() -> Unit): SubGraph {
	return SubGraph(name).apply(block).also { subGraphs += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.nodeStyle(nodeName: String, vararg styles: Pair<String, String>): NodeStyle {
	return NodeStyle(nodeName, styles.toMap()).also { nodeStyles += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.linkStyle(linkOrder: Int, vararg styles: Pair<String, String>): LinkStyle {
	return LinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.classDef(className: String, vararg styles: Pair<String, String>): ClassDef {
	return ClassDef(className, styles.toMap()).also { classDefs += it }
}

@MermaidFlowChartDslMarker
fun DslEntry.classRef(className: String, vararg nodeNames: String): ClassRef {
	return ClassRef(className, nodeNames.toSet()).also { classRefs += it }
}

@MermaidFlowChartDslMarker
infix fun Node.text(text: String): Node {
	return apply { this.text = text }
}

@MermaidFlowChartDslMarker
infix fun Node.shape(shape: NodeShape): Node {
	return apply { this.shape = shape }
}

@MermaidFlowChartDslMarker
infix fun Link.text(text: String): Link {
	return apply { this.text = text }
}

@MermaidFlowChartDslMarker
infix fun Link.arrowShape(arrowShape: ArrowShape): Link {
	return apply { this.arrowShape = arrowShape }
}
