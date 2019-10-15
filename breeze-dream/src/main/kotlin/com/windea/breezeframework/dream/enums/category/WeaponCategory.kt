package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.simpleChinese
import com.windea.breezeframework.dream.enums.category.WeaponSuperCategory.*

/**武器分类。*/
@Name("武器分类", simpleChinese)
enum class WeaponCategory(
	val superCategory: WeaponSuperCategory
) {
	@Name("匕首", simpleChinese)
	Dagger(BladeWeapon),
	@Name("刺剑", simpleChinese)
	Rapier(BladeWeapon),
	@Name("直剑", simpleChinese)
	StraightSword(BladeWeapon),
	@Name("大剑", simpleChinese)
	GreatSword(BladeWeapon),
	@Name("特大剑", simpleChinese)
	UltraGreatSword(BladeWeapon),
	@Name("曲刀", simpleChinese)
	CurvedSword(BladeWeapon),
	@Name("大曲刀", simpleChinese)
	GreatCurvedSword(BladeWeapon),
	@Name("刀", simpleChinese)
	Katana(BladeWeapon),
	@Name("鞭", simpleChinese)
	Whip(BladeWeapon),
	
	@Name("枪", simpleChinese)
	Spear(LongArmWeapon),
	@Name("长枪", simpleChinese)
	Lance(LongArmWeapon),
	@Name("戟", simpleChinese)
	Hambert(LongArmWeapon),
	@Name("镰刀", simpleChinese)
	Scythe(LongArmWeapon),
	
	@Name("长棍", simpleChinese)
	QuarterStaff(StrikeWeapon),
	@Name("槌", simpleChinese)
	Hammer(StrikeWeapon),
	@Name("大槌", simpleChinese)
	GreatHammer(StrikeWeapon),
	
	@Name("弓", simpleChinese)
	Bow(ShootWeapon),
	@Name("大弓", simpleChinese)
	GreatBow(ShootWeapon),
	@Name("弩", simpleChinese)
	Crossbow(ShootWeapon),
	@Name("手枪", simpleChinese)
	Pistol(ShootWeapon),
	@Name("轻枪械", simpleChinese)
	LightRifle(ShootWeapon),
	@Name("重枪械", simpleChinese)
	HeavyRifle(ShootWeapon),
	
	@Name("小型盾", simpleChinese)
	SmallShield(Shield),
	@Name("中型盾", simpleChinese)
	MediumShield(Shield),
	@Name("大型盾", simpleChinese)
	GreatShield(Shield),
	
	@Name("法杖", simpleChinese)
	Staff(MagicInterface),
	@Name("魔法石", simpleChinese)
	MagicStone(MagicInterface),
	@Name("护符", simpleChinese)
	Talisman(MagicInterface),
	@Name("圣铃", simpleChinese)
	Chime(MagicInterface),
	@Name("书籍", simpleChinese)
	Book(MagicInterface)
}


/**武器顶级分类。*/
@Name("武器顶级分类", simpleChinese)
enum class WeaponSuperCategory {
	@Name("刀刃武器", simpleChinese)
	BladeWeapon,
	@Name("长柄武器", simpleChinese)
	LongArmWeapon,
	@Name("斩击武器", simpleChinese)
	SlashWeapon,
	@Name("打击武器", simpleChinese)
	StrikeWeapon,
	@Name("射击武器", simpleChinese)
	ShootWeapon,
	@Name("盾牌", simpleChinese)
	Shield,
	@Name("魔法媒介", simpleChinese)
	MagicInterface
}
