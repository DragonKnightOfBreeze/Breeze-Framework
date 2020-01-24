package com.windea.breezeframework.core.enums.core

/**显示格式。*/
interface FormatCase {
	/**分割方法。*/
	val splitter: (CharSequence) -> Sequence<String>
	/**加入方法。*/
	val joiner: (Iterable<CharSequence>) -> String
	/**数组的加入方法。*/
	val arrayJoiner: (Array<out CharSequence>) -> String
	/**用于验证合法性的正则表达式。*/
	val regex: Regex?
	/**用于验证合法性的预测。*/
	val predicate: (String) -> Boolean
}
