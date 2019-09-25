@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.extensions.*

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal
//所有的Dsl元素和Dsl构建方法都必须添加对应的slMarker注解
//所有的Dsl构建方法都要尽可能地写成内联形式和表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法
//运算符重载规则：`+"text"`表示文本，`-"text"`表示注释，`"text" { } `表示唯一子级元素。
//文本属性以外的默认属性通过内联中缀方法构建。

//REGION Dsl annotations

@DslMarker
internal annotation class GeneralDsl

//REGION Top interfaces

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

/**Dsl的配置。*/
interface DslConfig

//REGION General Dsl elements and related extensions

/**可换行内容。*/
@GeneralDsl
interface CanWrapContent {
	var wrapContent: Boolean
}

/**设置是否换行内容。*/
@GeneralDsl
inline infix fun <T : CanWrapContent> T.wrap(value: Boolean) = this.also { wrapContent = value }


/**可缩进内容。*/
@GeneralDsl
interface CanIndentContent {
	var indentContent: Boolean
}

/**设置是否缩进内容。*/
@GeneralDsl
inline infix fun <T : CanIndentContent> T.indent(value: Boolean) = this.also { indentContent = value }


/**可内联内容。即，可将内容直接写入字符串模版中。*/
@GeneralDsl
interface CanInlineContent {
	var inlineContent: Boolean
}

/**内联内容。*/
@GeneralDsl
inline infix fun <T : CanInlineContent> T.inline(value: Boolean) = this.also { inlineContent = value }

//REGION Useful extensions for argument handling

/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")

/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replaceAll("\n" to "\\n", "\r" to "\\r")
