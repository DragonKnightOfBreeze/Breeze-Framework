// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.http

import com.windea.breezeframework.core.domain.*
import java.net.http.*
import java.time.*

/**
 * Http请求的配置。
 * @see HttpRequest.Builder
 */
data class HttpRequestConfig(
	val headers: Map<String, List<String>> = mapOf(),
	val query: Map<String, List<String>> = mapOf(),
	val timeout: Duration? = null,
	val version: HttpClient.Version? = null,
	val expectContinue: Boolean? = null,
	val encoding: String = "UTF-8",
) : DataEntity {
	class Builder : DataBuilder<HttpRequestConfig> {
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
}

