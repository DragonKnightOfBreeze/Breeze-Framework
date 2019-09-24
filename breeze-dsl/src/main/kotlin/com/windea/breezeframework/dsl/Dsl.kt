@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.extensions.*

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal
//所有的Dsl元素和Dsl构建方法都必须添加对应的slMarker注解
//所有的Dsl构建方法都要尽可能地写成内联形式和X表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法
//所有的Dsl元素的属性都不应该声明在构造方法之内，因为可能需要对输入参数进行处理。

//REGION Dsl annotations

@DslMarker
internal annotation class GeneralDsl

//REGION Top interfaces

/**Dsl。即，领域专用语言。*/
@GeneralDsl
interface Dsl

/**Dsl的元素。*/
@GeneralDsl
interface DslElement

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

/**换行内容。*/
@GeneralDsl
inline fun <T : CanWrapContent> T.wrap() = this.wrap(true)

/**不换行内容。*/
@GeneralDsl
inline fun <T : CanWrapContent> T.unwrap() = this.wrap(false)


/**可缩进内容。*/
@GeneralDsl
interface CanIndentContent {
	var indentContent: Boolean
}

/**设置是否缩进内容。*/
@GeneralDsl
inline infix fun <T : CanIndentContent> T.indent(value: Boolean) = this.also { indentContent = value }

/**缩进内容。*/
@GeneralDsl
inline fun <T : CanIndentContent> T.indent() = this.indent(true)

/**不缩进内容。*/
@GeneralDsl
inline fun <T : CanIndentContent> T.unindent() = this.indent(false)

//REGION Useful extensions for argument handling

/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")
