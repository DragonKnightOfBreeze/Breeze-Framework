package com.windea.breezeframework.http

import java.net.*
import java.net.http.*
import java.time.*
import java.util.concurrent.*
import javax.net.ssl.*

/**
 * Http的配置。
 */
class HttpConfig {
	var cookieHandler: CookieHandler? = null
	var connectTimeout: Duration? = null
	var sslContext: SSLContext? = null
	var sslParameters: SSLParameters? = null
	var executor: Executor? = null
	var followRedirects: HttpClient.Redirect? = null
	var version: HttpClient.Version? = null
	var priority: Int? = null
	var proxy: ProxySelector? = null
	var authenticator: Authenticator? = null
	var encoding: String = "UTF-8"
}
