package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*

/**Markdown领域特定语言的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement, MarkdownDslInlineEntry
