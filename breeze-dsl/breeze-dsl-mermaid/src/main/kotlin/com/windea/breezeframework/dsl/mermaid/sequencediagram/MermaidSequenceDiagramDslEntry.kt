package com.windea.breezeframework.dsl.mermaid.sequencediagram

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid序列图领域特定语言的入口。
 * @property participants 参与者一览。忽略重复的元素。
 * @property messages 消息一览。
 * @property notes 注释一览。
 * @property scopes 作用域一览。
 */
@MermaidSequenceDiagramDsl
interface MermaidSequenceDiagramDslEntry : MermaidDslEntry, CanSplitLine, WithTransition<MermaidSequenceDiagram.Participant, MermaidSequenceDiagram.Message> {
	val participants:MutableSet<MermaidSequenceDiagram.Participant>
	val messages:MutableList<MermaidSequenceDiagram.Message>
	val notes:MutableList<MermaidSequenceDiagram.Note>
	val scopes:MutableList<MermaidSequenceDiagram.Scope>

	override fun contentString() = buildString {
		if(participants.isNotEmpty()) appendJoin(participants, SystemProperties.lineSeparator).append(splitSeparator)
		if(messages.isNotEmpty()) appendJoin(messages, SystemProperties.lineSeparator).append(splitSeparator)
		if(notes.isNotEmpty()) appendJoin(notes, SystemProperties.lineSeparator).append(splitSeparator)
		if(scopes.isNotEmpty()) appendJoin(scopes, SystemProperties.lineSeparator).append(splitSeparator)
	}.trimEnd()

	override fun String.links(other:String) = message(this, other)
}
