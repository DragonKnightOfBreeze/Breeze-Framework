package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese
import com.windea.breezeframework.dream.enums.category.MagicSuperCategory.*

/**魔法分类。*/
@Name("魔法分类", simpleChinese)
enum class MagicCategory(
	val superCategory: MagicSuperCategory
) {
	@Name("风息魔法", simpleChinese)
	WindMagic(Charm),
	@Name("冷气魔法", simpleChinese)
	ColdMagic(Charm),
	@Name("火炎魔法", simpleChinese)
	FireMagic(Charm),
	@Name("电磁魔法", simpleChinese)
	ElectricMagic(Charm),
	@Name("次元魔法", simpleChinese)
	DimensionMagic(Charm),
	
	@Name("灵魂魔法", simpleChinese)
	SoulMagic(Sorcery),
	@Name("结晶魔法", simpleChinese)
	CrystalMagic(Sorcery),
	@Name("刻印魔法", simpleChinese)
	SealMagic(Sorcery),
	@Name("暗影魔法", simpleChinese)
	ShadowMagic(Sorcery),
	@Name("死灵魔法", simpleChinese)
	DeadMagic(Sorcery),
	
	@Name("圣光魔法", simpleChinese)
	SacredMagic(Aethery),
	@Name("灵光魔法", simpleChinese)
	ShineMagic(Aethery),
	@Name("混乱魔法", simpleChinese)
	ChaosMagic(Aethery),
	@Name("幽光魔法", simpleChinese)
	PsionicMagic(Aethery),
	@Name("星光魔法", simpleChinese)
	StarlightMagic(Aethery),
	
	@Name("洞察魔法", simpleChinese)
	InsightMagic(Contract),
	@Name("召唤魔法", simpleChinese)
	SummonMagic(Contract),
	@Name("源血魔法", simpleChinese)
	SorceriumMagic(Contract),
	@Name("虹光魔法", simpleChinese)
	AetheriumMagic(Contract)
}


/**武器顶级分类。*/
@Name("武器顶级分类", simpleChinese)
enum class MagicSuperCategory {
	@Name("法术", simpleChinese)
	Charm,
	@Name("咒术", simpleChinese)
	Sorcery,
	@Name("灵术", simpleChinese)
	Aethery,
	@Name("精灵术", simpleChinese)
	Contract
}
