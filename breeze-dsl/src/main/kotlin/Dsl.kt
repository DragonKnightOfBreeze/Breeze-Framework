@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.DslConstants.ss

//规定：
//所有的Dsl元素的构造方法都必须是@Published internal。
//所有的Dsl元素和Dsl构建方法都必须添加对应的Dsl注解。
//运算符重载规则：`+"text"`表示文本，`-"text"`表示注释，`!"text"`表示内联子元素，`"text"{ }`表示块子元素。
//文本属性以外的默认属性通过内联中缀方法构建。
//Dsl构建方法需要尽可能地写成扩展方法。
//Dsl的主要功能是生成处理后的字符串，尽量避免添加其他无关的功能。

//注意：
//通过抑制编译错误，可以做到：
//内部可见性的内联类构造器
//非顶级声明的内联类
//不为json和yaml提供dsl，因为它们可以非常方便地使用集合结构表示

/**领域特定语言。*/
@DslMarker
@MustBeDocumented
annotation class Dsl
