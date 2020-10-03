// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal。
//所有的Dsl元素和Dsl构建方法都必须添加对应的Dsl注解。
//运算符重载规则：`+"inlineText"`表示文本，`-"inlineText"`表示注释，`!"inlineText"`表示内联子元素，`"inlineText"{ }`表示块子元素。
//文本属性以外的默认属性通过内联中缀方法构建。
//Dsl构建方法需要尽可能地写成扩展方法。
//Dsl的主要功能是生成处理后的字符串，尽量避免添加其他无关的功能。

//注意：
//通过抑制编译错误，可以做到：
//内部可见性的内联类构造器
//非顶级声明的内联类
//不为json和yaml提供dsl，因为它们可以非常方便地使用集合结构表示

/**
 * Dsl marker of [Breeze Framework Dsl Api](https://github.com/DragonKnightOfBreeze/Breeze-Framework/tree/master/breeze-dsl).
 */
@DslMarker
@MustBeDocumented
annotation class DslApiMarker
