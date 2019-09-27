@file:Suppress("unused")

package com.windea.breezeframework.javafx.extensions

import com.windea.breezeframework.core.extensions.*
import javafx.geometry.*
import javafx.scene.paint.*
import kotlin.random.*

/**得到随机二维点。包含下限而不包含上限。*/
fun Random.nextPoint2D(): Point2D = Point2D(nextDouble(), nextDouble())

/**得到指定范围内的随机二维点。包含下限而不包含上限。*/
fun Random.nextPoint2D(min: Tuple2<Double>, max: Tuple2<Double>): Point2D =
	Point2D(nextDouble(min.first, max.first), nextDouble(min.second, max.second))

/**得到指定范围内的随机三维点。包含下限而不包含上限。*/
fun Random.nextPoint3D(): Point3D = Point3D(nextDouble(), nextDouble(), nextDouble())

/**得到指定范围内的随机三维点。包含下限而不包含上限。*/
fun Random.nextPoint3D(min: Tuple3<Double>, max: Tuple3<Double>): Point3D =
	Point3D(nextDouble(min.first, max.first), nextDouble(min.second, max.second), nextDouble(min.third, max.third))

/**得到随机矩形。包含下限而不包含上限。*/
fun Random.nextRectangle2D(): Rectangle2D = Rectangle2D(nextDouble(), nextDouble(), nextDouble(), nextDouble())

/**得到指定范围内的随机矩形。包含下限而不包含上限。*/
fun Random.nextRectangle2D(min: Tuple4<Double>, max: Tuple4<Double>): Rectangle2D =
	Rectangle2D(
		nextDouble(min.first, max.first), nextDouble(min.second, max.second),
		nextDouble(min.third, max.third), nextDouble(min.fourth, max.fourth)
	)

/**得到随机颜色。*/
fun Random.nextColor(): Color = Color.color(nextDouble(), nextDouble(), nextDouble())
