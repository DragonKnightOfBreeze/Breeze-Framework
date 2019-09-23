@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.dsl

//规定：
//所有的Dsl元素和Dsl构建方法都必须添加对应的slMarker注解
//所有的Dsl构建方法都要尽可能地写成内联形式和X表达式形式，且不要显式声明返回值，使用`Xxx.also{}`的写法
//所有的Dsl元素的属性都不应该声明在构造方法之内，因为可能需要对输入参数进行处理。

//REGION Top interfaces

@DslMarker
internal annotation class GeneralDsl

/**Dsl的构建器。*/
interface DslBuilder

/**Dsl的配置。*/
interface DslConfig

//REGION General Dsl interfaces and related extensions

/**可换行内容。*/
interface CanWrapContent {
	var wrapContent: Boolean
}

/**可缩进内容。*/
interface CanIndentContent {
	var indentContent: Boolean
}


/**设置是否换行内容。*/
@GeneralDsl
inline infix fun <T> T.wrap(value: Boolean): T where T : CanWrapContent = this.also { wrapContent = value }

/**设置是否缩进内容。*/
@GeneralDsl
inline infix fun <T> T.indent(value: Boolean): T where T : CanIndentContent = this.also { indentContent = value }


/**换行内容。*/
@GeneralDsl
inline fun <T> T.wrap(): T where T : CanWrapContent = this.wrap(true)

/**不换行内容。*/
@GeneralDsl
inline fun <T> T.unwrap(): T where T : CanWrapContent = this.wrap(false)

/**缩进内容。*/
@GeneralDsl
inline fun <T> T.indent(): T where T : CanIndentContent = this.indent(true)

/**不缩进内容。*/
@GeneralDsl
inline fun <T> T.unindent(): T where T : CanIndentContent = this.indent(false)
