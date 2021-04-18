// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.expression

interface Expression:CharSequence {
	val expression:String

	override val length: Int get() = expression.length

	override fun get(index: Int): Char {
		return expression.get(index)
	}

	override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
		return expression.subSequence(startIndex, endIndex)
	}
}

