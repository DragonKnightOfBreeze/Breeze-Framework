// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.google.gson.*
import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*

/**
 * 由Gson实现的Json的序列化器。
 *
 * @see com.google.gson.Gson
 */
@BreezeComponent
open class GsonSerializer : JsonSerializer, DelegateSerializer, Configurable<GsonBuilder> {
	private val gsonBuilder by lazy { GsonBuilder() }
	val gson: Gson by lazy { gsonBuilder.create() }

	override fun configure(block: GsonBuilder.() -> Unit) {
		gsonBuilder.block()
	}

	override fun <T> serialize(target: T): String {
		return gson.toJson(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return gson.fromJson(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return gson.fromJson(value, type)
	}
}
