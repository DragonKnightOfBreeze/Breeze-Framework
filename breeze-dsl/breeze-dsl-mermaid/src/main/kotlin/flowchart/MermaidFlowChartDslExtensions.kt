// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChartDslDefinitions.*

@MermaidFlowChartDslMarker
inline fun mermaidFlowChartDsl(direction: Direction, block: MermaidFlowChartDsl.() -> Unit) = MermaidFlowChartDsl(direction).apply(block)


@MermaidFlowChartDslMarker
fun IDslEntry.node(name: String) = Node(name).also { nodes += it }

@MermaidFlowChartDslMarker
fun IDslEntry.link(fromNodeId: String, toNodeId: String) = Link(fromNodeId, toNodeId).also { links += it }

@MermaidFlowChartDslMarker
fun IDslEntry.link(
	fromNodeId: String,
	arrowShape: ArrowShape,
	toNodeId: String
) = Link(fromNodeId, toNodeId).apply { this.arrowShape = arrowShape }.also { links += it }

@MermaidFlowChartDslMarker
inline fun IDslEntry.subGraph(name: String, block: SubGraph.() -> Unit) = SubGraph(name).apply(block).also { subGraphs += it }

@MermaidFlowChartDslMarker
fun IDslEntry.nodeStyle(nodeName: String, vararg styles: Pair<String, String>) = NodeStyle(nodeName, styles.toMap()).also { nodeStyles += it }

@MermaidFlowChartDslMarker
fun IDslEntry.linkStyle(linkOrder: Int, vararg styles: Pair<String, String>) = LinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }

@MermaidFlowChartDslMarker
fun IDslEntry.classDef(className: String, vararg styles: Pair<String, String>) = ClassDef(className, styles.toMap()).also { classDefs += it }

@MermaidFlowChartDslMarker
fun IDslEntry.classRef(className: String, vararg nodeNames: String) = ClassRef(className, nodeNames.toSet()).also { classRefs += it }

@MermaidFlowChartDslMarker
infix fun Node.text(text: String) = apply { this.text = text }

@MermaidFlowChartDslMarker
infix fun Node.shape(shape: NodeShape) = apply { this.shape = shape }

@MermaidFlowChartDslMarker
infix fun Link.text(text: String) = apply { this.text = text }

@MermaidFlowChartDslMarker
infix fun Link.arrowShape(arrowShape: ArrowShape) = apply { this.arrowShape = arrowShape }
