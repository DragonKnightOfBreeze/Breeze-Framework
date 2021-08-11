// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.http

import java.net.http.*
import java.time.*

/**
 * Http请求的配置。
 */
data class HttpRequestConfig(
	val headers: Map<String, List<String>> = mapOf(),
	val query: Map<String, List<String>> = mapOf(),
	val timeout: Duration? = null,
	val version: HttpClient.Version? = null,
	val expectContinue: Boolean? = null,
	val encoding: String = "UTF-8",
) {
}

