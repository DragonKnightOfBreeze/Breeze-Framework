package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于XZ轴的二维方向。*/
@Name("基于XZ轴的二维方向", SimpleChinese)
enum class DirectionXZ(
	val dimension: Dimension
) {
	@Name("原点", SimpleChinese)
	Origin(Point),
	@Name("前", SimpleChinese)
	Forward(Length),
	@Name("后", SimpleChinese)
	Backward(Length),
	@Name("左", SimpleChinese)
	Left(Width),
	@Name("右", SimpleChinese)
	Right(Width)
}
