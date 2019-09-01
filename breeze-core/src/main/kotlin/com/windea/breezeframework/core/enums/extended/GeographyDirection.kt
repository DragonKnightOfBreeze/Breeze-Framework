package com.windea.breezeframework.core.enums.extended

import com.windea.breezeframework.core.annotations.messages.*

/**地理方向。*/
@Name("地理方向")
enum class GeographyDirection {
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
