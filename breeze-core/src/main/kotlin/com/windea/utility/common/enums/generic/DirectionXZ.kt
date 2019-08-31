package com.windea.utility.common.enums.generic

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.generic.Dimension.*

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
