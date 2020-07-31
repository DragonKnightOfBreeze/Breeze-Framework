@file:JvmName("TupleExtensions")

package com.windea.breezeframework.javafx.extensions

import com.windea.breezeframework.core.types.*
import javafx.geometry.*

/**将浮点类型的二元素元组转化为二维点。*/
fun TypedTuple2<Double>.toPoint2D() = Point2D(first, second)

/**将浮点类型的三元素元组转化为三维点。*/
fun TypedTuple3<Double>.toPoint3D() = Point3D(first, second, third)

/**将浮点类型的四元素元组转化为二维矩形。*/
fun TypedTuple4<Double>.toRectangle2D() = Rectangle2D(first, second, third, fourth)
