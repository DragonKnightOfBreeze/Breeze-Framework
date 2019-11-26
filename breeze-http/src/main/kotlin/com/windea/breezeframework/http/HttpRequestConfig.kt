package com.windea.breezeframework.http

import java.net.http.*
import java.time.*

data class HttpRequestConfig(
	val headers: MutableMap<String, List<String>> = mutableMapOf(),
	val query: MutableMap<String, List<String>> = mutableMapOf(),
	val timeout: Duration? = null,
	val version: HttpClient.Version? = null,
	val expectContinue: Boolean? = null
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

