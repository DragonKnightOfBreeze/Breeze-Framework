package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese

/**作用类型。*/
@Name("作用类型", SimpleChinese)
enum class ImpactType {
	@Name("普通", SimpleChinese)
	General,
	@Name("斩击", SimpleChinese)
	Slash,
	@Name("突刺", SimpleChinese)
	Stub,
	@Name("打击", SimpleChinese)
	Strike,
	@Name("射击", SimpleChinese)
	Shoot
}
