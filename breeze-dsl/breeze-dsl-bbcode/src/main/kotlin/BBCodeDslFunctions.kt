package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.bbcode.BBCode.*

/**生成一段BBCode文本。*/
@TopDslFunction
@BBCodeDsl
inline fun bbcode(block:Document.()->Unit) = Document().apply(block)
