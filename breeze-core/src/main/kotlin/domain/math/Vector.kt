// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain.math

import java.io.*

/**
 * 向量
 */
interface Vector<T : Vector<T>> : Serializable {
	/**模长的平方*/
	val lengthSquared: Float

	/**模长*/
	val length: Float

	/**单位向量。*/
	val unitVector: T

	/**是否是零向量。*/
	val isOriginVector: Boolean

	/**是否是单位向量。*/
	val isUnitVector: Boolean
}
