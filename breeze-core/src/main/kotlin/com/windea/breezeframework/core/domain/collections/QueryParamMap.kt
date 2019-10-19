package com.windea.breezeframework.core.domain.collections

import com.windea.breezeframework.core.extensions.*

//TODO 继承自MultiValueMap

/**查询参数映射。*/
class QueryParamMap internal constructor(
	map: Map<String, List<String>> = mapOf()
) : Map<String, List<String>> by map {
	internal constructor(query: String) : this(
		query.split("&").map { s -> s.split("=", limit = 2) }.groupBy({ it[0] }, { it[1] })
	)
	
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

