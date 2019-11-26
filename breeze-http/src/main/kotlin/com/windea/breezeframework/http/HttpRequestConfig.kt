package com.windea.breezeframework.http

import java.net.http.*
import java.time.*

data class HttpRequestConfig(
	val headers: Map<String, List<String>> = mutableMapOf(),
	val query: Map<String, List<String>> = mutableMapOf(),
	val timeout: Duration? = null,
	val version: HttpClient.Version? = null,
	val expectContinue: Boolean? = null
)
