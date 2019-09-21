@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.domain.math

import kotlin.math.*

/**二维向量。*/
data class Vector3(
	val x: Float,
	val y: Float,
	val z: Float
) : Vector<Vector3> {
	/**模长。*/
	override val length = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
	/**单位向量。*/
	override val unitVector = this / length
	/**是否是零向量。*/
	override val isOriginVector = x == 0f && y == 0f && z == 0f
	/**是否是单位向量。*/
	override val isUnitVector = length == 1f
	
	
	operator fun compareTo(other: Vector3) = length.compareTo(other.length)
	
	operator fun plus(other: Vector2) = this.plus(other.toVector3())
	
	operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)
	
	operator fun minus(other: Vector2) = this.minus(other.toVector3())
	
	operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)
	
	operator fun times(other: Int) = Vector3(x * other, y * other, z * other)
	
	operator fun times(other: Long) = Vector3(x * other, y * other, z * other)
	
	operator fun times(other: Float) = Vector3(x * other, y * other, z * other)
	
	operator fun div(other: Int) = Vector3(x / other, y / other, z / other)
	
	operator fun div(other: Long) = Vector3(x / other, y / other, z / other)
	
	operator fun div(other: Float) = Vector3(x / other, y / other, z / other)
	
	operator fun unaryPlus() = this
	
	operator fun unaryMinus() = Vector3(-x, -y, -z)
	
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector2): Float = length * other.length
	
	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector3): Float = length * other.length
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3): Vector3 = Vector3(
		y * other.z - z * other.y,
		z * other.x - x * other.z,
		x * other.y - y * other.x
	)
	
	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2) = this.vectorProduct(other.toVector3())
	
	
	companion object {
		/**零向量。*/
		val origin = Vector3(0f, 0f, 0f)
	}
}


inline fun Triple<Float, Float, Float>.toVector3(): Vector3 = Vector3(this.first, this.second, this.third)

inline fun Vector3.toTriple(): Triple<Float, Float, Float> = Triple(this.x, this.y, this.z)
