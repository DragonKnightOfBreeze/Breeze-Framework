package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向")
enum class DirectionYZ(
	val dimension: Dimension
) {
	@Name("原点")
	Origin(Point),
	@Name("上")
	Up(Height),
	@Name("下")
	Down(Height),
	@Name("前")
	Forward(Length),
	@Name("后")
	Backward(Length)
}
