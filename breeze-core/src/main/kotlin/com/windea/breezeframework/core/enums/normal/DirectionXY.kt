package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.enums.normal.Dimension.*

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
