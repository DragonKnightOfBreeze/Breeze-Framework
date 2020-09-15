/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.dsl.flow.FlowChartDslDefinitions.*

@FlowChartDslMarker
inline fun flowChartDsl(block: FlowChartDsl.() -> Unit) = FlowChartDsl().apply(block)


@FlowChartDslMarker
fun IDslEntry.start(name: String) = Node(name, NodeType.Start).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.end(name: String) = Node(name, NodeType.End).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.operation(name: String) = Node(name, NodeType.Operation).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.inputOutput(name: String) = Node(name, NodeType.InputOutput).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.subroutine(name: String) = Node(name, NodeType.Subroutine).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.condition(name: String) = Node(name, NodeType.Condition).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.parallel(name: String) = Node(name, NodeType.Parallel).also { nodes += it }

@FlowChartDslMarker
fun IDslEntry.connection(fromNodeId: String, toNodeId: String) = Connection(fromNodeId, toNodeId).also { connections += it }

@FlowChartDslMarker
infix fun Node.text(text: String) = apply { this.text = text }

@FlowChartDslMarker
infix fun Node.flowState(flowState: String) = apply { this.flowState = flowState }

@FlowChartDslMarker
infix fun Node.urlLink(urlLink: String) = apply { this.urlLink = urlLink }

@FlowChartDslMarker
infix fun Node.newUrlLink(urlLink: String) = apply { this.urlLink = urlLink }.apply { openNewTab = true }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(status) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.status(status: ConnectionStatus) = apply { this.status = status }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(path) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.path(path: ConnectionPath) = apply { this.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(direction) fromTo "B".""")
@FlowChartDslMarker
infix fun Connection.direction(direction: ConnectionDirection) = apply { this.direction = direction }
