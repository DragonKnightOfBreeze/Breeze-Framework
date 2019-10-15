package com.windea.breezeframework.dsl.api

import com.windea.breezeframework.dsl.*

//TODO

interface IRichTextDslElement : DslElement

interface IRichTextDslInlineElement : IRichTextDslElement


class IRichTextText : IRichTextDslInlineElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}

class IRichTextIcon : IRichTextDslInlineElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}

class IRichTextImage : IRichTextDslInlineElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}

class IRichTextPlaceholder : IRichTextDslInlineElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}

open class IRichTextRichText : IRichTextDslInlineElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}


class IRichTextTextBlock : IRichTextDslElement {
	override fun toString(): String {
		TODO("not implemented")
	}
}
