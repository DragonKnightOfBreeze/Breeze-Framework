// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.http

import java.net.*
import java.net.http.*
import java.time.*
import java.util.concurrent.*
import javax.net.ssl.*

/**
 * Http的配置。
 */
data class HttpConfig(
	val cookieHandler: CookieHandler? = null,
	val connectTimeout: Duration? = null,
	val sslContext: SSLContext? = null,
	val sslParameters: SSLParameters? = null,
	val executor: Executor? = null,
	val followRedirects: HttpClient.Redirect? = null,
	val version: HttpClient.Version? = null,
	val priority: Int? = null,
	val proxy: ProxySelector? = null,
	val authenticator: Authenticator? = null,
	val encoding: String = "UTF-8",
) {
}
