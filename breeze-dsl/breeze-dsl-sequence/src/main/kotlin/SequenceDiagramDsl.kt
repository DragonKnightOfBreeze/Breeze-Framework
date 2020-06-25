package com.windea.breezeframework.dsl.sequence

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.sequence.SequenceDiagramDslDefinitions.*

/**
 * [Sequence Diagram](https://bramp.github.io/js-sequence-diagrams/) dsl.
 */
@SequenceDiagramDslMarker
class SequenceDiagramDsl @PublishedApi internal constructor() : Dsl, IDslEntry {
	var title: Title? = null
	override val participants: MutableSet<Participant> = mutableSetOf()
	override val messages: MutableList<Message> = mutableListOf()
	override val notes: MutableList<Note> = mutableListOf()
	override var splitContent: Boolean = true

	override fun toString(): String {
		return arrayOf(title, toContentString()).doSplit()
	}
}
