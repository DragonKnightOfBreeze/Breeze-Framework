package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*

/**稀有度。*/
@Name("稀有度")
enum class Rarity {
	@Name("普通")
	Common,
	@Name("不普通")
	Uncommon,
	@Name("稀有")
	Rare,
	@Name("传说")
	Legend,
	@Name("史诗")
	Epic
}
