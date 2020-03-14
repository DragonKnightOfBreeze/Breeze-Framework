package com.windea.breezeframework.core.domain

/**
 * 经过处理的字符序列。
 *
 * 这种字符序列的抽象方法由它的[text]属性委托实现，
 * 但是在转化成字符串时，可能需要进行额外的处理，例如使用特定的标记字符串包围原始文本。
 * @property text 原始文本。
 */
interface HandledCharSequence : CharSequence {
	val text:CharSequence
	override val length get() = text.length
	override fun get(index:Int) = text[index]
	override fun subSequence(startIndex:Int, endIndex:Int) = text.subSequence(startIndex, endIndex)
	override fun toString():String
}

/**
 * 被委托的字符序列。
 *
 * 这种字符序列的抽象方法由它的[text]属性委托实现。
 * @property text 原始文本。
 */
interface DelegatedCharSequence : CharSequence {
	val text:CharSequence
	override val length get() = text.length
	override fun get(index:Int) = text[index]
	override fun subSequence(startIndex:Int, endIndex:Int) = text.subSequence(startIndex, endIndex)
	override fun toString():String
}
