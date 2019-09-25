package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于XY轴的二维方向。*/
@Name("基于XY轴的二维方向", SimpleChinese)
enum class DirectionXY(
	val dimension: Dimension
) {
	@Name("原点", SimpleChinese)
	Origin(Point),
	@Name("上", SimpleChinese)
	Up(Height),
	@Name("下", SimpleChinese)
	Down(Height),
	@Name("左", SimpleChinese)
	Left(Length),
	@Name("右", SimpleChinese)
	Right(Length)
}
