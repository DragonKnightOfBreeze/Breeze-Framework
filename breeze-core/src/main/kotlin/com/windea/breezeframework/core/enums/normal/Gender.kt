package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.Locales.SimpleChinese

/**性别。*/
@Name("性别", SimpleChinese)
enum class Gender {
	@Name("男性", SimpleChinese)
	Male,
	@Name("女性", SimpleChinese)
	Female
}
