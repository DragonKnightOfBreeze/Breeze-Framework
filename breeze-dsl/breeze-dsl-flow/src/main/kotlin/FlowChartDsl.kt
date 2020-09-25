/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.flow.FlowChartDslDefinitions.*

/**
 * [Flow Chart](https://github.com/adrai/flowchart.js) dsl.
 */
@FlowChartDslMarker
class FlowChartDsl @PublishedApi internal constructor() : Dsl, IDslEntry {
	override val nodes: MutableSet<FlowChartDslDefinitions.Node> = mutableSetOf()
	override val connections: MutableList<FlowChartDslDefinitions.Connection> = mutableListOf()
	override var splitContent: Boolean = true

	override fun toString(): String {
		return toContentString()
	}
}
