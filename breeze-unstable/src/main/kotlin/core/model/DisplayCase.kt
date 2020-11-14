// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.annotations.*

/**
 * 显示格式。
 * @see CaseType
 * @see ReferenceCase
 */
interface DisplayCase {
	/**分割方法。*/
	@InternalApi
	val splitter: (CharSequence) -> List<String>

	/**序列的分割方法。*/
	@InternalApi
	val sequenceSplitter: (CharSequence) -> Sequence<String>

	/**加入方法。*/
	@InternalApi
	val joiner: (Iterable<CharSequence>) -> String

	/**数组的加入方法。*/
	@InternalApi
	val arrayJoiner: (Array<out CharSequence>) -> String

	/**用于验证合法性的正则表达式。*/
	@InternalApi
	val regex: Regex?

	/**用于验证合法性的预测。*/
	@InternalApi
	val predicate: (String) -> Boolean
}
