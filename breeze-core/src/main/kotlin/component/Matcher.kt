// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 匹配器。
 *
 * 匹配器用于表示指定类型的值是否匹配某种格式。
 */
interface Matcher<T>: Component {
	/**
	 * 判断指定的值是否匹配。
	 */
	fun matches(value:T):Boolean
}
