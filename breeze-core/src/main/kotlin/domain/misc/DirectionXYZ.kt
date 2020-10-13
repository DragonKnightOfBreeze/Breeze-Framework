// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain.misc

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.misc.Dimension.*

/**基于XYZ轴的三维方向。*/
@Name("基于XYZ轴的三维方向")
enum class DirectionXYZ(
	val dimension: Dimension,
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
