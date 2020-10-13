// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain.data

import java.io.*
import java.time.temporal.*

/**
 * 可审计的对象。
 *
 * @property createdTime 创建时间。
 * @property lastUpdatedTime 最后更新时间。
 * @property createdBy 创建者。
 * @property lastUpdatedBy 最后更新者。
 */
interface Auditable<T : Temporal, U : Serializable> : Serializable {
	val createdTime: T
	var lastUpdatedTime: T
	val createdBy: U
	var lastUpdatedBy: U
}
