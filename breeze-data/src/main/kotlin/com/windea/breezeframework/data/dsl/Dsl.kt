@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.dsl

/////////////Top interfaces

interface Dsl {
	companion object
}

interface DslConfig {
	companion object
}

//////////////General Dsl interfaces and related extensions

interface CanWrapContent {
	var wrapContent: Boolean
}

interface CanIndentContent {
	var indentContent: Boolean
}

inline infix fun <T> T.wrap(value: Boolean) where T : CanWrapContent = this.also { wrapContent = value }

inline fun <T> T.wrap() where T : CanWrapContent = this.also { wrapContent = true }

inline fun <T> T.unwrap() where T : CanWrapContent = this.also { wrapContent = false }

inline infix fun <T> T.indent(value: Boolean) where T : CanIndentContent = this.also { indentContent = value }

inline fun <T> T.indent() where T : CanIndentContent = this.also { indentContent = true }

inline fun <T> T.unindent() where T : CanIndentContent = this.also { indentContent = false }
