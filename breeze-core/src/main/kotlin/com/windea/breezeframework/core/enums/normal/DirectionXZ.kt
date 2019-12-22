package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.enums.normal.Dimension.*

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
