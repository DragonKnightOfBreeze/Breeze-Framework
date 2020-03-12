package com.windea.breezeframework.core.enums.text

/**
 * 格式策略。
 * @see LetterCase
 * @see ReferenceCase
 */
interface CaseStrategy {
	/**分割方法。*/
	val splitter: (CharSequence) -> List<String>

	/**序列的分割方法。*/
	val sequenceSplitter: (CharSequence) -> Sequence<String>

	/**加入方法。*/
	val joiner: (Iterable<CharSequence>) -> String

	/**数组的加入方法。*/
	val arrayJoiner: (Array<out CharSequence>) -> String

	/**用于验证合法性的正则表达式。*/
	val regex: Regex?

	/**用于验证合法性的预测。*/
	val predicate: (String) -> Boolean
}
