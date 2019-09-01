package com.windea.breezeframework.springboot.cache.keygenerators

import com.windea.breezeframework.core.extensions.*
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
		val query = (method.parameters.map { it.name } zip params).joinToString("&") { (k, v) -> "$k=$v" }
		return "$path${query.ifNotEmpty { "?$it" }}"
	}
}
