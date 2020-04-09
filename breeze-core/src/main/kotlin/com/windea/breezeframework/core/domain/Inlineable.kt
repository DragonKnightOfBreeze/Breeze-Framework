package com.windea.breezeframework.core.domain

/**
 * 可内联的类。
 *
 * 此类的实例可以直接作为字符序列使用，其抽象方法由它的[text]属性委托实现。
 * 但在转化成字符串时，可能需要进行额外的处理，例如使用特定的标记字符串包围原始文本。
 * @property text 原始文本。
 */
interface Inlineable : CharSequence {
	val text:CharSequence
	override val length get() = text.length

	override fun get(index:Int) = text[index]

	override fun subSequence(startIndex:Int, endIndex:Int) = text.subSequence(startIndex, endIndex)

	override fun toString():String
}
