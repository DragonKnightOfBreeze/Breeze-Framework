// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("FlowChartDslExtensions")

package icu.windea.breezeframework.dsl.flow

import icu.windea.breezeframework.dsl.flow.FlowChartDsl.*

/**
 * 开始构建[FlowChartDsl]。
 */
@FlowChartDslMarker
inline fun flowChartDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.Start]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.start(name: String): Node {
	return Node(name, NodeType.Start).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.End]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.end(name: String): Node {
	return Node(name, NodeType.End).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.Operation]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.operation(name: String): Node {
	return Node(name, NodeType.Operation).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.InputOutput]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.inputOutput(name: String): Node {
	return Node(name, NodeType.InputOutput).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.Subroutine]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.subroutine(name: String): Node {
	return Node(name, NodeType.Subroutine).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.Condition]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.condition(name: String): Node {
	return Node(name, NodeType.Condition).also { nodes += it }
}

/**
 * 创建一个类型为[FlowChartDsl.NodeType.Parallel]的[FlowChartDsl.Node]并注册。
 */
@FlowChartDslMarker
fun DslEntry.parallel(name: String): Node {
	return Node(name, NodeType.Parallel).also { nodes += it }
}

/**
 * 创建一个[FlowChartDsl.Connection]并注册。
 */
@FlowChartDslMarker
fun DslEntry.connection(fromNodeId: String, toNodeId: String): Connection {
	return Connection(fromNodeId, toNodeId).also { connections += it }
}

/**
 * 配置[FlowChartDsl.Node]的文本。
 */
@FlowChartDslMarker
infix fun Node.text(text: String): Node {
	return apply { this.text = text }
}

/**
 * 配置[FlowChartDsl.Node]的流程状态。
 */
@FlowChartDslMarker
infix fun Node.flowState(flowState: String): Node {
	return apply { this.flowState = flowState }
}

/**
 * 配置[FlowChartDsl.Node]的链接。
 */
@FlowChartDslMarker
infix fun Node.urlLink(urlLink: String): Node {
	return apply { this.urlLink = urlLink }
}

/**
 * 配置[FlowChartDsl.Node]的链接，并配置为打开新链接。
 */
@FlowChartDslMarker
infix fun Node.newUrlLink(urlLink: String): Node {
	return apply { this.urlLink = urlLink }.apply { openNewTab = true }
}

/**
 * 配置[FlowChartDsl.Connection]的状态。
 */
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(status) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.status(status: ConnectionStatus): Connection {
	return apply { this.status = status }
}

/**
 * 配置[FlowChartDsl.Connection]的路径。
 */
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(path) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.path(path: ConnectionPath): Connection {
	return apply { this.path = path }
}

/**
 * 配置[FlowChartDsl.Connection]的方向。
 */
@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(direction) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.direction(direction: ConnectionDirection): Connection {
	return apply { this.direction = direction }
}
