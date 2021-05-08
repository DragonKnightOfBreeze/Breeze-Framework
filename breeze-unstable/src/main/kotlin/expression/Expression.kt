// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.expression

interface Expression<out T> {
	val context: ExpressionContext

	fun interpreter(): T
}

interface ExpressionContext {
	val variables: Map<String, Any?>
}

annotation class ExtendedExpression

object ExpressionParser{
	fun parseExpression(expressionString:String):Any?{
		val chars = expressionString.toCharArray()
		val size = chars.size
		var index = 0;
		while(index < size){
			val char = chars[index]

			index++;
		}
		return null
	}
}


interface VariableExpression<T>:Expression<T>{
	val variableName:String
}

@Suppress("UNCHECKED_CAST")
class VariableExpressionImpl<T>(
	override val context: ExpressionContext,
	override val variableName: String
):VariableExpression<T>{
	override fun interpreter(): T {
		return context.variables.getValue(variableName) as T
	}
}


interface UExpression<T, O> : Expression<T> {
	val other: Expression<O>
}

interface UnaryPlusExpression<T,O> : UExpression<T, O>

class IntUnaryPlusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Int>
): UnaryPlusExpression<Int,Int>{
	override fun interpreter() = other.interpreter().unaryPlus()
}

class LongUnaryPlusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Long>
): UnaryPlusExpression<Long,Long>{
	override fun interpreter() = other.interpreter().unaryPlus()
}

class FloatUnaryPlusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Float>
):UnaryPlusExpression<Float,Float>{
	override fun interpreter() = other.interpreter().unaryPlus()
}

class DoubleUnaryPlusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Double>
):UnaryPlusExpression<Double,Double>{
	override fun interpreter() = other.interpreter().unaryPlus()
}

interface UnaryMinusExpression<T,O> : UExpression<T,O>

class IntUnaryMinusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Int>
): UnaryMinusExpression<Int,Int>{
	override fun interpreter() = other.interpreter().unaryMinus()
}

class LongUnaryMinusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Long>
): UnaryMinusExpression<Long,Long>{
	override fun interpreter() = other.interpreter().unaryMinus()
}

class FloatUnaryMinusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Float>
):UnaryMinusExpression<Float,Float>{
	override fun interpreter() = other.interpreter().unaryMinus()
}

class DoubleUnaryMinusExpression(
	override val context: ExpressionContext,
	override val other: Expression<Double>
):UnaryMinusExpression<Double,Double>{
	override fun interpreter() = other.interpreter().unaryMinus()
}

interface NotExpression<T, O> : UExpression<T, O>

class BooleanNotExpression(
	override val context: ExpressionContext,
	override val other: Expression<Boolean>
):UExpression<Boolean,Boolean>{
	override fun interpreter(): Boolean {
		return other.interpreter().not()
	}
}

@ExtendedExpression
interface FlatExpression<T, O> : UExpression<T, O>


interface BiExpression<T, P, N> : Expression<T> {
	val prev: Expression<P>
	val next: Expression<N>
}

interface PlusExpression<T, P, N> : BiExpression<T, P, N>

interface MinusExpression<T, P, N> : BiExpression<T, P, N>

interface TimesExpression<T, P, N> : BiExpression<T, P, N>

interface DivExpression<T, P, N> : BiExpression<T, P, N>

interface RemExpression<T, P, N> : BiExpression<T, P, N>

@ExtendedExpression
interface PowExpression<T, P, N> : BiExpression<T, P, N>

interface InExpression<T, P, N> : BiExpression<T, P, N>

interface IsExpression<T, P, N> : BiExpression<T, P, N>

@ExtendedExpression
interface StartsWithExpression<T, P, N> : BiExpression<T, P, N>

@ExtendedExpression
interface EndsWithExpression<T, P, N> : BiExpression<T, P, N>


abstract class AbstractExpression<T>(
	override val context: ExpressionContext
) : Expression<T>


internal fun Any.isNullLike():Boolean{
	return when(this){
		is Boolean -> !this
		is Number -> toString().let{ it=="0" || it=="0.0" }
		is CharSequence -> isEmpty()
		is Array<*> -> isEmpty()
		is Collection<*> -> isEmpty()
		is Iterable<*> -> none()
		is Sequence<*> -> none()
		is Map<*,*> -> isEmpty()
		else -> false
	}
}
internal fun Any.isNotNullLike():Boolean{
	return when(this){
		is Boolean -> this
		is Number -> toString().let{ it!="0" || it!="0.0" }
		is CharSequence -> isNotEmpty()
		is Array<*> -> isNotEmpty()
		is Collection<*> -> isNotEmpty()
		is Iterable<*> -> any()
		is Sequence<*> -> any()
		is Map<*,*> -> isNotEmpty()
		else -> false
	}
}
