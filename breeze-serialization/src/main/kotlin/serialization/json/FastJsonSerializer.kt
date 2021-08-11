// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import com.alibaba.fastjson.*
import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 由FastJson委托实现的Json数据的序列化器。
 *
 * @see com.alibaba.fastjson.JSON
 */
class FastJsonSerializer : JsonSerializer, DelegateSerializer {
	override fun <T> serialize(target: T): String {
		return JSON.toJSONString(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return JSON.parseObject(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return JSON.parseObject(value, type)
	}
}
