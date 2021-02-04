// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 包含一对可被视为节点的子元素。
 * @property sourceNodeId 源结点的编号。
 * @property targetNodeId 目标结点的编号。
 */
@DslApiMarker
interface WithNode {
	val sourceNodeId: String
	val targetNodeId: String
}
