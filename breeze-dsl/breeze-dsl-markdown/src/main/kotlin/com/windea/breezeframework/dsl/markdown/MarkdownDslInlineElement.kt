package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*

/**Markdown领域特定语言的内联元素。*/
@MarkdownDsl
interface MarkdownDslInlineElement : DslElement, MarkdownDslElement, CharSequence
