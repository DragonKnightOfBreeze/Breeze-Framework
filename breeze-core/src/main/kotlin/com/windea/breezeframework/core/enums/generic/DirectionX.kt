package com.windea.breezeframework.core.enums.generic

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.enums.generic.Dimension.*

/**基于X轴的一维方向。*/
@Name("基于X轴的一维方向")
enum class DirectionX(
	val dimension: Dimension
) {
	@Name("原点")
	Origin(Point),
	@Name("前")
	Forward(Length),
	@Name("后")
	Backward(Length)
}
