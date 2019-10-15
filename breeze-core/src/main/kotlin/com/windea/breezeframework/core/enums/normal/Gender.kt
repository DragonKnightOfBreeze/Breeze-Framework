package com.windea.breezeframework.core.enums.normal

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**性别。*/
@Name("性别", simpleChinese)
enum class Gender {
	@Name("男性", simpleChinese)
	Male,
	@Name("女性", simpleChinese)
	Female
}
