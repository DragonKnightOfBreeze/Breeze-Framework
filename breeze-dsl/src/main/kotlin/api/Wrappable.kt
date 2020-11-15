// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 内容可换行的DSL元素。
 */
@DslApiMarker
interface Wrappable {
	var wrapContent: Boolean
}
