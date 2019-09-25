package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese

/**作用属性。*/
@Name("作用属性", SimpleChinese)
enum class ImpactAttribute {
	@Name("物理", SimpleChinese)
	Physics,
	@Name("化学", SimpleChinese)
	Chemical,
	@Name("灵魂", SimpleChinese)
	Soul,
	@Name("灵力", SimpleChinese)
	Spirit,
	@Name("风息", SimpleChinese)
	Wind,
	@Name("冷气", SimpleChinese)
	Cold,
	@Name("火炎", SimpleChinese)
	Fire,
	@Name("电磁", SimpleChinese)
	Electric
}
