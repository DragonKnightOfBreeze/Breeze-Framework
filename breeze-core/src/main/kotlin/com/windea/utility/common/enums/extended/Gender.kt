package com.windea.utility.common.enums.extended

import com.windea.utility.common.annotations.messages.*

/**性别。*/
@Name("性别")
enum class Gender {
	@Name("无性别")
	None,
	@Name("男性")
	Male,
	@Name("女性")
	Female,
	@Name("男性（模拟）")
	SimulateMale,
	@Name("女性（模拟）")
	SimulateFemale,
	@Name("男性（泛性别）")
	GenericMale,
	@Name("女性（泛性别）")
	GenericFemale
}
