package com.windea.breezeframework.core.enums.generic

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.enums.generic.Dimension.*

/**基于XZ轴的二维方向。*/
@Name("基于XZ轴的二维方向")
enum class DirectionXZ(
	val dimension: Dimension
) {
	@Name("原点")
	Origin(Point),
	@Name("前")
	Forward(Length),
	@Name("后")
	Backward(Length),
	@Name("左")
	Left(Width),
	@Name("右")
	Right(Width)
}
