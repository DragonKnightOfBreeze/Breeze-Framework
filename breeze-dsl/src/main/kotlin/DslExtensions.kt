// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DslExtensions")

package icu.windea.breezeframework.dsl

inline fun renderText(builder: StringBuilder,action:StringBuilder.()->Unit){
	builder.action()
}

fun StringBuilder.appendLineIf(condition: Boolean):StringBuilder{
	if(condition) appendLine()
	return this
}

fun String.prependIndentIf(condition: Boolean,indentFirstLine:Boolean, indent: String = "    "): String {
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
