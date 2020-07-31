package com.windea.breezeframework.core.domain

/**
 * 可内联的。
 *
 * 此接口的实例可以直接作为字符序列使用，其抽象方法由它的[inlineText]属性委托实现。
 * 但在转化成字符串时，可能需要进行额外的处理，例如使用特定的标记字符串包围原始文本。
 *
 * @property inlineText 内联文本。
 */
interface Inlineable : CharSequence {
	val inlineText:CharSequence
	override val length get() = inlineText.length

	override fun get(index:Int) = inlineText[index]

	override fun subSequence(startIndex:Int, endIndex:Int) = inlineText.subSequence(startIndex, endIndex)

	override fun toString():String
}
