// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.expression

class ConditionalExpression(
	override val expression: String
) : Expression {
	companion object {
		private val markers = charArrayOf('?', '!', '*', '+')
	}

	val marker: Char? = expression.lastOrNull()?.takeIf { it in markers }
	val value: String = if(marker != null) expression.dropLast(1) else expression
	val optional: Boolean = marker == '?' || marker == '*'
	val required: Boolean = marker == '!' || marker == '+'
	val multiple: Boolean = marker == '*' || marker == '+'

	override fun equals(other: Any?): Boolean {
		return other is ConditionalExpression && expression == other.expression
	}

	override fun hashCode(): Int {
		return expression.hashCode()
	}

	override fun toString(): String {
		return expression
	}
}
