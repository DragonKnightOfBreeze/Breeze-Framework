package com.windea.breezeframework.core.enums.text

/**
 * 格式化策略。
 * @see FormatType
 */
interface FormatStrategy {
	/**格式化方法。*/
	val formatter: (String, Array<out Any?>, String?) -> String
}
