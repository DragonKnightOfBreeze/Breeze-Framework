package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**地理方向。不包含斜向。*/
@Name("地理方向", simpleChinese)
enum class GeographyDirection4 {
	@Name("原点", simpleChinese)
	Origin,
	@Name("东", simpleChinese)
	East,
	@Name("南", simpleChinese)
	South,
	@Name("西", simpleChinese)
	West,
	@Name("北", simpleChinese)
	North
}
