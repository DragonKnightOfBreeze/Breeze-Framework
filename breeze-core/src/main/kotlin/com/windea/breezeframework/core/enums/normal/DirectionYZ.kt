package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.LocaleType.SimpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向", SimpleChinese)
enum class DirectionYZ(
	val dimension: Dimension
) {
	@Name("原点", SimpleChinese)
	Origin(Point),
	@Name("上", SimpleChinese)
	Up(Height),
	@Name("下", SimpleChinese)
	Down(Height),
	@Name("前", SimpleChinese)
	Forward(Length),
	@Name("后", SimpleChinese)
	Backward(Length)
}
