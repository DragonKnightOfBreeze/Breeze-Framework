package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**序列图领域特定语言的入口。*/
@SequenceDiagramDsl
interface SequenceDiagramDslEntry : DslEntry, CanSplitLine, WithTransition<SequenceDiagram.Participant, SequenceDiagram.Message> {
	val participants:MutableSet<SequenceDiagram.Participant>
	val messages:MutableList<SequenceDiagram.Message>
	val notes:MutableList<SequenceDiagram.Note>

	override fun contentString():String {
		return listOfNotNull(
			participants.orNull()?.joinToString("\n"),
			messages.orNull()?.joinToString("\n"),
			notes.orNull()?.joinToString("\n")
		).joinToString(splitSeparator)
	}

	override fun String.links(other:String) = message(this, other)
}
