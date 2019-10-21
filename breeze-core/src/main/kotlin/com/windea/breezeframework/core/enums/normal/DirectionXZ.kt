package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于XZ轴的二维方向。*/
@Name("基于XZ轴的二维方向", simpleChinese)
enum class DirectionXZ(
	val dimension: Dimension
) {
	@Name("原点", simpleChinese)
	Origin(Point),
	@Name("前", simpleChinese)
	Forward(Length),
	@Name("后", simpleChinese)
	Backward(Length),
	@Name("左", simpleChinese)
	Left(Width),
	@Name("右", simpleChinese)
	Right(Width)
}
