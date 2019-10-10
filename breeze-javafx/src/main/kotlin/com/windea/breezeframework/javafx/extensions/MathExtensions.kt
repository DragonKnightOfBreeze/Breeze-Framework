@file:Suppress("DuplicatedCode")

package com.windea.breezeframework.javafx.extensions

import javafx.animation.*
import javafx.geometry.*
import kotlin.math.*

/**得到指定范围的双精度浮点数的插值。*/
fun interpolate(startValue: Double, endValue: Double, fraction: Double, interpolator: Interpolator): Double {
	return interpolator.interpolate(startValue, endValue, fraction)
}

/**得到指定范围的二维点的插值。*/
fun interpolate(startValue: Point2D, endValue: Point2D, fraction: Double, interpolator: Interpolator): Point2D {
	val x = interpolator.interpolate(startValue.x, endValue.x, fraction)
	val y = interpolator.interpolate(startValue.y, endValue.y, fraction)
	return Point2D(x, y)
}

/**得到指定范围的三维点的插值。*/
fun interpolate(startValue: Point3D, endValue: Point3D, fraction: Double, interpolator: Interpolator): Point3D {
	val x = interpolator.interpolate(startValue.x, endValue.x, fraction)
	val y = interpolator.interpolate(startValue.y, endValue.y, fraction)
	val z = interpolator.interpolate(startValue.z, endValue.z, fraction)
	return Point3D(x, y, z)
}


/**得到指定参数的贝塞尔曲线。*/
fun bezier(p1: Point2D, p2: Point2D, p3: Point2D, t: Double): Point2D {
	val x = (1 - t) * (1 - t) * p1.x + 2.0 * (1 - t) * t * p2.x + t * t * p3.x
	val y = (1 - t) * (1 - t) * p1.y + 2.0 * (1 - t) * t * p2.y + t * t * p3.y
	return Point2D(x, y)
}

/**得到指定参数的贝塞尔曲线。*/
fun bezier(p1: Point2D, p2: Point2D, p3: Point2D, p4: Point2D, t: Double): Point2D {
	val x = (1 - t).pow(3.0) * p1.x + 3.0 * t * (1 - t).pow(2.0) * p2.x + 3.0 * t * t * (1 - t) * p3.x + t * t * t * p4.x
	val y = (1 - t).pow(3.0) * p1.y + 3.0 * t * (1 - t).pow(2.0) * p2.y + 3.0 * t * t * (1 - t) * p3.y + t * t * t * p4.y
	return Point2D(x, y)
}
