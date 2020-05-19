package com.windea.breezeframework.core.domain.text

import com.windea.breezeframework.core.annotations.*

/**
 * 显示格式。
 * @see LetterCase
 * @see ReferenceCase
 */
interface DisplayCase {
	/**分割方法。*/
	@InternalUsageApi
	val splitter: (CharSequence) -> List<String>

	/**序列的分割方法。*/
	@InternalUsageApi
	val sequenceSplitter: (CharSequence) -> Sequence<String>

	/**加入方法。*/
	@InternalUsageApi
	val joiner: (Iterable<CharSequence>) -> String

	/**数组的加入方法。*/
	@InternalUsageApi
	val arrayJoiner: (Array<out CharSequence>) -> String

	/**用于验证合法性的正则表达式。*/
	@InternalUsageApi
	val regex: Regex?

	/**用于验证合法性的预测。*/
	@InternalUsageApi
	val predicate: (String) -> Boolean
}
