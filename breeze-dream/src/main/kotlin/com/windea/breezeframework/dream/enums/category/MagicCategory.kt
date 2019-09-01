package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.dream.enums.category.MagicSuperCategory.*

/**魔法分类。*/
@Name("魔法分类")
enum class MagicCategory(
	val superCategory: MagicSuperCategory
) {
	@Name("风息魔法")
	WindMagic(Charm),
	@Name("冷气魔法")
	ColdMagic(Charm),
	@Name("火炎魔法")
	FireMagic(Charm),
	@Name("电磁魔法")
	ElectricMagic(Charm),
	@Name("次元魔法")
	DimensionMagic(Charm),
	
	@Name("灵魂魔法")
	SoulMagic(Sorcery),
	@Name("结晶魔法")
	CrystalMagic(Sorcery),
	@Name("刻印魔法")
	SealMagic(Sorcery),
	@Name("暗影魔法")
	ShadowMagic(Sorcery),
	@Name("死灵魔法")
	DeadMagic(Sorcery),
	
	@Name("圣光魔法")
	SacredMagic(Aethery),
	@Name("灵光魔法")
	ShineMagic(Aethery),
	@Name("混乱魔法")
	ChaosMagic(Aethery),
	@Name("幽光魔法")
	PsionicMagic(Aethery),
	@Name("星光魔法")
	StarlightMagic(Aethery),
	
	@Name("洞察魔法")
	InsightMagic(Link),
	@Name("召唤魔法")
	SummonMagic(Link),
	@Name("源血魔法")
	SorceriumMagic(Link),
	@Name("虹光魔法")
	AetheriumMagic(Link)
}


/**武器顶级分类。*/
@Name("武器顶级分类。")
enum class MagicSuperCategory {
	@Name("法术")
	Charm,
	@Name("咒术")
	Sorcery,
	@Name("灵术")
	Aethery,
	@Name("精灵术")
	Link
}
