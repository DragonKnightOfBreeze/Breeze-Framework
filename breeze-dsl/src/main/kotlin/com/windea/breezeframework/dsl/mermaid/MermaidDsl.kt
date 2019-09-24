@file:Reference("[Mermaid](https://mermaidjs.github.io)")

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

/**Mermaid Dsl。*/
interface Mermaid : Dsl

/**Mermaid Dsl的元素。*/
interface MermaidDslElement : DslElement

/**Mermaid Dsl的配置。*/
abstract class MermaidConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}
