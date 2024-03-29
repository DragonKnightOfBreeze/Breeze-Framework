// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.http

import icu.windea.breezeframework.core.model.*
import java.net.http.*
import java.time.*

/**
 * Http请求的配置构建器。
 */
class HttpRequestConfigBuilder : Builder<HttpRequestConfig> {
	val headers: MutableMap<String, List<String>> = mutableMapOf()
	val query: MutableMap<String, List<String>> = mutableMapOf()
	var timeout: Duration? = null
	var version: HttpClient.Version? = null
	var expectContinue: Boolean? = null
	var encoding: String = "UTF-8"

	fun header(name: String, value: String) {
		headers[name] = listOf(value)
	}

	fun header(name: String, vararg values: String) {
		headers[name] = listOf(*values)
	}

	fun query(name: String, value: String) {
		query[name] = listOf(value)
	}

	fun query(name: String, vararg values: String) {
		query[name] = listOf(*values)
	}

	override fun build() = HttpRequestConfig(headers, query, timeout, version, expectContinue, encoding)
}
