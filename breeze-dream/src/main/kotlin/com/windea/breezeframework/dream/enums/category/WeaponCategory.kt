package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.core.consts.Locales.SimpleChinese
import com.windea.breezeframework.dream.enums.category.WeaponSuperCategory.*

/**武器分类。*/
@Name("武器分类", SimpleChinese)
enum class WeaponCategory(
	val superCategory: WeaponSuperCategory
) {
	@Name("匕首", SimpleChinese)
	Dagger(BladeWeapon),
	@Name("刺剑", SimpleChinese)
	Rapier(BladeWeapon),
	@Name("直剑", SimpleChinese)
	StraightSword(BladeWeapon),
	@Name("大剑", SimpleChinese)
	GreatSword(BladeWeapon),
	@Name("特大剑", SimpleChinese)
	UltraGreatSword(BladeWeapon),
	@Name("曲刀", SimpleChinese)
	CurvedSword(BladeWeapon),
	@Name("大曲刀", SimpleChinese)
	GreatCurvedSword(BladeWeapon),
	@Name("刀", SimpleChinese)
	Katana(BladeWeapon),
	@Name("鞭", SimpleChinese)
	Whip(BladeWeapon),
	
	@Name("枪", SimpleChinese)
	Spear(LongArmWeapon),
	@Name("长枪", SimpleChinese)
	Lance(LongArmWeapon),
	@Name("戟", SimpleChinese)
	Hambert(LongArmWeapon),
	@Name("镰刀", SimpleChinese)
	Scythe(LongArmWeapon),
	
	@Name("长棍", SimpleChinese)
	QuarterStaff(StrikeWeapon),
	@Name("槌", SimpleChinese)
	Hammer(StrikeWeapon),
	@Name("大槌", SimpleChinese)
	GreatHammer(StrikeWeapon),
	
	@Name("弓", SimpleChinese)
	Bow(ShootWeapon),
	@Name("大弓", SimpleChinese)
	GreatBow(ShootWeapon),
	@Name("弩", SimpleChinese)
	Crossbow(ShootWeapon),
	@Name("手枪", SimpleChinese)
	Pistol(ShootWeapon),
	@Name("轻枪械", SimpleChinese)
	LightRifle(ShootWeapon),
	@Name("重枪械", SimpleChinese)
	HeavyRifle(ShootWeapon),
	
	@Name("小型盾", SimpleChinese)
	SmallShield(Shield),
	@Name("中型盾", SimpleChinese)
	MediumShield(Shield),
	@Name("大型盾", SimpleChinese)
	GreatShield(Shield),
	
	@Name("法杖", SimpleChinese)
	Staff(MagicInterface),
	@Name("魔法石", SimpleChinese)
	MagicStone(MagicInterface),
	@Name("护符", SimpleChinese)
	Talisman(MagicInterface),
	@Name("圣铃", SimpleChinese)
	Chime(MagicInterface),
	@Name("书籍", SimpleChinese)
	Book(MagicInterface)
}


/**武器顶级分类。*/
@Name("武器顶级分类", SimpleChinese)
enum class WeaponSuperCategory {
	@Name("刀刃武器", SimpleChinese)
	BladeWeapon,
	@Name("长柄武器", SimpleChinese)
	LongArmWeapon,
	@Name("斩击武器", SimpleChinese)
	SlashWeapon,
	@Name("打击武器", SimpleChinese)
	StrikeWeapon,
	@Name("射击武器", SimpleChinese)
	ShootWeapon,
	@Name("盾牌", SimpleChinese)
	Shield,
	@Name("魔法媒介", SimpleChinese)
	MagicInterface
}
