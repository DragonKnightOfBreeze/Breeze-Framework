package com.windea.breezeframework.dsl.mermaid.statediagram

import com.windea.breezeframework.core.constants.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.*

/**
 * Mermaid状态图领域特定语言的入口。
 * @property states 状态一览。忽略重复的元素。
 * @property links 连接一览。忽略重复的元素。
 * @property notes 注释一览。
 */
@MermaidStateDiagramDsl
interface MermaidStateDiagramDslEntry : MermaidDslEntry, CanSplitLine, WithTransition<MermaidStateDiagram.State, MermaidStateDiagram.Transition> {
	val states:MutableSet<MermaidStateDiagram.State>
	val links:MutableList<MermaidStateDiagram.Transition>
	val notes:MutableList<MermaidStateDiagram.Note>

	override fun contentString() = buildString {
		if(states.isNotEmpty()) appendJoin(states, SystemProperties.lineSeparator).append(splitSeparator)
		if(links.isNotEmpty()) appendJoin(links, SystemProperties.lineSeparator).append(splitSeparator)
		if(notes.isNotEmpty()) appendJoin(notes, SystemProperties.lineSeparator).append(splitSeparator)
	}.trimEnd()

	override fun String.links(other:String) = transition(this, other)

}
