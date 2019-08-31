package com.windea.utility.dream.enums.property

import com.windea.utility.common.annotations.messages.*

/**作用类型。*/
@Name("作用类型")
enum class ImpactType {
	@Name("普通")
	General,
	@Name("斩击")
	Slash,
	@Name("突刺")
	Stub,
	@Name("打击")
	Strike,
	@Name("射击")
	Shoot
}
