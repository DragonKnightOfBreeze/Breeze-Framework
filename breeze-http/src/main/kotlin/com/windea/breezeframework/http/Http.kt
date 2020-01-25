package com.windea.breezeframework.http

import java.net.*
import java.net.http.*

/**
 * Http连接。基于java11的[HttpClient]。
 *
 * @see java.net.http.HttpClient
 * @see java.net.http.HttpRequest
 * @see java.net.http.HttpResponse
 */
class Http {
	private var config: HttpConfig
	private var client: HttpClient

	constructor() {
		this.config = HttpConfig()
		this.client = HttpClient.newHttpClient()
	}

	constructor(configBlock: HttpConfig.() -> Unit) {
		this.config = HttpConfig().also(configBlock)
		this.client = buildHttpClient()
	}

	fun copy(): Http {
		return Http().also {
			it.config = config
			it.client = client
		}
	}

	private fun buildHttpClient(): HttpClient {
		val (cookieHandler, connectTimeout, sslContext, sslParameters, executor, followRedirects, version, priority, proxy, authenticator) = config
		return HttpClient.newBuilder()
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
		//allow query in url
		val delimiter = if("?" in this) "&" else "?"
		val querySnippet = if(query.isEmpty()) "" else query.entries.joinToString("&", delimiter) { (name, values) ->
			values.joinToString("&") { value -> "$name=${value.urlEncoded()}" }
		}
		return "$this$querySnippet"
	}

	private fun String.urlEncoded(): String {
		return URLEncoder.encode(this, "UTF-8")
	}

	fun get(url: String): HttpResponse<String> {
		return request("GET", url, null, null)
	}

	fun get(url: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("GET", url, null, HttpRequestConfig().also(configBlock))
	}

	fun post(url: String, body: String): HttpResponse<String> {
		return request("POST", url, body, null)
	}

	fun post(url: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("POST", url, null, HttpRequestConfig().also(configBlock))
	}

	fun put(url: String, body: String): HttpResponse<String> {
		return request("PUT", url, body, null)
	}

	fun put(url: String, body: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("PUT", url, body, HttpRequestConfig().also(configBlock))
	}

	fun delete(url: String): HttpResponse<String> {
		return request("DELETE", url, null, null)
	}

	fun delete(url: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("DELETE", url, null, HttpRequestConfig().also(configBlock))
	}

	fun head(url: String, body: String): HttpResponse<String> {
		return request("HEAD", url, body, null)
	}

	fun head(url: String, body: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("HEAD", url, body, HttpRequestConfig().also(configBlock))
	}

	fun batch(url: String, body: String): HttpResponse<String> {
		return request("BATCH", url, body, null)
	}

	fun batch(url: String, body: String, configBlock: HttpRequestConfig.() -> Unit): HttpResponse<String> {
		return request("BATCH", url, body, HttpRequestConfig().also(configBlock))
	}

	private fun request(method: String, url: String, body: String?, config: HttpRequestConfig?): HttpResponse<String> {
		val bodyPublisher = if(body != null) HttpRequest.BodyPublishers.ofString(body) else null
		val bodyHandler = HttpResponse.BodyHandlers.ofString()
		return buildResponse(method, url, bodyPublisher, bodyHandler, config)
	}

	private fun buildResponse(method: String, url: String, bodyPublisher: HttpRequest.BodyPublisher?, bodyHandler: HttpResponse.BodyHandler<String>?, config: HttpRequestConfig?): HttpResponse<String> {
		val httpRequest = if(config == null) {
			val uri = URI.create(url)
			HttpRequest.newBuilder(uri)
				.buildMethodRequest(method, bodyPublisher)
				.build()
		} else {
			val (headers, query, timeout, version, expectContinue) = config
			val uri = URI.create(url.withQuery(query))
			HttpRequest.newBuilder(uri)
				.buildMethodRequest(method, bodyPublisher)
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
		}
		return client.send(httpRequest, bodyHandler)
	}

	private fun HttpRequest.Builder.buildMethodRequest(method: String, bodyPublisher: HttpRequest.BodyPublisher?): HttpRequest.Builder {
		return this.let {
			when(method) {
				"GET" -> it.GET()
				"POST" -> it.POST(bodyPublisher)
				"PUT" -> it.PUT(bodyPublisher)
				"DELETE" -> it.DELETE()
				"HEADER", "BATCH" -> it.method(method, bodyPublisher)
				else -> throw IllegalArgumentException("Http request method is invalid. Current: '$method'.")
			}
		}
	}
}
