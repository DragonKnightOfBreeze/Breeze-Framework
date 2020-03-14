package com.windea.breezeframework.dsl.mermaid.flowchart

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChart.*


/**(No document.)*/
@TopDslFunction
@MermaidFlowChartDsl
inline fun mermaidFlowChart(direction:Direction, block:Document.() -> Unit) = Document(direction).apply(block)


/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.node(name:String) =
	Node(name).also { nodes += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.link(fromNodeId:String, toNodeId:String) =
	Link(fromNodeId, toNodeId).also { links += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.link(fromNodeId:String, arrowShape:ArrowShape, toNodeId:String) =
	Link(fromNodeId, toNodeId).apply { this.arrowShape = arrowShape }.also { links += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
inline fun MermaidFlowChartDslEntry.subGraph(name:String, block:SubGraph.() -> Unit) =
	SubGraph(name).apply(block).also { subGraphs += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.nodeStyle(nodeName:String, vararg styles:Pair<String, String>) =
	NodeStyle(nodeName, styles.toMap()).also { nodeStyles += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.linkStyle(linkOrder:Int, vararg styles:Pair<String, String>) =
	LinkStyle(linkOrder, styles.toMap()).also { linkStyles += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.classDef(className:String, vararg styles:Pair<String, String>) =
	ClassDef(className, styles.toMap()).also { classDefs += it }

/**(No document.)*/
@DslFunction
@MermaidFlowChartDsl
fun MermaidFlowChartDslEntry.classRef(className:String, vararg nodeNames:String) =
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
