package com.windea.utility.common.domain.math

import kotlin.math.*

/**二维向量。*/
data class Vector3D(
	val x: Float,
	val y: Float,
	val z: Float
) {
	/**模长。*/
	val length = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
	/**单位向量。*/
	val unitVector = this / length
	/**是否是零向量。*/
	val isOriginVector = x == 0f && y == 0f && z == 0f
	/**是否是单位向量。*/
	val isUnitVector = length == 1f
	
	
	operator fun compareTo(other: Vector3D) = length.compareTo(other.length)
	
	operator fun plus(other: Vector3D) = Vector3D(x + other.x, y + other.y, z + other.z)
	
	operator fun plus(other: Vector2D) = this.plus(other.toVector3D())
	
	operator fun minus(other: Vector3D) = Vector3D(x - other.x, y - other.y, z - other.z)
	
	operator fun minus(other: Vector2D) = this.minus(other.toVector3D())
	
	operator fun times(other: Int) = Vector3D(x * other, y * other, z * other)
	
	operator fun times(other: Float) = Vector3D(x * other, y * other, z * other)
	
	operator fun div(other: Int) = Vector3D(x / other, y / other, z / other)
	
	operator fun div(other: Float) = Vector3D(x / other, y / other, z / other)
	
	operator fun unaryPlus() = this
	
	operator fun unaryMinus() = Vector3D(-x, -y, -z)
	
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector3D): Float {
		return length * other.length
	}
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3D): Vector3D {
		return Vector3D(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x
		)
	}
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2D) = this.vectorProduct(other.toVector3D())
	
	
	companion object {
		/**零向量。*/
		val origin = Vector3D(0f, 0f, 0f)
	}
}
