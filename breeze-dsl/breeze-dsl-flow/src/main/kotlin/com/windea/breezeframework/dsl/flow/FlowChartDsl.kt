package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.flow.FlowChart.*
import com.windea.breezeframework.dsl.flow.FlowChart.FlowChartConnection.Companion.binderQueue
import java.util.*

/**
 * 流程图的Dsl。
 * 参见：[Github](https://github.com/adrai/flowchart.js)
 * */
@DslMarker
@MustBeDocumented
annotation class FlowChartDsl

/**流程图的入口。*/
@FlowChartDsl
interface FlowChartEntry : DslEntry, CanSplitLine, WithTransition<FlowChartNode, FlowChartConnection> {
	val nodes:MutableSet<FlowChartNode>
	val connections:MutableList<FlowChartConnection>

	override val contentString:String
		get() {
			return listOfNotNull(
				nodes.orNull()?.joinToString("\n"),
				connections.orNull()?.joinToString("\n")
			).joinToString(splitSeparator)
		}

	@DslFunction
	@FlowChartDsl
	override fun String.links(other:String) = connection(this, other)

	@DslFunction
	@FlowChartDsl
	operator fun String.invoke(direction:ConnectionDirection) =
		also { binderQueue.add(FlowChartConnection.Binder(null, null, direction)) }

	@DslFunction
	@FlowChartDsl
	operator fun String.invoke(status:ConnectionStatus, direction:ConnectionDirection? = null) =
		also { binderQueue.add(FlowChartConnection.Binder(status, null, direction)) }

	@DslFunction
	@FlowChartDsl
	operator fun String.invoke(path:ConnectionPath, direction:ConnectionDirection? = null) =
		also { binderQueue.add(FlowChartConnection.Binder(null, path, direction)) }
}

/**流程图的元素。*/
@FlowChartDsl
interface FlowChartElement : DslElement

/**流程图。*/
@FlowChartDsl
interface FlowChart {
	/**流程图的方向。*/
	class Document @PublishedApi internal constructor() : DslDocument, FlowChartEntry {
		override val nodes:MutableSet<FlowChartNode> = mutableSetOf()
		override val connections:MutableList<FlowChartConnection> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString() = contentString
	}

	/**带有流程图方向。*/
	@FlowChartDsl
	interface WithDirection {
		val direction:ConnectionDirection?
	}

	/**流程图的节点。*/
	@FlowChartDsl
	class FlowChartNode @PublishedApi internal constructor(
		val name:String, val type:NodeType
	) : FlowChartElement, WithId {
		var text:String? = null //换行符：\n
		var flowState:String? = null
		var urlLink:String? = null
		var openNewTab:Boolean = false
		override val id:String get() = name

		override fun equals(other:Any?) = equalsByOne(this, other) { id }
		override fun hashCode() = hashCodeByOne(this) { id }

		//syntax: name=>$type: $text|$flowState?:>$urlLink
		override fun toString():String {
			val flowStateSnippet = flowState?.let { "|$it" }.orEmpty()
			val urlLinkSnippet = urlLink?.let { ":>$it" }.orEmpty()
			val blankSnippet = if(openNewTab) "[blank]" else ""
			return "$name=>${type.text}: $text$flowStateSnippet$urlLinkSnippet$blankSnippet"
		}
	}

	/**流程图的连接。*/
	@FlowChartDsl
	class FlowChartConnection @PublishedApi internal constructor(
		val fromNodeId:String, val toNodeId:String
	) : FlowChartElement, WithNode {
		private val builder = binderQueue.poll() ?: Binder()

		var status:ConnectionStatus? = builder.status
		var path:ConnectionPath? = builder.path
		var direction:ConnectionDirection? = builder.direction
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		//syntax: $fromNodeId($specifications)->$toNodeId
		override fun toString():String {
			val specificationsSnippet = listOfNotNull(
				status?.text, path?.text, direction?.text
			).orNull()?.joinToString(", ", "(", ")").orEmpty()
			return "$fromNodeId$specificationsSnippet->$toNodeId"
		}

		internal class Binder(
			var status:ConnectionStatus? = null,
			var path:ConnectionPath? = null,
			var direction:ConnectionDirection? = null
		)

		companion object {
			internal val binderQueue:Queue<Binder> = ArrayDeque(2) //将部分属性存储在外部绑定器队列
		}
	}

	/**流程图节点的类型。*/
	@FlowChartDsl
	enum class NodeType(
		internal val text:String
	) {
		Start("start"), End("end"), Operation("operation"), InputOutput("inputoutput"),
		Subroutine("subroutine"), Condition("condition"), Parallel("parallel")
	}

	/**流程图连接的状态。*/
	@FlowChartDsl
	enum class ConnectionStatus(
		internal val text:String
	) {
		Yes("yes"), No("no")
	}

	/**流程图连接的路径。*/
	@FlowChartDsl
	enum class ConnectionPath(
		internal val text:String
	) {
		Path1("path1"), Path2("path2"), Path3("path3")
	}

	/**流程图连接的方向。*/
	@FlowChartDsl
	enum class ConnectionDirection(
		internal val text:String
	) {
		Left("left"), Right("right"), Top("top"), Bottom("bottom")
	}
}


@TopDslFunction
@FlowChartDsl
inline fun flowChart(block:Document.() -> Unit) = Document().apply(block)

@DslFunction
@FlowChartDsl
fun FlowChartEntry.start(name:String) =
	FlowChartNode(name, NodeType.Start).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.end(name:String) =
	FlowChartNode(name, NodeType.End).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.operation(name:String) =
	FlowChartNode(name, NodeType.Operation).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.inputOutput(name:String) =
	FlowChartNode(name, NodeType.InputOutput).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.subroutine(name:String) =
	FlowChartNode(name, NodeType.Subroutine).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.condition(name:String) =
	FlowChartNode(name, NodeType.Condition).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.parallel(name:String) =
	FlowChartNode(name, NodeType.Parallel).also { nodes += it }

@DslFunction
@FlowChartDsl
fun FlowChartEntry.connection(fromNodeId:String, toNodeId:String) =
	FlowChartConnection(fromNodeId, toNodeId).also { connections += it }

@DslFunction
@FlowChartDsl
infix fun FlowChartNode.text(text:String) =
	apply { this.text = text }

@DslFunction
@FlowChartDsl
infix fun FlowChartNode.flowState(flowState:String) =
	apply { this.flowState = flowState }

@DslFunction
@FlowChartDsl
infix fun FlowChartNode.urlLink(urlLink:String) =
	apply { this.urlLink = urlLink }

@DslFunction
@FlowChartDsl
infix fun FlowChartNode.newUrlLink(urlLink:String) =
	apply { this.urlLink = urlLink }.apply { openNewTab = true }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(status) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun FlowChartConnection.status(status:ConnectionStatus) =
	apply { this.status = status }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(path) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun FlowChartConnection.path(path:ConnectionPath) =
	apply { this.path = path }

@Suppress("DeprecatedCallableAddReplaceWith")
@Deprecated("""Use related binding-to-node build extension. Such as: "A"(direction) fromTo "B".""")
@DslFunction
@FlowChartDsl
infix fun FlowChartConnection.direction(direction:ConnectionDirection) =
	apply { this.direction = direction }
