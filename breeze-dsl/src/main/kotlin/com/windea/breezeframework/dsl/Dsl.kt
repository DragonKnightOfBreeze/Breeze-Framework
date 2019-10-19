@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.extensions.*

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal。
//所有的Dsl元素和Dsl构建方法都必须添加对应的DslMarker注解。
//所有的Dsl构建方法都要尽可能地写成内联形式和表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法。
//运算符重载规则：`+"text"`表示文本，`-"text"`表示注释，`!"text"`表示内联子元素，`"text"{ }`表示块子元素。
//文本属性以外的默认属性通过内联中缀方法构建。
//Dsl构建方法需要尽可能地写成扩展方法。
//Dsl的主要功能是生成处理后的字符串，尽量避免添加其他无关的功能。
//toString()方法的具体实现不要要求过多，只要能够良好地打印字符串即可。

//REGION Dsl annotations

@DslMarker
private annotation class Dsl

//REGION Dsl & Dsl config & Dsl elements

/**Dsl的构建器。*/
@Dsl
interface DslBuilder {
	override fun toString(): String
}

/**Dsl的配置。*/
@Dsl
interface DslConfig

/**Dsl的元素。其构建方法会自动将其添加到父级元素。*/
@Dsl
interface DslElement {
	override fun toString(): String
}

//REGION Interfaces

/**包含（唯一主要的）可被视为文本的内容。*/
@Dsl
interface WithText<T : DslElement> {
	/**添加主要的文本元素为子元素。*/
	operator fun String.unaryPlus(): T
}

/**包含（唯一主要的）可被视为注释的内容。*/
@Dsl
interface WithComment<T : DslElement> {
	/**添加注释元素为子元素。*/
	operator fun String.unaryMinus(): T
}

/**包含（唯一主要的）可被视为块的内容。*/
@Dsl
interface WithBlock<T : DslElement> {
	/**添加主要的块元素为子元素。*/
	operator fun String.invoke(): T
	
	/**添加主要的块元素为子元素。*/
	operator fun String.invoke(builder: T.() -> Unit): T
}


/**包含可换行的内容。这个接口的优先级高于[IndentContent]。*/
@Dsl
interface WrapContent {
	var wrapContent: Boolean
}

/**包含可缩进的内容。*/
@Dsl
interface IndentContent {
	var indentContent: Boolean
}

/**包含可生成的内容。可能替换原始文本。*/
@Dsl
interface GenerateContent {
	var generateContent: Boolean
}

//REGION Build extensions

/**设置是否换行内容。*/
@Dsl
inline infix fun <T : WrapContent> T.wrap(value: Boolean) = this.also { wrapContent = value }

/**设置是否缩进内容。*/
@Dsl
inline infix fun <T : IndentContent> T.indent(value: Boolean) = this.also { indentContent = value }

/**设置是否生成内容。*/
@Dsl
inline infix fun <T : GenerateContent> T.generate(value: Boolean) = this.also { generateContent = value }

//REGION Helpful extensions

/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")

/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replaceAll("\n" to "\\n", "\r" to "\\r")
