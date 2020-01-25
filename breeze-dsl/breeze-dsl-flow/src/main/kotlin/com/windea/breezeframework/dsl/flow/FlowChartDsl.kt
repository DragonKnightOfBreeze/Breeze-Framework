@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.flow.FlowChartConnection.Companion.binderQueue
import java.util.*

//region top annotations and interfaces
/**流程图的Dsl。*/
@ReferenceApi("[Github](https://github.com/adrai/flowchart.js)")
@DslMarker
@MustBeDocumented
internal annotation class FlowChartDsl

/**流程图。*/
@FlowChartDsl
class FlowChart @PublishedApi internal constructor() : DslDocument, FlowChartDslEntry {
	override val nodes: MutableSet<FlowChartNode> = mutableSetOf()
	override val connections: MutableList<FlowChartConnection> = mutableListOf()

	override var splitContent: Boolean = true

	override fun toString(): String {
		return toContentString()
	}
}
//endregion

//region dsl interfaces
/**流程图Dsl的入口。*/
@FlowChartDsl
interface FlowChartDslEntry : DslEntry, CanSplit, WithTransition<FlowChartNode, FlowChartConnection> {
	val nodes: MutableSet<FlowChartNode>
	val connections: MutableList<FlowChartConnection>

	fun toContentString(): String {
		return arrayOf(
			nodes.joinToStringOrEmpty("\n"),
			connections.joinToStringOrEmpty("\n")
		).filterNotEmpty().joinToStringOrEmpty(split)
	}

	@FlowChartDsl
	override fun String.fromTo(other: String) = connection(this, other)

	@FlowChartDsl
	operator fun String.invoke(direction: FlowChartConnection.Direction) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, null, direction)) }

	@FlowChartDsl
	operator fun String.invoke(status: FlowChartConnection.Status, direction: FlowChartConnection.Direction? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(status, null, direction)) }

	@FlowChartDsl
	operator fun String.invoke(path: FlowChartConnection.Path, direction: FlowChartConnection.Direction? = null) =
		this.also { binderQueue.push(FlowChartConnection.Binder(null, path, direction)) }
}

/**流程图Dsl的元素。*/
@FlowChartDsl
interface FlowChartDslElement : DslElement

/**带有流程图方向。*/
@FlowChartDsl
interface WithFlowChartDirection {
	val direction: FlowChartConnection.Direction?
}

/**流程图连接的绑定器。*/
@FlowChartDsl
interface FlowChartConnectionBinder {
	var status: FlowChartConnection.Status?
	var path: FlowChartConnection.Path?
	var direction: FlowChartConnection.Direction?
}
//endregion

//region dsl elements
/**流程图节点。*/
@FlowChartDsl
class FlowChartNode @PublishedApi internal constructor(
	val name: String,
	val type: Type
) : FlowChartDslElement, WithUniqueId {
	@MultilineProp("\n")
	var text: String? = null
	var flowState: String? = null
	var urlLink: String? = null
	var openNewTab: Boolean = false

	override val id: String get() = name

	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	//syntax: name=>$type: $text|$flowState?:>$urlLink
	override fun toString(): String {
		val flowStateSnippet = flowState?.let { "|$it" }.orEmpty()
		val urlLinkSnippet = urlLink?.let { ":>$it" }.orEmpty()
		val blankSnippet = if(openNewTab) "[blank]" else ""
		return "$name=>${type.text}: $text$flowStateSnippet$urlLinkSnippet$blankSnippet"
	}

	/**流程图节点的类型。*/
	@FlowChartDsl
	enum class Type(val text: String) {
		Start("start"), End("end"), Operation("operation"), InputOutput("inputoutput"),
		Subroutine("subroutine"), Condition("condition"), Parallel("parallel")
	}
}

/**流程图连接。*/
@FlowChartDsl
class FlowChartConnection @PublishedApi internal constructor(
	val fromNodeId: String,
	val toNodeId: String
) : FlowChartDslElement, WithNode<FlowChartNode>, FlowChartConnectionBinder by binderQueue.pollLast() ?: Binder() {
	override val sourceNodeId get() = fromNodeId
	override val targetNodeId get() = toNodeId

	//syntax: $fromNodeId($specifications)->$toNodeId
	override fun toString(): String {
		val specificationsSnippet = listOfNotNull(status?.text, path?.text, direction?.text).joinToStringOrEmpty(", ", "(", ")")
		return "$fromNodeId$specificationsSnippet->$toNodeId"
	}

	/**流程图连接的状态。*/
	@FlowChartDsl
	enum class Status(val text: String) {
		Yes("yes"), No("no")
	}

	/**流程图连接的路径。*/
	@FlowChartDsl
	enum class Path(val text: String) {
		Path1("path1"), Path2("path2"), Path3("path3")
	}

	/**流程图连接的方向。*/
	@FlowChartDsl
	enum class Direction(val text: String) {
		Left("left"), Right("right"), Top("top"), Bottom("bottom")
	}

	internal class Binder(
		override var status: Status? = null,
		override var path: Path? = null,
		override var direction: Direction? = null
	) : FlowChartConnectionBinder

	//使用委托在外部存储数据，等到必要时传递回来
	companion object {
		internal val binderQueue = LinkedList<Binder>()
	}
}
//endregion

//region build extensions
@FlowChartDsl
inline fun flowChart(block: FlowChart.() -> Unit) =
	FlowChart().also { it.block() }

@FlowChartDsl
inline fun FlowChartDslEntry.start(name: String) =
	FlowChartNode(name, FlowChartNode.Type.Start).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.end(name: String) =
	FlowChartNode(name, FlowChartNode.Type.End).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.operation(name: String) =
	FlowChartNode(name, FlowChartNode.Type.Operation).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.inputOutput(name: String) =
	FlowChartNode(name, FlowChartNode.Type.InputOutput).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.subroutine(name: String) =
	FlowChartNode(name, FlowChartNode.Type.Subroutine).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.condition(name: String) =
	FlowChartNode(name, FlowChartNode.Type.Condition).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.parallel(name: String) =
	FlowChartNode(name, FlowChartNode.Type.Parallel).also { nodes += it }

@FlowChartDsl
inline fun FlowChartDslEntry.connection(fromNodeId: String, toNodeId: String) =
	FlowChartConnection(fromNodeId, toNodeId).also { connections += it }

@FlowChartDsl
inline infix fun FlowChartNode.text(text: String) =
	this.also { it.text = text }

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
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(status) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.status(status: FlowChartConnection.Status) =
	this.also { it.status = status }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(path) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.path(path: FlowChartConnection.Path) =
	this.also { it.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(direction) fromTo "B".""")
@FlowChartDsl
inline infix fun FlowChartConnection.direction(direction: FlowChartConnection.Direction) =
	this.also { it.direction = direction }
