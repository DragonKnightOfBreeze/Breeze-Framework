package com.windea.breezeframework.core.domain

/**
 * 数据构建器。
 *
 * 数据构建器用于生成指定的数据类。
 * 其中，数据构建器中的属性是可变的，数据类中的数据是不可变的。
 * 基于该数据类的操作类，推荐拥有两个构造方法，主构造方法以该数据类为默认参数，次构造方法以该数据类的代码块为参数。
 * 数据构建器的实现推荐是相关数据类的内部类，并且可以是单例的。
 * */
interface DataBuilder<T : DataEntity> {
	fun build(): T
}
