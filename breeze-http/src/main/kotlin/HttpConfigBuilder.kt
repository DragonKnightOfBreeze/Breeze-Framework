// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.http

import icu.windea.breezeframework.core.model.*
import java.net.*
import java.net.http.*
import java.time.*
import java.util.concurrent.*
import javax.net.ssl.*

/**
 * Http的配置构建器。
 */
class HttpConfigBuilder : Builder<HttpConfig> {
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

	override fun build() = HttpConfig(cookieHandler, connectTimeout, sslContext, sslParameters, executor,
		followRedirects, version, priority, proxy, authenticator, encoding)
}
