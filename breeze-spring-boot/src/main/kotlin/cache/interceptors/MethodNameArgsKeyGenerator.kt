package com.windea.breezeframework.springboot.cache.interceptors

import org.springframework.cache.interceptor.*
import java.lang.reflect.*

/**
 * 基于方法名和参数的缓存键生成器。
 *
 * * 生成以方法名为路径，以参数为查询参数的Url格式的键。
 * * 示例："/findAll", "/findByName?name=Windea"
 **/
class MethodNameArgsKeyGenerator : KeyGenerator {
	override fun generate(target: Any, method: Method, vararg params: Any?): Any {
		val path = "/${method.name}"
		if(params.isEmpty()) return path
		val query = (method.parameters zip params).joinToString("&") { (k, v) -> "${k.name}=$v" }
		return "$path?$query"
	}
}
