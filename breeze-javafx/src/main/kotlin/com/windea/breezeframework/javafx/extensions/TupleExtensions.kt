package com.windea.breezeframework.javafx.extensions

import com.windea.breezeframework.core.extensions.*
import javafx.geometry.*

fun TypedTuple2<Double>.toPoint2D() = Point2D(first, second)

fun TypedTuple3<Double>.toPoint3D() = Point3D(first, second, third)

fun TypedTuple4<Double>.toRectangle2D() = Rectangle2D(first, second, third, fourth)
