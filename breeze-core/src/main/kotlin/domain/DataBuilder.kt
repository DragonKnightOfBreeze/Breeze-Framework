package com.windea.breezeframework.core.domain

/**
 * 数据构建器。
 *
 * 数据构建器用于生成指定的数据实体。
 * 其中，数据构建器中的属性是可变的，数据类中的数据是不可变的。
 * 数据构建器应当是对应数据实体的内部类，或者内部单例对象。
 * 基于该数据实体的操作类，推荐拥有两个构造方法，
 * 主构造方法以该数据实体为默认参数，次构造方法以该数据构建器的构建代码块为参数。
 */
interface DataBuilder<T : DataEntity> {
	/**构建对应的数据实体。*/
	fun build():T


}
