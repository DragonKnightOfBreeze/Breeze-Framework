package com.windea.breezeframework.core.domain.text

/**
 * 匹配策略。
 * @see MatchType
 */
interface MatchStrategy {
	/**转化为对应的正则表达式的方法。*/
	val regexTransform: (String) -> String
}
