// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.api

/**
 * 可换行内容。
 * @property wrapContent 是否换行内容。
 */
@DslApiMarker
interface Wrappable {
	var wrapContent: Boolean
}
