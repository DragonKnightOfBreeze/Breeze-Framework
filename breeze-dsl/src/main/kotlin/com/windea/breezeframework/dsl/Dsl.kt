@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.extensions.*

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal
//所有的Dsl元素和Dsl构建方法都必须添加对应的DslMarker注解
//所有的Dsl构建方法都要尽可能地写成内联形式和表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法
//运算符重载规则：`+"text"`表示文本，`-"text"`表示内联文本，`"text" { } `表示唯一子级元素。
//文本属性以外的默认属性通过内联中缀方法构建。

//REGION Dsl annotations

@DslMarker
internal annotation class GeneralDsl

//REGION Dsl & Dsl elements & Dsl config

/**Dsl。即，领域专用语言。*/
@GeneralDsl
interface Dsl {
	override fun toString(): String
}

/**Dsl的元素。*/
@GeneralDsl
interface DslElement {
	override fun toString(): String
}

/**具有唯一性的Dsl的元素。*/
@GeneralDsl
interface UniqueDslElement : DslElement {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}

/**包含可换行内容。*/
@GeneralDsl
interface CanWrapContent {
	var wrapContent: Boolean
}

/**包含可缩进内容。*/
@GeneralDsl
interface CanIndentContent {
	var indentContent: Boolean
}

/**包含（可被视为的）注释内容。*/
@GeneralDsl
interface CommentContent<T : DslElement> {
	/**添加注释元素为子元素。*/
	operator fun String.not(): T
}

/**包含（唯一主要的）行内子元素。*/
@GeneralDsl
interface InlineContent<T : DslElement> {
	/**添加主要的行内子元素为子元素。*/
	operator fun String.unaryPlus(): T
	
	/**以内联方式添加主要的行内子元素为子元素。允许将内容直接写入字符串模版中。将会清空之前的所有子元素。*/
	operator fun String.unaryMinus(): T = throw UnsupportedOperationException()
}

/**包含（唯一主要的）块子元素的Dsl元素。*/
@GeneralDsl
interface BlockContent<T : DslElement> {
	/**添加主要的块子元素为子元素。*/
	operator fun String.invoke(builder: T.() -> Unit): T
}

/**Dsl的配置。*/
@GeneralDsl
interface DslConfig

//REGION Build extensions

/**设置是否换行内容。*/
@GeneralDsl
inline infix fun <T : CanWrapContent> T.wrap(value: Boolean) = this.also { wrapContent = value }

/**设置是否缩进内容。*/
@GeneralDsl
inline infix fun <T : CanIndentContent> T.indent(value: Boolean) = this.also { indentContent = value }

//REGION Useful extensions for argument handling

/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")

/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replaceAll("\n" to "\\n", "\r" to "\\r")
