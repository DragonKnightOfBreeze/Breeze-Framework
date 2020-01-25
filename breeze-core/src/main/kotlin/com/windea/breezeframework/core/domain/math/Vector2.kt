@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.domain.math

import kotlin.math.*

/**二维向量。*/
data class Vector2(
	val x: Float,
	val y: Float
) : Vector<Vector2> {
	override val lengthSquared: Float = x * x + y * y
	override val length: Float = sqrt(lengthSquared)
	override val unitVector: Vector2 = this / length
	override val isOriginVector: Boolean = x == 0f && y == 0f
	override val isUnitVector: Boolean = length == 1f

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


	/**得到两个向量之间的距离的平方。*/
	infix fun distanceSquared(other: Vector2): Float {
		val distanceX = other.x - x
		val distanceY = other.y - y
		return distanceX * distanceX + distanceY * distanceY
	}

	/**得到两个向量之间的距离。*/
	infix fun distance(other: Vector2): Float = sqrt(this distanceSquared other)

	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector2): Float = length * other.length

	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector3): Float = length * other.length

	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2): Vector3 = this.toVector3().vectorProduct(other.toVector3())

	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3): Vector3 = this.toVector3().vectorProduct(other)

	/**得到两个向量的夹角。结果在-180到180之间。返回弧度。*/
	infix fun includedAngle(other: Vector2): Float {
		return Math.toDegrees((this includedRadian other).toDouble()).toFloat()
	}

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


/**将二维向量转化为三位向量。*/
inline fun Vector2.toVector3(): Vector3 = Vector3(this.x, this.y, 0f)

/**将二维向量转化为二元素元组。*/
inline fun Vector2.toPair(): Pair<Float, Float> = Pair(this.x, this.y)
