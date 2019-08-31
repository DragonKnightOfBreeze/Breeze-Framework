package com.windea.utility.common.dsl

/**
 * Dsl（领域专用语言）。
 *
 * 仅能通过Dsl语法构建Dsl，禁止用户自行实例化Dsl以及Dsl元素。
 *
 * 对于Dsl元素，提供部分运算符重载：
 * * `+""` 添加一个文本元素。不推荐使用模版字符串。
 * * `-""` 添加一个文本元素，并作为上一级元素的唯一子元素。可以使用模版字符串。
 * * `elem + elem` 在同一行添加多个元素。后添加的文本元素可以用字符串表示。
 */
interface Dsl {
	val name: String
	
	companion object
}

/**
 * Dsl（领域专用语言）的配置。
 *
 * 可以通过DslConfig.xxx { ... }来配置对应的Dsl。
 */
interface DslConfig {
	companion object
}
