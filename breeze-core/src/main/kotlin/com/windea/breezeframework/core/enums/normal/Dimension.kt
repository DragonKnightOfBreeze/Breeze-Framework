package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**维度。*/
@Name("维度", simpleChinese)
enum class Dimension {
	@Name("点", simpleChinese)
	Point,
	@Name("长度", simpleChinese)
	Length,
	@Name("宽度", simpleChinese)
	Width,
	@Name("高度", simpleChinese)
	Height
}
