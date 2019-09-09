package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.LocaleType.SimpleChinese

/**维度。*/
@Name("维度", SimpleChinese)
enum class Dimension {
	@Name("点", SimpleChinese)
	Point,
	@Name("长度", SimpleChinese)
	Length,
	@Name("宽度", SimpleChinese)
	Width,
	@Name("高度", SimpleChinese)
	Height
}
