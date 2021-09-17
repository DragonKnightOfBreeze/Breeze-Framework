// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

import java.lang.IllegalArgumentException

class ParameterMapImpl(
    private val map: MutableMap<String, Any?> = mutableMapOf(),
    private val ignoreCase:Boolean = false
) : ParameterMap {
	override val size: Int = map.size

	private fun normalizeParameterName(name:String):String{
		return if(ignoreCase) name.lowercase() else name
	}

	override fun hasValue(name: String):Boolean {
		val paramName = normalizeParameterName(name)
		return map.containsKey(paramName)
	}

	override fun getValue(name: String): Any? {
		val paramName = normalizeParameterName(name)
		if(!hasValue(paramName)) throw IllegalArgumentException("Parameter map does not contains parameter '$paramName'.")
		return map.get(paramName)
	}

	override fun getValueOrNull(name: String): Any? {
		val paramName = normalizeParameterName(name)
		return map.get(paramName)
	}

	override fun addValue(name: String,value:Any?): ParameterMap {
		val paramName = normalizeParameterName(name)
		map.put(paramName,value)
		return this
	}
}
