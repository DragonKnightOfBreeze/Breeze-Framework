package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese

/**饰品分类。*/
@Name("饰品分类", simpleChinese)
enum class AccessoryCategory {
	@Name("头饰", simpleChinese)
	Headwear,
	@Name("耳饰", simpleChinese)
	Earring,
	@Name("围巾", simpleChinese)
	Scarf,
	@Name("项圈", simpleChinese)
	Collar,
	@Name("首饰", simpleChinese)
	Jewelry,
	@Name("胸针", simpleChinese)
	Brooch,
	@Name("护身符", simpleChinese)
	Amulet,
	@Name("手镯", simpleChinese)
	Bracelet,
	@Name("戒指", simpleChinese)
	Ring,
	@Name("脚镯", simpleChinese)
	Anklet
}
