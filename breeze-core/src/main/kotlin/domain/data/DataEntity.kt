/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.domain.data

import java.io.*
import kotlin.reflect.*

/**
 * 数据实体。
 *
 * 数据实体用于存储一系列的数据，并且保证最小的可见性。
 * 数据实体应当是一个数据类，可以通过其构造方法创建，也可以通过其构建器的构建方法创建。
 */
interface DataEntity : Serializable {
	override fun equals(other:Any?):Boolean

	override fun hashCode():Int

	override fun toString():String
}
