package com.windea.utility.common.domain.text

/**查询参数映射。*/
class QueryParamMap(
	map: Map<String, Any>
) : HashMap<String, Any>(map) {
	/**得到指定名字的单个查询参数。*/
	fun getParam(name: String): String? {
		return this[name]?.let { (if(it is Iterable<*>) it.first() else it) as String }
	}
	
	/**得到指定名字的所用查询参数。*/
	fun getParams(name: String): List<String> {
		return this[name]?.let { (it as? List<*>)?.filterIsInstance<String>() } ?: listOf()
	}
}

