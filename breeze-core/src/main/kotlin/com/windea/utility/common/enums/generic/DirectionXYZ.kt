package com.windea.utility.common.enums.generic

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.generic.Dimension.*

/**基于XYZ轴的三维方向。*/
@Name("基于XYZ轴的三维方向")
enum class DirectionXYZ(
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
	Backward(Length),
	@Name("左")
	Left(Width),
	@Name("右")
	Right(Width)
}
