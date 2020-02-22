package com.windea.breezeframework.http

import java.net.http.*
import java.time.*

/**
 * Http请求的配置。
 */
data class HttpRequestConfiguration(
	val headers: MutableMap<String, List<String>> = mutableMapOf(),
	val query: MutableMap<String, List<String>> = mutableMapOf(),
	var timeout: Duration? = null,
	var version: HttpClient.Version? = null,
	var expectContinue: Boolean? = null,
	var encoding: String = "UTF-8"
) {
	fun header(name: String, value: String) {
		this.headers[name] = listOf(value)
	}

	fun header(name: String, vararg values: String) {
		this.headers[name] = listOf(*values)
	}

	fun query(name: String, value: String) {
		this.query[name] = listOf(value)
	}

	fun query(name: String, vararg values: String) {
		this.query[name] = listOf(*values)
	}
}

