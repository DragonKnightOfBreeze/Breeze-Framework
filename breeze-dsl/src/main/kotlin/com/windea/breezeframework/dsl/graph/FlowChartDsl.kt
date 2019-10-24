@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
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
	
	@GenericDsl
	override fun String.fromTo(other: String) = connection(this, other)
	
	@GenericDsl
	operator fun String.invoke(direction: FlowChartConnection.Direction) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, null, direction)) }
	
	@GenericDsl
	operator fun String.invoke(status: FlowChartConnection.Status, direction: FlowChartConnection.Direction? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(status, null, direction)) }
	
	@GenericDsl
	operator fun String.invoke(path: FlowChartConnection.Path, direction: FlowChartConnection.Direction? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, path, direction)) }
}

/**流程图Dsl的元素。*/
@FlowChartDsl
interface FlowChartDslElement : DslElement

@FlowChartDsl
interface WithFlowChartDirection {
	val direction: FlowChartConnection.Direction?
}

@FlowChartDsl
interface FlowChartConnectionBinder {
	var status: FlowChartConnection.Status?
	var path: FlowChartConnection.Path?
	var direction: FlowChartConnection.Direction?
}

//REGION dsl elements

/**流程图节点。*/
@FlowChartDsl
class FlowChartNode @PublishedApi internal constructor(
	val name: String,
	val type: Type,
	val text: String? = null //NOTE can wrap by truly "\n"
) : FlowChartDslElement, WithName {
	var flowState: String? = null
	var urlLink: String? = null
	var openNewTab: Boolean = false
	
	override val _name: String get() = name
	
	override fun equals(other: Any?) = equalsBySelect(this, other) { arrayOf(name) }
	
	override fun hashCode() = hashCodeBySelect(this) { arrayOf(name) }
	
	//NOTE syntax: name=>$type: $text|$flowState?:>$urlLink
	override fun toString(): String {
		val flowStateSnippet = flowState?.let { "|$it" }.orEmpty()
		val urlLinkSnippet = urlLink?.let { ":>$it" }.orEmpty()
		val blankSnippet = if(openNewTab) "[blank]" else ""
		return "$name=>${type.text}: $text$flowStateSnippet$urlLinkSnippet$blankSnippet"
	}
	
	enum class Type(val text: String) {
		Start("start"), End("end"), Operation("operation"), InputOutput("inputoutput"),
		Subroutine("subroutine"), Condition("condition"), Parallel("parallel")
	}
}

/**流程图连接。*/
@FlowChartDsl
class FlowChartConnection @PublishedApi internal constructor(
	val fromNodeName: String,
	val toNodeName: String
) : FlowChartDslElement, WithNode<FlowChartNode>, FlowChartConnectionBinder by binderQueue.pollLast() ?: Binder() {
	override val _fromNodeName get() = fromNodeName
	override val _toNodeName get() = toNodeName
	
	//NOTE syntax: $fromNodeName($specifications)->$toNodeName
	override fun toString(): String {
		val specificationsSnippet = listOfNotNull(status?.text, path?.text, direction?.text).joinToStringOrEmpty(", ", "(", ")")
		return "$fromNodeName$specificationsSnippet->$toNodeName"
	}
	
	enum class Status(val text: String) {
		Yes("yes"), No("no")
	}
	
	enum class Path(val text: String) {
		Path1("path1"), Path2("path2"), Path3("path3")
	}
	
	enum class Direction(val text: String) {
		Left("left"), Right("right"), Top("top"), Bottom("bottom")
	}
	
	class Binder(
		override var status: Status? = null,
		override var path: Path? = null,
		override var direction: Direction? = null
	) : FlowChartConnectionBinder
	
	//NOTE 使用委托在外部存储数据，等到必要时传递回来
	companion object {
		@PublishedApi internal val binderQueue = LinkedList<Binder>()
	}
}

//REGION build extensions

@FlowChartDsl
inline fun flowChart(block: FlowChart.() -> Unit) =
	FlowChart().also { it.block() }

@FlowChartDsl
inline fun FlowChartDslEntry.start(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.Start, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.end(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.End, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.operation(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.Operation, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.inputOutput(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.InputOutput, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.subroutine(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.Subroutine, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.condition(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.Condition, text).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.parallel(name: String, text: String? = null) =
	FlowChartNode(name, FlowChartNode.Type.Parallel, text).also { nodes += it }

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
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(status) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.status(status: FlowChartConnection.Status) =
	this.also { it.status = status }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(path) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.path(path: FlowChartConnection.Path) =
	this.also { it.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use binding-to-node build extensions. e.g: "A"(direction) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.direction(direction: FlowChartConnection.Direction) =
	this.also { it.direction = direction }
