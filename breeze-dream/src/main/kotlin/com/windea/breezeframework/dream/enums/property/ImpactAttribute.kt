package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**作用属性。*/
@Name("作用属性", simpleChinese)
enum class ImpactAttribute {
	@Name("物理", simpleChinese)
	Physics,
	@Name("化学", simpleChinese)
	Chemical,
	@Name("灵魂", simpleChinese)
	Soul,
	@Name("灵力", simpleChinese)
	Spirit,
	@Name("风息", simpleChinese)
	Wind,
	@Name("冷气", simpleChinese)
	Cold,
	@Name("火炎", simpleChinese)
	Fire,
	@Name("电磁", simpleChinese)
	Electric
}
