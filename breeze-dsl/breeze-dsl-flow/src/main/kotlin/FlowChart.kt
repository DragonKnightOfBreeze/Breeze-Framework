@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.flow.FlowChart.Connection.*
import java.util.*

/**流程图。*/
@FlowChartDsl
interface FlowChart {
	/**流程图的文档。*/
	@FlowChartDsl
	class Document @PublishedApi internal constructor() : Dsl, IDslEntry {
		override val nodes:MutableSet<Node> = mutableSetOf()
		override val connections:MutableList<Connection> = mutableListOf()
		override var splitContent:Boolean = true

		override fun toString():String {
			return toContentString()
		}
	}


	/**流程图领域特定语言的入口。*/
	@FlowChartDsl
	interface IDslEntry : DslEntry, Splitable, WithTransition<Node, Connection> {
		val nodes:MutableSet<Node>
		val connections:MutableList<Connection>

		override fun toContentString():String {
			return arrayOf(nodes.typingAll(ls), connections.typingAll(ls)).doSplit()
		}

		override fun String.links(other:String) = connection(this, other)

		operator fun String.invoke(direction:ConnectionDirection) =
			apply { Connection.binderQueue.add(Binder(null, null, direction)) }

		operator fun String.invoke(status:ConnectionStatus, direction:ConnectionDirection? = null) =
			apply { Connection.binderQueue.add(Binder(status, null, direction)) }

		operator fun String.invoke(path:ConnectionPath, direction:ConnectionDirection? = null) =
			apply { Connection.binderQueue.add(Binder(null, path, direction)) }
	}

	/**流程图领域特定语言的元素。*/
	@FlowChartDsl
	interface IDslElement : DslElement

	/**带有流程图的方向。*/
	@FlowChartDsl
	interface WithDirection {
		val direction:ConnectionDirection?
	}

	/**流程图的节点。*/
	@FlowChartDsl
	class Node @PublishedApi internal constructor(
		val name:String, val type:NodeType
	) : IDslElement, WithId {
		var text:String? = null
		var flowState:String? = null
		var urlLink:String? = null
		var openNewTab:Boolean = false
		override val id:String get() = name

		override fun equals(other:Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString():String {
			val typeSnippet = type.text
			val flowStateSnippet = flowState.typing { "|$it" }
			val urlLinkSnippet = urlLink.typing { ":>$it" }
			val blankSnippet = if(openNewTab) "[blank]" else ""
			return "$name=>$typeSnippet: $text$flowStateSnippet$urlLinkSnippet$blankSnippet"
		}
	}

	/**流程图的连接。*/
	@FlowChartDsl
	class Connection @PublishedApi internal constructor(
		val fromNodeId:String, val toNodeId:String
	) : IDslElement, WithNode {
		private val builder = binderQueue.poll() ?: Binder()

		var status:ConnectionStatus? = builder.status
		var path:ConnectionPath? = builder.path
		var direction:ConnectionDirection? = builder.direction
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		override fun toString():String {
			val specificationsSnippet = arrayOf(status?.text, path?.text, direction?.text).typingAll(", ", "(", ")")
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
