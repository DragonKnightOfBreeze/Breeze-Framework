// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.http

import com.windea.breezeframework.core.domain.data.*
import java.net.*
import java.net.http.*
import java.time.*
import java.util.concurrent.*
import javax.net.ssl.*

/**
 * Http的配置。
 * @see HttpClient.Builder
 */
data class HttpConfig(
	val cookieHandler:CookieHandler? = null,
	val connectTimeout:Duration? = null,
	val sslContext:SSLContext? = null,
	val sslParameters:SSLParameters? = null,
	val executor:Executor? = null,
	val followRedirects:HttpClient.Redirect? = null,
	val version:HttpClient.Version? = null,
	val priority:Int? = null,
	val proxy:ProxySelector? = null,
	val authenticator:Authenticator? = null,
	val encoding:String = "UTF-8"
) : DataEntity {
	class Builder : DataBuilder<DataEntity> {
		var cookieHandler:CookieHandler? = null
		var connectTimeout:Duration? = null
		var sslContext:SSLContext? = null
		var sslParameters:SSLParameters? = null
		var executor:Executor? = null
		var followRedirects:HttpClient.Redirect? = null
		var version:HttpClient.Version? = null
		var priority:Int? = null
		var proxy:ProxySelector? = null
		var authenticator:Authenticator? = null
		var encoding:String = "UTF-8"

		override fun build() = HttpConfig(cookieHandler, connectTimeout, sslContext, sslParameters, executor,
			followRedirects, version, priority, proxy, authenticator, encoding)
	}
}
