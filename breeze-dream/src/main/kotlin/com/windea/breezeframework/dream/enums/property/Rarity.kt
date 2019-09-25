package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese

/**稀有度。*/
@Name("稀有度", SimpleChinese)
enum class Rarity {
	@Name("普通", SimpleChinese)
	Common,
	@Name("不普通", SimpleChinese)
	Uncommon,
	@Name("稀有", SimpleChinese)
	Rare,
	@Name("传说", SimpleChinese)
	Legend,
	@Name("史诗", SimpleChinese)
	Epic
}
