// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.extension.*
import kotlin.math.*

/**
 * 三维向量
 */
data class Vector3(
	val x: Float,
	val y: Float,
	val z: Float,
) : Vector<Vector3> {
	override val lengthSquared: Float = x * x + y * y + z * z
	override val length: Float = sqrt(lengthSquared)
	override val unitVector = this / length
	override val isOriginVector = x == 0f && y == 0f && z == 0f
	override val isUnitVector = length == 1f


	operator fun compareTo(other: Vector3): Int = length.compareTo(other.length)

	operator fun plus(other: Vector2): Vector3 = this.plus(other.toVector3())

	operator fun plus(other: Vector3): Vector3 = Vector3(x + other.x, y + other.y, z + other.z)

	operator fun minus(other: Vector2): Vector3 = this.minus(other.toVector3())

	operator fun minus(other: Vector3): Vector3 = Vector3(x - other.x, y - other.y, z - other.z)

	operator fun times(other: Int): Vector3 = Vector3(x * other, y * other, z * other)

	operator fun times(other: Long) = Vector3(x * other, y * other, z * other)

	operator fun times(other: Float) = Vector3(x * other, y * other, z * other)

	operator fun div(other: Int) = Vector3(x / other, y / other, z / other)

	operator fun div(other: Long) = Vector3(x / other, y / other, z / other)

	operator fun div(other: Float) = Vector3(x / other, y / other, z / other)

	operator fun unaryPlus() = this

	operator fun unaryMinus() = Vector3(-x, -y, -z)


	/**得到两个向量之间的距离的平方。*/
	infix fun distanceSquared(other: Vector3): Float {
		val distanceX = other.x - x
		val distanceY = other.y - y
		val distanceZ = other.z - z
		return distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ
	}

	/**得到两个向量之间的距离。*/
	infix fun distance(other: Vector3): Float = sqrt(this distanceSquared other)

	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector2): Float = length * other.length

	/**得到两个向量的数量积。*/
	infix fun dotProduct(other: Vector3): Float = length * other.length

	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector3): Vector3 {
		return Vector3(
			y * other.z - z * other.y,
			z * other.x - x * other.z,
			x * other.y - y * other.x
		)
	}

	/**得到两个向量的向量积。*/
	infix fun vectorProduct(other: Vector2) = this.vectorProduct(other.toVector3())


	companion object {
		/**零向量。*/
		val origin = Vector3(0f, 0f, 0f)
	}
}
