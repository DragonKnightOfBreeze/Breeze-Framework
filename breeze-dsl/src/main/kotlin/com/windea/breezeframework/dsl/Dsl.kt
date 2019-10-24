@file:Suppress("NOTHING_TO_INLINE", "PropertyName")

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

//REGION top annotations and interfaces

/**公共的Dsl。*/
@DslMarker
internal annotation class GenericDsl

/**内联的Dsl。即，对应的构建方法不会自动注册对应的元素，且允许直接返回字符串。*/
internal annotation class InlineDsl

/**Dsl的构建器。*/
interface DslBuilder {
	override fun toString(): String
}

/**Dsl的配置。*/
interface DslConfig

/**Dsl的入口。*/
interface DslEntry

/**Dsl的元素。*/
interface DslElement {
	override fun toString(): String
}

//REGION dsl interfaces

/**包含可换行的内容。这个接口的优先级要高于[CanIndent]。*/
@GenericDsl
interface CanWrap {
	var wrapContent: Boolean
}

/**包含可缩进的内容。*/
@GenericDsl
interface CanIndent {
	var indentContent: Boolean
}

/**包含可生成的内容。可能替换原始文本。*/
@GenericDsl
interface CanGenerate {
	var generateContent: Boolean
	
	fun toGeneratedString(): String
}

/**可省略声明。*/
@GenericDsl
interface CanOmit {
	val isOmitted: Boolean
}

/**包含可被视为文本的子元素。*/
@GenericDsl
interface WithText<T> {
	/**添加主要的文本元素为子元素。*/
	@GenericDsl
	operator fun String.unaryPlus(): T
}

/**包含可被视为注释的子元素。*/
@GenericDsl
interface WithComment<T> {
	/**添加注释元素为子元素。*/
	@GenericDsl
	operator fun String.unaryMinus(): T
}

/**包含可被视为块的子元素。*/
@GenericDsl
interface WithBlock<T> {
	/**添加主要的块元素为子元素。*/
	@GenericDsl
	operator fun String.invoke(block: T.() -> Unit = {}): T
}

/**带有一个可用于查询的名字。这个名字不是唯一的。*/
@GenericDsl
interface WithName {
	val _name: String
}

/**包含两个和多个可被视为节点的子元素。*/
@GenericDsl
interface WithNode<N : WithName> {
	val _fromNodeName: String
	val _toNodeName: String
}

/**包含可被视为转换的子元素。*/
@GenericDsl
interface WithTransition<N : WithName, T : WithNode<N>> {
	/**根据节点元素创建过渡元素。*/
	@GenericDsl
	infix fun String.fromTo(other: String): T
	
	/**根据节点元素创建过渡元素。*/
	@GenericDsl
	infix fun String.fromTo(other: N): T = this@WithTransition.run { this@fromTo fromTo other._name }
	
	/**根据节点元素创建过渡元素。*/
	@GenericDsl
	infix fun N.fromTo(other: String): T = this@WithTransition.run { this@fromTo._name fromTo other }
	
	/**根据节点元素创建过渡元素。*/
	@GenericDsl
	infix fun N.fromTo(other: N): T = this@WithTransition.run { this@fromTo._name fromTo other._name }
	
	/**根据节点元素连续创建过渡元素。*/
	@GenericDsl
	infix fun T.fromTo(other: String): T = this@WithTransition.run { this@fromTo._toNodeName fromTo other }
	
	/**根据节点元素连续创建过渡元素。*/
	@GenericDsl
	infix fun T.fromTo(other: N): T = this@WithTransition.run { this@fromTo._toNodeName fromTo other._name }
}

//REGION build extensions

/**设置是否换行内容。*/
@GenericDsl
inline infix fun <T : CanWrap> T.wrap(value: Boolean) = this.also { wrapContent = value }

/**设置是否缩进内容。*/
@GenericDsl
inline infix fun <T : CanIndent> T.indent(value: Boolean) = this.also { indentContent = value }

/**设置是否生成内容。*/
@GenericDsl
inline infix fun <T : CanGenerate> T.generate(value: Boolean) = this.also { generateContent = value }

//REGION helpful extensions

/**将`\n`或`\r`替换成`<br>`。*/
@PublishedApi
internal fun String.replaceWithHtmlWrap() = this.replaceAll("\n" to "<br>", "\r" to "<br>")

/**将`\n`或`\r`替换成`\\n`和`\\r`。*/
@PublishedApi
internal fun String.replaceWithEscapedWrap() = this.replaceAll("\n" to "\\n", "\r" to "\\r")
