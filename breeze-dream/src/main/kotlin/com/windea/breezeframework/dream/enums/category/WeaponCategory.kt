package com.windea.breezeframework.dream.enums.category

import com.windea.breezeframework.core.annotations.messages.*
import com.windea.breezeframework.dream.enums.category.WeaponSuperCategory.*

/**武器分类。*/
@Name("武器分类")
enum class WeaponCategory(
	val superCategory: WeaponSuperCategory
) {
	@Name("匕首")
	Dagger(BladeWeapon),
	@Name("刺剑")
	Rapier(BladeWeapon),
	@Name("直剑")
	StraightSword(BladeWeapon),
	@Name("大剑")
	GreatSword(BladeWeapon),
	@Name("特大剑")
	UltraGreatSword(BladeWeapon),
	@Name("曲刀")
	CurvedSword(BladeWeapon),
	@Name("大曲刀")
	GreatCurvedSword(BladeWeapon),
	@Name("刀")
	Katana(BladeWeapon),
	@Name("鞭")
	Whip(BladeWeapon),
	
	@Name("枪")
	Spear(LongArmWeapon),
	@Name("长枪")
	Lance(LongArmWeapon),
	@Name("戟")
	Hambert(LongArmWeapon),
	@Name("镰刀")
	Scythe(LongArmWeapon),
	
	@Name("长棍")
	QuarterStaff(StrikeWeapon),
	@Name("槌")
	Hammer(StrikeWeapon),
	@Name("大槌")
	GreatHammer(StrikeWeapon),
	
	@Name("弓")
	Bow(ShootWeapon),
	@Name("大弓")
	GreatBow(ShootWeapon),
	@Name("弩")
	Crossbow(ShootWeapon),
	@Name("手枪")
	Pistol(ShootWeapon),
	@Name("轻枪械")
	LightRifle(ShootWeapon),
	@Name("重枪械")
	HeavyRifle(ShootWeapon),
	
	@Name("小型盾")
	SmallShield(Shield),
	@Name("中型盾")
	MediumShield(Shield),
	@Name("大型盾")
	GreatShield(Shield),
	
	@Name("法杖")
	Staff(MagicInterface),
	@Name("魔法石")
	MagicStone(MagicInterface),
	@Name("护符")
	Talisman(MagicInterface),
	@Name("圣铃")
	Chime(MagicInterface),
	@Name("书籍")
	Book(MagicInterface)
}


/**武器顶级分类。*/
@Name("武器顶级分类")
enum class WeaponSuperCategory {
	@Name("刀刃武器")
	BladeWeapon,
	@Name("长柄武器")
	LongArmWeapon,
	@Name("斩击武器")
	SlashWeapon,
	@Name("打击武器")
	StrikeWeapon,
	@Name("射击武器")
	ShootWeapon,
	@Name("盾牌")
	Shield,
	@Name("魔法媒介")
	MagicInterface
}
