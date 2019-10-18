@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

//REGION Dsl annotations

@DslMarker
private annotation class MermaidDsl

//REGION Dsl & Dsl config & Dsl elements

/**Mermaid Dsl。*/
@ReferenceApi("[Mermaid](https://mermaidjs.github.io)")
@MermaidDsl
abstract class Mermaid : DslBuilder

/**Mermaid Dsl的配置。*/
@ReferenceApi("[Mermaid](https://mermaidjs.github.io)")
@MermaidDsl
object MermaidConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var preferDoubleQuote: Boolean = true
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}

/**Mermaid Dsl的元素。*/
@MermaidDsl
interface MermaidDslElement : DslElement
