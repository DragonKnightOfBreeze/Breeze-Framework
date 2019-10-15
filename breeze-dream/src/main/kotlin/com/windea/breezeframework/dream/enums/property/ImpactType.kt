package com.windea.breezeframework.dream.enums.property

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**作用类型。*/
@Name("作用类型", simpleChinese)
enum class ImpactType {
	@Name("普通", simpleChinese)
	General,
	@Name("斩击", simpleChinese)
	Slash,
	@Name("突刺", simpleChinese)
	Stub,
	@Name("打击", simpleChinese)
	Strike,
	@Name("射击", simpleChinese)
	Shoot
}
