package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese
import com.windea.breezeframework.core.enums.normal.Dimension.*

/**基于YZ轴的二维方向。*/
@Name("基于YZ轴的二维方向", simpleChinese)
enum class DirectionYZ(
	val dimension: Dimension
) {
	@Name("原点", simpleChinese)
	Origin(Point),
	@Name("上", simpleChinese)
	Up(Height),
	@Name("下", simpleChinese)
	Down(Height),
	@Name("前", simpleChinese)
	Forward(Length),
	@Name("后", simpleChinese)
	Backward(Length)
}
