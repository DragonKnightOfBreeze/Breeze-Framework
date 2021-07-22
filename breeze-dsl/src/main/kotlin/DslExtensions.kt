// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DslExtensions")

package icu.windea.breezeframework.dsl

//internal val isWindowsOsType = System.getProperty("os.name").contains("windows", true)


internal inline fun renderText(builder: StringBuilder, action: StringBuilder.() -> Unit) {
	builder.action()
}

internal fun StringBuilder.appendLineIf(condition: Boolean): StringBuilder {
	if(condition) appendLine()
	return this
}

internal fun String.prependIndentIf(condition: Boolean, indentFirstLine: Boolean, indent: String = "    "): String {
	if(condition) {
		val result = prependIndent(indent)
		return if(indentFirstLine) this else result.drop(indent.length)
	}
	return this
}


internal typealias IDslConfig = DslConfig
internal typealias IDslDocument = DslDocument
internal typealias IDslEntry = DslEntry
internal typealias IDslElement = DslElement
internal typealias IDslInlineElement = DslInlineElement
internal typealias IDslInlineEntry = DslInlineEntry
