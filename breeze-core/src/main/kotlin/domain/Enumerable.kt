package com.windea.breezeframework.core.domain

import java.io.Serializable

/**
 * 可枚举的。
 *
 * 此接口拥有两个属性，[code]用于数据库存储，[text]用于序列化。
 */
interface Enumerable<T:Serializable> {
	val code:T
	val text:String
}
