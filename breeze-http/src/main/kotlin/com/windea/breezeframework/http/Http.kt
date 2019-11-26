package com.windea.breezeframework.http

import com.windea.breezeframework.core.extensions.*
import java.net.*
import java.net.http.*

//TODO 未测试

/**Http请求。提供各种http请求方法。*/
class Http {
	private var httpConfig: HttpConfig = HttpConfig()
	private var httpClient = HttpClient.newHttpClient()
	
	fun config(block: HttpConfig.() -> Unit) {
		httpConfig.block()
		buildHttpClient()
	}
	
	fun copy(): Http {
		return Http().also {
			it.httpConfig = httpConfig
			it.httpClient = httpClient
		}
	}
	
	fun get(url: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("GET", url, null, config)
	}
	
	fun post(url: String, body: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("POST", url, body, config)
	}
	
	fun put(url: String, body: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("PUT", url, body, config)
	}
	
	fun delete(url: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("DELETE", url, null, config)
	}
	
	fun header(url: String, body: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("HEADER", url, body, config)
	}
	
	fun batch(url: String, body: String, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		return request("BATCH", url, body, config)
	}
	
	private fun request(method: String, url: String, body: String?, config: HttpRequestConfig = HttpRequestConfig()): HttpResponse<String> {
		val bodyPublisher = if(body != null) HttpRequest.BodyPublishers.ofString(body) else null
		val bodyHandler = HttpResponse.BodyHandlers.ofString()
		
		val (headers, query, timeout, version, expectContinue) = config
		val uri = URI.create(url.withQuery(query))
		
		val httpRequest = HttpRequest.newBuilder(uri)
			.method(method, bodyPublisher)
			.also {
				for((name, values) in headers) {
					for(value in values) {
						it.header(name, value)
					}
				}
			}
			.also { if(timeout != null) it.timeout(timeout) }
			.also { if(expectContinue != null) it.expectContinue(expectContinue) }
			.also { if(version != null) it.version(version) }
			.build()
		return httpClient.send(httpRequest, bodyHandler)
	}
	
	private fun buildHttpClient() {
		val (cookieHandler, connectTimeout, sslContext, sslParameters, executor, followRedirects, version, priority, proxy, authenticator) = httpConfig
		httpClient = HttpClient.newBuilder()
			.also { if(cookieHandler != null) it.cookieHandler(cookieHandler) }
			.also { if(connectTimeout != null) it.connectTimeout(connectTimeout) }
			.also { if(sslContext != null) it.sslContext(sslContext) }
			.also { if(sslParameters != null) it.sslParameters(sslParameters) }
			.also { if(executor != null) it.executor(executor) }
			.also { if(followRedirects != null) it.followRedirects(followRedirects) }
			.also { if(version != null) it.version(version) }
			.also { if(priority != null) it.priority(priority) }
			.also { if(proxy != null) it.proxy(proxy) }
			.also { if(authenticator != null) it.authenticator(authenticator) }
			.build()
	}
	
	private fun String.withQuery(query: Map<String, List<String>>): String {
		val querySnippet = query.joinToStringOrEmpty("&", "?") { (name, values) ->
			values.joinToString("&") { value -> "$name=${value.urlEncoded()}" }
		}
		return "$this$querySnippet"
	}
	
	private fun String.urlEncoded(): String {
		return URLEncoder.encode(this, "UTF-8")
	}
}

