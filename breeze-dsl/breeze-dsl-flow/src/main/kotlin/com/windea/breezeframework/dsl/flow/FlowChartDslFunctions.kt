package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.flow.FlowChart.*

@TopDslFunction
@FlowChartDsl
inline fun flowChart(block:Document.() -> Unit) = Document().apply(block)


@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.start(name:String) =
	Node(name, NodeType.Start).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.end(name:String) =
	Node(name, NodeType.End).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.operation(name:String) =
	Node(name, NodeType.Operation).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.inputOutput(name:String) =
	Node(name, NodeType.InputOutput).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.subroutine(name:String) =
	Node(name, NodeType.Subroutine).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.condition(name:String) =
	Node(name, NodeType.Condition).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.parallel(name:String) =
	Node(name, NodeType.Parallel).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowDslChartEntry.connection(fromNodeId:String, toNodeId:String) =
	Connection(fromNodeId, toNodeId).also { connections += it }

@DslFunction
@FlowChartDsl
infix fun Node.text(text:String) =
	apply { this.text = text }

@DslFunction
@FlowChartDsl
infix fun Node.flowState(flowState:String) =
	apply { this.flowState = flowState }

@DslFunction
@FlowChartDsl
infix fun Node.urlLink(urlLink:String) =
	apply { this.urlLink = urlLink }

@DslFunction
@FlowChartDsl
infix fun Node.newUrlLink(urlLink:String) =
	apply { this.urlLink = urlLink }.apply { openNewTab = true }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(status) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun Connection.status(status:ConnectionStatus) =
	apply { this.status = status }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(path) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun Connection.path(path:ConnectionPath) =
	apply { this.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(direction) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun Connection.direction(direction:ConnectionDirection) =
	apply { this.direction = direction }
