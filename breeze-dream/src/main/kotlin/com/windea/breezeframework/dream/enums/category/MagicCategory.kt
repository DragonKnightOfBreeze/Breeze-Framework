package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.SimpleChinese
import com.windea.breezeframework.dream.enums.category.MagicSuperCategory.*

/**魔法分类。*/
@Name("魔法分类", SimpleChinese)
enum class MagicCategory(
	val superCategory: MagicSuperCategory
) {
	@Name("风息魔法", SimpleChinese)
	WindMagic(Charm),
	@Name("冷气魔法", SimpleChinese)
	ColdMagic(Charm),
	@Name("火炎魔法", SimpleChinese)
	FireMagic(Charm),
	@Name("电磁魔法", SimpleChinese)
	ElectricMagic(Charm),
	@Name("次元魔法", SimpleChinese)
	DimensionMagic(Charm),
	
	@Name("灵魂魔法", SimpleChinese)
	SoulMagic(Sorcery),
	@Name("结晶魔法", SimpleChinese)
	CrystalMagic(Sorcery),
	@Name("刻印魔法", SimpleChinese)
	SealMagic(Sorcery),
	@Name("暗影魔法", SimpleChinese)
	ShadowMagic(Sorcery),
	@Name("死灵魔法", SimpleChinese)
	DeadMagic(Sorcery),
	
	@Name("圣光魔法", SimpleChinese)
	SacredMagic(Aethery),
	@Name("灵光魔法", SimpleChinese)
	ShineMagic(Aethery),
	@Name("混乱魔法", SimpleChinese)
	ChaosMagic(Aethery),
	@Name("幽光魔法", SimpleChinese)
	PsionicMagic(Aethery),
	@Name("星光魔法", SimpleChinese)
	StarlightMagic(Aethery),
	
	@Name("洞察魔法", SimpleChinese)
	InsightMagic(Contract),
	@Name("召唤魔法", SimpleChinese)
	SummonMagic(Contract),
	@Name("源血魔法", SimpleChinese)
	SorceriumMagic(Contract),
	@Name("虹光魔法", SimpleChinese)
	AetheriumMagic(Contract)
}


/**武器顶级分类。*/
@Name("武器顶级分类", SimpleChinese)
enum class MagicSuperCategory {
	@Name("法术", SimpleChinese)
	Charm,
	@Name("咒术", SimpleChinese)
	Sorcery,
	@Name("灵术", SimpleChinese)
	Aethery,
	@Name("精灵术", SimpleChinese)
	Contract
}
