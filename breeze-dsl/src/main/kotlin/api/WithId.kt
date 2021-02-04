// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 带有一个可用于查询的编号。
 * @property id 编号。（不保证唯一性）
 */
@DslApiMarker
interface WithId {
	val id: String
}

