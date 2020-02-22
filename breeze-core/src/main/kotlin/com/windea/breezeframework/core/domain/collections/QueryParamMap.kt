package com.windea.breezeframework.core.domain.collections

import com.windea.breezeframework.core.extensions.*

/**查询参数映射。*/
class QueryParamMap internal constructor(
	val map: Map<String, List<String>> = mapOf()
) : Map<String, List<String>> by map {
	internal constructor(query: String) : this(query.split("&").groupBy({ it.substringBefore("=") }, { it.substringAfter("=") }))

	/**得到指定名字的单个查询参数。*/
	fun getParam(name: String): String? {
		return this[name]?.firstOrNull()
	}

	/**得到指定名字的所用查询参数。*/
	fun getParams(name: String): List<String> {
		return this[name] ?: listOf()
	}

	override fun toString(): String {
		return this.joinToString(", ", "{", "}") { (k, v) -> "$k=$v" }
	}
}

