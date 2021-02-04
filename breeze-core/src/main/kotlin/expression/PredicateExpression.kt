// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.expression

class PredicateExpression(
	override val expression: String
) : Expression {
	val marker: Char? = expression.firstOrNull { it == '!' }
	val value: String = if(marker != null) expression.drop(1) else expression
	val not: Boolean = marker == '!'

	override fun equals(other: Any?): Boolean {
		return other is PredicateExpression && expression == other.expression
	}

	override fun hashCode(): Int {
		return expression.hashCode()
	}

	override fun toString(): String {
		return expression
	}
}
