// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 组件参数信息。
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
annotation class ComponentParam(
	/**
	 * 组件参数的名字。
	 */
	val name: String,
	/**
	 * 组件参数的类型，对应Kotlin类型，可以传递任何可以转化为该类型的参数。
	 */
	val type: String,
	/**
	 * 组件参数的默认值。
	 */
	val defaultValue: String = "",
	/**
	 * 被该组件参数重载的一组参数的名字。通过逗号隔开。
	 */
	val override: String = "",
	/**
	 * 是否向下传递参数。默认传递。
	 */
	val passing: Boolean = true
)
