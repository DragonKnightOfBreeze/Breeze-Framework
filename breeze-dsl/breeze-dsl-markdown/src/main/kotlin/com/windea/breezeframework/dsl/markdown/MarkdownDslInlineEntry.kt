package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.*

/**Markdown领域特定语言的内联入口。*/
@MarkdownDsl
interface MarkdownDslInlineEntry : DslEntry, CriticMarkupDslInlineEntry
