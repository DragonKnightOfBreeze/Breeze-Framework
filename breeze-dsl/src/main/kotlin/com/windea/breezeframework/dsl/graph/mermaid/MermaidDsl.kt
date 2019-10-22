@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//REGION top annotations and interfaces

@DslMarker
private annotation class MermaidDsl

/**Mermaid Dsl。*/
@ReferenceApi("[Mermaid](https://mermaidjs.github.io)")
@MermaidDsl
abstract class Mermaid : DslBuilder

/**Mermaid Dsl的配置。*/
@ReferenceApi("[Mermaid](https://mermaidjs.github.io)")
@MermaidDsl
object MermaidConfig : DslConfig {
	private val indentSizeRange = -2..8
	
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	var preferDoubleQuote: Boolean = true
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}

/**Mermaid Dsl的入口。*/
@MermaidDsl
interface MermaidDslEntry : DslEntry

/**Mermaid Dsl的元素。*/
@MermaidDsl
interface MermaidDslElement : DslElement
