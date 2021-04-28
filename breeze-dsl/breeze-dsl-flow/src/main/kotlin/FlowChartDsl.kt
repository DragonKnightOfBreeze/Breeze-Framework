// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.flow

import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.dsl.api.*
import java.util.*
import icu.windea.breezeframework.dsl.DslDocument as IDslDocument
import icu.windea.breezeframework.dsl.DslElement as IDslElement
import icu.windea.breezeframework.dsl.DslEntry as IDslEntry

@FlowChartDslMarker
interface FlowChartDsl {
	@FlowChartDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, DslEntry {
		override val nodes: MutableSet<Node> = mutableSetOf()
		override val connections: MutableList<Connection> = mutableListOf()

		override fun toString(): String {
			return toContentString()
		}
	}

	@FlowChartDslMarker
	interface DslEntry : IDslEntry, WithTransition<Node, Connection> {
		val nodes: MutableSet<Node>
		val connections: MutableList<Connection>

		override fun toContentString(): String {
			return arrayOf(nodes.joinToText("\n"), connections.joinToText("\n")).joinToText("\n\n")
		}

		override fun String.links(other: String) = connection(this, other)

		operator fun String.invoke(direction: ConnectionDirection): String {
			return apply { Connection.binderQueue.add(Connection.Binder(null, null, direction)) }
		}

		operator fun String.invoke(status: ConnectionStatus, direction: ConnectionDirection? = null): String {
			return apply { Connection.binderQueue.add(Connection.Binder(status, null, direction)) }
		}

		operator fun String.invoke(path: ConnectionPath, direction: ConnectionDirection? = null): String {
			return apply { Connection.binderQueue.add(Connection.Binder(null, path, direction)) }
		}
	}

	@FlowChartDslMarker
	interface DslElement : IDslElement

	@FlowChartDslMarker
	interface WithDirection {
		val direction: ConnectionDirection?
	}

	@FlowChartDslMarker
	class Node @PublishedApi internal constructor(
		val name: String, val type: NodeType,
	) : DslElement, WithId {
		var text: String? = null
		var flowState: String? = null
		var urlLink: String? = null
		var openNewTab: Boolean = false
		override val id: String get() = name

		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val typeSnippet = type.text
			val flowStateSnippet = flowState.toText { "|$it" }
			val urlLinkSnippet = urlLink.toText { ":>$it" }
			val blankSnippet = if(openNewTab) "[blank]" else ""
			return "$name=>$typeSnippet: $text$flowStateSnippet$urlLinkSnippet$blankSnippet"
		}
	}

	@FlowChartDslMarker
	class Connection @PublishedApi internal constructor(
		val fromNodeId: String, val toNodeId: String,
	) : DslElement, WithNode {
		private val builder = binderQueue.poll() ?: Binder()

		var status: ConnectionStatus? = builder.status
		var path: ConnectionPath? = builder.path
		var direction: ConnectionDirection? = builder.direction
		override val sourceNodeId get() = fromNodeId
		override val targetNodeId get() = toNodeId

		override fun toString(): String {
			val specificationsSnippet = arrayOf(status?.text, path?.text, direction?.text).joinToText(", ", "(", ")")
			return "$fromNodeId$specificationsSnippet->$toNodeId"
		}

		internal class Binder(
			var status: ConnectionStatus? = null,
			var path: ConnectionPath? = null,
			var direction: ConnectionDirection? = null,
		)

		companion object {
			internal val binderQueue: Queue<Binder> = ArrayDeque(2) //将部分属性存储在外部绑定器队列
		}
	}

	@FlowChartDslMarker
	enum class NodeType(val text: String) {
		Start("start"), End("end"), Operation("operation"), InputOutput("inputoutput"),
		Subroutine("subroutine"), Condition("condition"), Parallel("parallel")
	}

	@FlowChartDslMarker
	enum class ConnectionStatus(val text: String) {
		Yes("yes"), No("no")
	}

	@FlowChartDslMarker
	enum class ConnectionPath(val text: String) {
		Path1("path1"), Path2("path2"), Path3("path3")
	}

	@FlowChartDslMarker
	enum class ConnectionDirection(val text: String) {
		Left("left"), Right("right"), Top("top"), Bottom("bottom")
	}
}
