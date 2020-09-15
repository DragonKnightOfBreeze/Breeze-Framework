/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.domain.misc

import com.windea.breezeframework.core.annotations.*

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
