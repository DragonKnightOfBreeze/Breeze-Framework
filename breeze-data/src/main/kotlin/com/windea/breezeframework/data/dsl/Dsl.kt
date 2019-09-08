@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.dsl

/////////////Top interfaces

/**Dsl。即，领域专用语言。*/
interface Dsl {
	companion object
}

/**Dsl的配置。*/
interface DslConfig {
	companion object
}

//////////////General Dsl interfaces and related extensions

/**可换行内容。*/
interface CanWrapContent {
	var wrapContent: Boolean
}

/**可缩进内容。*/
interface CanIndentContent {
	var indentContent: Boolean
}

/**设置是否换行内容。*/
inline infix fun <T> T.wrap(value: Boolean) where T : CanWrapContent = this.also { wrapContent = value }

/**换行内容。*/
inline fun <T> T.wrap() where T : CanWrapContent = this.also { wrapContent = true }

/**不换行内容。*/
inline fun <T> T.unwrap() where T : CanWrapContent = this.also { wrapContent = false }

/**设置是否缩进内容。*/
inline infix fun <T> T.indent(value: Boolean) where T : CanIndentContent = this.also { indentContent = value }

/**缩进内容。*/
inline fun <T> T.indent() where T : CanIndentContent = this.also { indentContent = true }

/**不缩进内容。*/
inline fun <T> T.unindent() where T : CanIndentContent = this.also { indentContent = false }
