package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.domain.consts.LocaleType.SimpleChinese

/**饰品分类。*/
@Name("饰品分类", SimpleChinese)
enum class AccessoryCategory {
	@Name("头饰", SimpleChinese)
	Headwear,
	@Name("耳饰", SimpleChinese)
	Earring,
	@Name("围巾", SimpleChinese)
	Scarf,
	@Name("项圈", SimpleChinese)
	Collar,
	@Name("首饰", SimpleChinese)
	Jewelry,
	@Name("胸针", SimpleChinese)
	Brooch,
	@Name("护身符", SimpleChinese)
	Amulet,
	@Name("手镯", SimpleChinese)
	Bracelet,
	@Name("戒指", SimpleChinese)
	Ring,
	@Name("脚镯", SimpleChinese)
	Anklet
}
