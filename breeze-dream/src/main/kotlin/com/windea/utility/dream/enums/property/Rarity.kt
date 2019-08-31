package com.windea.utility.dream.enums.property

import com.windea.utility.common.annotations.messages.*

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
