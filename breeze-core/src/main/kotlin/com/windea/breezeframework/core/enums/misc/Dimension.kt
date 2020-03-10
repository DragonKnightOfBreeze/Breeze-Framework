package com.windea.breezeframework.core.enums.misc

import com.windea.breezeframework.core.annotations.*

/**维度。*/
@Name("维度")
enum class Dimension {
	@Name("点")
	Point,
	@Name("长度")
	Length,
	@Name("宽度")
	Width,
	@Name("高度")
	Height
}