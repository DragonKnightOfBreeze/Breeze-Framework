@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.domain.math

import kotlin.math.*

/**二维向量。*/
data class Vector2(
	val x: Float,
	val y: Float
) : Vector<Vector2> {
	/**模长。*/
	override val length = sqrt(x.pow(2) + y.pow(2))
	/**单位向量。*/
	override val unitVector = this / length
	/**是否是零向量。*/
	override val isOriginVector = x == 0f && y == 0f
	/**是否是单位向量。*/
	override val isUnitVector = length == 1f
	/**基于(0, 0)的角度。返回弧度。*/
	val radian = this includedRadian Vector2(0f, 0f)
	
	
	operator fun compareTo(other: Vector2) = length.compareTo(other.length)
	
	operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
	
	operator fun plus(other: Vector3) = this.toVector3().plus(other)
	
	operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
	
	operator fun minus(other: Vector3) = this.toVector3().plus(other)
	
	operator fun times(other: Int) = Vector2(x * other, y * other)
	
	operator fun times(other: Long) = Vector2(x * other, y * other)
	
	operator fun times(other: Float) = Vector2(x * other, y * other)
	
	operator fun div(other: Int) = Vector2(x / other, y / other)
	
	operator fun div(other: Long) = Vector2(x / other, y / other)
	
	operator fun div(other: Float) = Vector2(x / other, y / other)
	
	operator fun unaryPlus() = this
	
	operator fun unaryMinus() = Vector2(-x, -y)
	
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector2): Float = length * other.length
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector3): Float = length * other.length
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2): Vector3 = this.toVector3().vectorProduct(other.toVector3())
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3): Vector3 = this.toVector3().vectorProduct(other)
	
	/**得到两个向量的夹角。结果在-PI到PI之间。返回弧度。*/
	infix fun includedRadian(other: Vector2): Float {
		var radian = atan2(other.y.toDouble(), other.x.toDouble()) - atan2(y.toDouble(), x.toDouble())
		if(radian > PI) radian -= PI * 2
		else if(radian < -PI) radian += PI * 2
		return radian.toFloat()
	}
	
	
	companion object {
		/**零向量。*/
		val origin = Vector2(0f, 0f)
	}
}


inline fun Vector2.toVector3(): Vector3 = Vector3(this.x, this.y, 0f)

inline fun Pair<Float, Float>.toVector2(): Vector2 = Vector2(this.first, this.second)

inline fun Vector2.toPair(): Pair<Float, Float> = Pair(this.x, this.y)
