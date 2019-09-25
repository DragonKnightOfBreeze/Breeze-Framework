package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese

/**地理方向。不包含斜向。*/
@Name("地理方向", SimpleChinese)
enum class GeographyDirection4 {
	@Name("原点", SimpleChinese)
	Origin,
	@Name("东", SimpleChinese)
	East,
	@Name("南", SimpleChinese)
	South,
	@Name("西", SimpleChinese)
	West,
	@Name("北", SimpleChinese)
	North
}
