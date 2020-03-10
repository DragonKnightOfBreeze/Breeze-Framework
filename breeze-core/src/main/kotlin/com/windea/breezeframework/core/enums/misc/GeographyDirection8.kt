package com.windea.breezeframework.core.enums.misc

import com.windea.breezeframework.core.annotations.*

/**地理方向。包含斜向。*/
@Name("地理方向")
enum class GeographyDirection8 {
	@Name("原点")
	Origin,
	@Name("东")
	East,
	@Name("南")
	South,
	@Name("西")
	West,
	@Name("北")
	North,
	@Name("东南")
	SouthEast,
	@Name("西南")
	SouthWest,
	@Name("东北")
	NorthEast,
	@Name("西北")
	NorthWest
}
