@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.interfaces.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.FlowChartConnection.Companion.binderQueue
import java.util.*

//REGION top annotations and interfaces

/**流程图的Dsl。*/
@ReferenceApi("[Github](//https://github.com/adrai/flowchart.js)")
@DslMarker
private annotation class FlowChartDsl

/**流程图。*/
@FlowChartDsl
class FlowChart @PublishedApi internal constructor() : DslBuilder, FlowChartDslEntry {
	override val nodes: MutableSet<FlowChartNode> = mutableSetOf()
	override val connections: MutableList<FlowChartConnection> = mutableListOf()
	
	override fun toString(): String {
		val nodesSnippet = nodes.joinToString("\n")
		val connectionsSnippet = connections.joinToString("\n")
		return "$nodesSnippet\n\n$connectionsSnippet"
	}
}

//REGION dsl interfaces

/**流程图Dsl的入口。*/
@FlowChartDsl
interface FlowChartDslEntry : DslEntry, WithTransition<FlowChartNode, FlowChartConnection> {
	val nodes: MutableSet<FlowChartNode>
	val connections: MutableList<FlowChartConnection>
	
	override val FlowChartNode._nodeName get() = this.name
	override val FlowChartConnection._toNodeName get() = this.toNodeName
	
	@GenericDsl
	override fun String.fromTo(other: String) = connection(this, other)
	
	@GenericDsl
	operator fun String.invoke(direction: FlowChartConnectionDirection) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, null, direction)) }
	
	@GenericDsl
	operator fun String.invoke(condition: FlowChartConnectionBoolean, direction: FlowChartConnectionDirection? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(condition, null, direction)) }
	
	@GenericDsl
	operator fun String.invoke(path: FlowChartConnectionPath, direction: FlowChartConnectionDirection? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, path, direction)) }
}

/**流程图Dsl的元素。*/
@FlowChartDsl
interface FlowChartDslElement : DslElement

@FlowChartDsl
interface WithFlowChartDirection {
	val direction: FlowChartConnectionDirection?
}

@FlowChartDsl
interface FlowChartConnectionBinder {
	var condition: FlowChartConnectionBoolean?
	var path: FlowChartConnectionPath?
	var direction: FlowChartConnectionDirection?
}

//REGION dsl elements

/**流程图节点。*/
@FlowChartDsl
class FlowChartNode @PublishedApi internal constructor(
	val name: String,
	val type: FlowChartNodeType,
	val text: String? = null //NOTE can wrap by "\n"
) : FlowChartDslElement, CanEqual {
	var flowState: String? = null
	var urlLink: String? = null
	var openNewTab: Boolean = false
	
	@PublishedApi internal var binder: FlowChartConnection.Binder? = null
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	//NOTE syntax: name=>$type: $text|$flowState?:>$urlLink
	override fun toString(): String {
		val textSnippet = text?.replaceWithEscapedWrap()
		val flowStateSnippet = flowState?.let { "|$it" }.orEmpty()
		val urlLinkSnippet = urlLink?.let { ":>$it" }.orEmpty()
		val blankSnippet = if(openNewTab) "[blank]" else ""
		return "$name=>${type.text}: $textSnippet$flowStateSnippet$urlLinkSnippet$blankSnippet"
	}
}

/**流程图连接。*/
@FlowChartDsl
class FlowChartConnection @PublishedApi internal constructor(
	val fromNodeName: String,
	val toNodeName: String
) : FlowChartDslElement, FlowChartConnectionBinder by binderQueue.pollLast() ?: Binder() {
	//NOTE syntax: $fromNodeName($specifications)->$toNodeName
	override fun toString(): String {
		val specificationsSnippet = listOfNotNull(condition?.text, path?.text, direction?.text).joinToStringOrEmpty(", ", "(", ")")
		return "$fromNodeName$specificationsSnippet->$toNodeName"
	}
	
	class Binder(
		override var condition: FlowChartConnectionBoolean? = null,
		override var path: FlowChartConnectionPath? = null,
		override var direction: FlowChartConnectionDirection? = null
	) : FlowChartConnectionBinder
	
	//NOTE 使用委托在外部存储数据，等到必要时传递回来
	companion object {
		@PublishedApi internal val binderQueue = LinkedList<Binder>()
	}
}

//REGION enumerations and constants

/**流程图节点的类型。*/
@FlowChartDsl
enum class FlowChartNodeType(val text: String) {
	Start("start"), End("end"), Operation("operation"), InputOutput("inputoutput"),
	Subroutine("subroutine"), Condition("condition"), Parallel("parallel")
}

/**流程图条件节点的状态。*/
@FlowChartDsl
enum class FlowChartConnectionBoolean(val text: String) {
	Yes("yes"), No("no")
}

/**流程图并发节点的路径。*/
@FlowChartDsl
enum class FlowChartConnectionPath(val text: String) {
	Path1("path1"), Path2("path2"), Path3("path3")
}

/**流程图节点的方向。*/
@FlowChartDsl
enum class FlowChartConnectionDirection(val text: String) {
	Left("left"), Right("right"), Top("top"), Bottom("bottom")
}

//REGION build extensions

@FlowChartDsl
inline fun flowChart(block: FlowChart.() -> Unit) =
	FlowChart().also { it.block() }

@FlowChartDsl
inline fun FlowChartDslEntry.start(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.Start, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.end(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.End, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.operation(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.Operation, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.inputOutput(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.InputOutput, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.subroutine(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.Subroutine, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.condition(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.Condition, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.parallel(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNodeType.Parallel, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.connection(fromNodeName: String, toNodeName: String) =
	FlowChartConnection(fromNodeName, toNodeName).also { connections += it }

@FlowChartDsl
inline fun FlowChartDslEntry.connection(fromNode: FlowChartNode, toNode: FlowChartNode) =
	FlowChartConnection(fromNode.name, toNode.name).also { connections += it }

@FlowChartDsl
inline infix fun FlowChartNode.flowState(flowState: String) =
	this.also { it.flowState = flowState }

@FlowChartDsl
inline infix fun FlowChartNode.urlLink(urlLink: String) =
	this.also { it.urlLink = urlLink }

@FlowChartDsl
inline infix fun FlowChartNode.newUrlLink(urlLink: String) =
	this.also { it.urlLink = urlLink;it.openNewTab = true }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(condition) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.condition(condition: FlowChartConnectionBoolean) =
	this.also { it.condition = condition }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(path) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.path(path: FlowChartConnectionPath) =
	this.also { it.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(direction) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.direction(direction: FlowChartConnectionDirection) =
	this.also { it.direction = direction }
