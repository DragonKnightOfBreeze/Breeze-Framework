package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.flow.FlowChart.*
import com.windea.breezeframework.dsl.flow.FlowChart.Connection.*

/**流程图领域特定语言的入口。*/
@FlowChartDsl
interface FlowDslChartEntry : DslEntry, CanSplitLine, WithTransition<Node, Connection> {
	val nodes:MutableSet<Node>
	val connections:MutableList<Connection>

	override fun contentString():String {
		return listOfNotNull(
			nodes.orNull()?.joinToString("\n"),
			connections.orNull()?.joinToString("\n")
		).joinToString(splitSeparator)
	}

	override fun String.links(other:String) = connection(this, other)

	operator fun String.invoke(direction:ConnectionDirection) =
		also { Connection.binderQueue.add(Binder(null, null, direction)) }

	operator fun String.invoke(status:ConnectionStatus, direction:ConnectionDirection? = null) =
		also { Connection.binderQueue.add(Binder(status, null, direction)) }

	operator fun String.invoke(path:ConnectionPath, direction:ConnectionDirection? = null) =
		also { Connection.binderQueue.add(Binder(null, path, direction)) }
}
