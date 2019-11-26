package com.windea.breezeframework.http

import java.net.*
import java.net.http.*
import java.time.*
import java.util.concurrent.*
import javax.net.ssl.*

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
	val authenticator: Authenticator? = null
)
