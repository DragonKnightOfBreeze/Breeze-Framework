// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain.misc

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.misc.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向")
enum class DirectionYZ(
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
	Backward(Length)
}
