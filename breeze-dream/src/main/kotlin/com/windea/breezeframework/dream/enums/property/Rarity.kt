package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**稀有度。*/
@Name("稀有度", simpleChinese)
enum class Rarity {
	@Name("普通", simpleChinese)
	Common,
	@Name("不普通", simpleChinese)
	Uncommon,
	@Name("稀有", simpleChinese)
	Rare,
	@Name("传说", simpleChinese)
	Legend,
	@Name("史诗", simpleChinese)
	Epic
}
