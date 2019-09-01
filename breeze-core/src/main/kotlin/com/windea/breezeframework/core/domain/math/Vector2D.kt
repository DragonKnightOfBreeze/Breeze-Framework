package com.windea.breezeframework.core.domain.math

import kotlin.math.*

/**二维向量。*/
data class Vector2D(
	val x: Float,
	val y: Float
) {
	/**模长。*/
	val length = sqrt(x.pow(2) + y.pow(2))
	/**单位向量。*/
	val unitVector = this / length
	/**是否是零向量。*/
	val isOriginVector = x == 0f && y == 0f
	/**是否是单位向量。*/
	val isUnitVector = length == 1f
	/**基于(0, 0)的角度。返回弧度。*/
	val radian = this includedRadian Vector2D(0f, 0f)
	
	
	operator fun compareTo(other: Vector2D) = length.compareTo(other.length)
	
	operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
	
	operator fun plus(other: Vector3D) = this.toVector3D().plus(other)
	
	operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
	
	operator fun minus(other: Vector3D) = this.toVector3D().plus(other)
	
	operator fun times(other: Int) = Vector2D(x * other, y * other)
	
	operator fun times(other: Float) = Vector2D(x * other, y * other)
	
	operator fun div(other: Int) = Vector2D(x / other, y / other)
	
	operator fun div(other: Float) = Vector2D(x / other, y / other)
	
	operator fun unaryPlus() = this
	
	operator fun unaryMinus() = Vector2D(-x, -y)
	
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector2D): Float {
		return length * other.length
	}
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3D) = this.toVector3D().vectorProduct(other)
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2D) = this.toVector3D().vectorProduct(other.toVector3D())
	
	/**得到两个向量的夹角。结果在-PI到PI之间。返回弧度。*/
	infix fun includedRadian(other: Vector2D): Float {
		var radian = atan2(other.y.toDouble(), other.x.toDouble()) - atan2(y.toDouble(), x.toDouble())
		if(radian > PI) radian -= PI * 2
		else if(radian < -PI) radian += PI * 2
		return radian.toFloat()
	}
	
	
	/**转化为三维向量。*/
	fun toVector3D(): Vector3D {
		return Vector3D(x, y, 0f)
	}
	
	
	companion object {
		/**零向量。*/
		val origin = Vector2D(0f, 0f)
	}
}
