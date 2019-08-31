package com.windea.utility.common.enums.generic

import com.windea.utility.common.annotations.messages.*
import com.windea.utility.common.enums.generic.Dimension.*

/**基于XY轴的二维方向。*/
@Name("基于XY轴的二维方向")
enum class DirectionXY(
	val dimension: Dimension
) {
	@Name("原点")
	Origin(Point),
	@Name("上")
	Up(Height),
	@Name("下")
	Down(Height),
	@Name("左")
	Left(Length),
	@Name("右")
	Right(Length)
}
