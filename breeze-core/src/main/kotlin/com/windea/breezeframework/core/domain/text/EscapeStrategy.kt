package com.windea.breezeframework.core.domain.text

/**
 * 转义策略。
 * @see EscapeType
 */
interface EscapeStrategy {
	/**转义的字符串组。*/
	val escapeStrings: Array<String>

	/**转义后的字符串组。*/
	val escapedStrings: Array<String>
}
