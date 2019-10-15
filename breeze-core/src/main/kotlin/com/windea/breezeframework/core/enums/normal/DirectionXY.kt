package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于XY轴的二维方向。*/
@Name("基于XY轴的二维方向", simpleChinese)
enum class DirectionXY(
	val dimension: Dimension
) {
	@Name("原点", simpleChinese)
	Origin(Point),
	@Name("上", simpleChinese)
	Up(Height),
	@Name("下", simpleChinese)
	Down(Height),
	@Name("左", simpleChinese)
	Left(Length),
	@Name("右", simpleChinese)
	Right(Length)
}
