package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChart.*


/**(No document.)*/
@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction:Direction, block:Document.() -> Unit) = Document(direction).apply(block)


/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.node(name:String) =
	Node(name).also { nodes += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.link(fromNodeId:String, toNodeId:String) =
	Link(fromNodeId, toNodeId).also { links += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.link(fromNodeId:String, arrowShape:ArrowShape, toNodeId:String) =
	Link(fromNodeId, toNodeId).apply { this.arrowShape = arrowShape }.also { links += it }

/**(No document.)*/
@MermaidFlowChartDsl
inline fun IDslEntry.subGraph(name:String, block:SubGraph.() -> Unit) =
	SubGraph(name).apply(block).also { subGraphs += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.nodeStyle(nodeName:String, vararg styles:Pair<String, String>) =
	NodeStyle(nodeName, styles.toMap()).also { nodeStyles += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.linkStyle(linkOrder:Int, vararg styles:Pair<String, String>) =
	LinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.classDef(className:String, vararg styles:Pair<String, String>) =
	ClassDef(className, styles.toMap()).also { classDefs += it }

/**(No document.)*/
@MermaidFlowChartDsl
fun IDslEntry.classRef(className:String, vararg nodeNames:String) =
	ClassRef(className, nodeNames.toSet()).also { classRefs += it }

/**(No document.)*/
@MermaidFlowChartDsl
infix fun Node.text(text:String) =
	apply { this.text = text }

/**(No document.)*/
@MermaidFlowChartDsl
infix fun Node.shape(shape:NodeShape) =
	apply { this.shape = shape }

/**(No document.)*/
@MermaidFlowChartDsl
infix fun Link.text(text:String) =
	apply { this.text = text }

/**(No document.)*/
@MermaidFlowChartDsl
infix fun Link.arrowShape(arrowShape:ArrowShape) =
	apply { this.arrowShape = arrowShape }
