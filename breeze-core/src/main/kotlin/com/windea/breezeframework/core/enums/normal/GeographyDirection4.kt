package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*

/**地理方向。不包含斜向。*/
@Name("地理方向")
enum class GeographyDirection4 {
	@Name("原点")
	Origin,
	@Name("东")
	East,
	@Name("南")
	South,
	@Name("西")
	West,
	@Name("北")
	North
}
