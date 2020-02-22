package com.windea.breezeframework.http

import java.net.*
import java.net.http.*
import java.nio.charset.*

/**
 * Http连接。基于java11的[HttpClient]。
 *
 * @see java.net.http.HttpClient
 * @see java.net.http.HttpRequest
 * @see java.net.http.HttpResponse
 */
class Http(
	val configuration: HttpConfiguration = HttpConfiguration()
) {
	constructor(block: HttpConfiguration.() -> Unit) : this(HttpConfiguration().apply(block))

	internal val client: HttpClient = buildClient()

	private fun buildClient(): HttpClient {
		return HttpClient.newBuilder().also { builder ->
			configuration.cookieHandler?.let { builder.cookieHandler(it) }
			configuration.connectTimeout?.let { builder.connectTimeout(it) }
			configuration.sslContext?.let { builder.sslContext(it) }
			configuration.sslParameters?.let { builder.sslParameters(it) }
			configuration.executor?.let { builder.executor(it) }
			configuration.followRedirects?.let { builder.followRedirects(it) }
			configuration.version?.let { builder.version(it) }
			configuration.priority?.let { builder.priority(it) }
			configuration.proxy?.let { builder.proxy(it) }
			configuration.authenticator?.let { builder.authenticator(it) }
		}.build()
	}

	/**复制Http连接。*/
	fun copy(): Http {
		return Http(configuration)
	}


	/**发送GET请求。*/
	fun get(url: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("GET", url, null, configuration)
	}

	/**发送GET请求，附带额外配置。*/
	fun get(url: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("GET", url, null, HttpRequestConfiguration().apply(configurationBlock))
	}

	/**发送POST请求。*/
	fun post(url: String, body: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("POST", url, body, configuration)
	}

	/**发送POST请求，附带额外配置。*/
	fun post(url: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("POST", url, null, HttpRequestConfiguration().apply(configurationBlock))
	}

	/**发送PUT请求。*/
	fun put(url: String, body: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("PUT", url, body, configuration)
	}

	/**发送PUT请求，附带额外配置。*/
	fun put(url: String, body: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("PUT", url, body, HttpRequestConfiguration().apply(configurationBlock))
	}

	/**发送DELETE请求。*/
	fun delete(url: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("DELETE", url, null, configuration)
	}

	/**发送DELETE请求，附带额外参数。*/
	fun delete(url: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("DELETE", url, null, HttpRequestConfiguration().apply(configurationBlock))
	}

	/**发送HEAD请求。*/
	fun head(url: String, body: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("HEAD", url, body, configuration)
	}

	/**发送HEAD请求，附带额外参数。*/
	fun head(url: String, body: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("HEAD", url, body, HttpRequestConfiguration().apply(configurationBlock))
	}

	/**发送BATCH请求。*/
	fun batch(url: String, body: String, configuration: HttpRequestConfiguration? = null): HttpRequest {
		return request("BATCH", url, body, configuration)
	}

	/**发送BATCH请求，附带额外参数。*/
	fun batch(url: String, body: String, configurationBlock: HttpRequestConfiguration.() -> Unit): HttpRequest {
		return request("BATCH", url, body, HttpRequestConfiguration().apply(configurationBlock))
	}

	private fun request(method: String, url: String, body: String?, configuration: HttpRequestConfiguration?): HttpRequest {
		val charset = Charset.forName(configuration?.encoding ?: "UTF-8")
		val bodyPublisher = if(body != null) HttpRequest.BodyPublishers.ofString(body, charset) else null
		return buildRequest(method, url, bodyPublisher, configuration)
	}

	private fun buildRequest(method: String, url: String, bodyPublisher: HttpRequest.BodyPublisher?,
		configuration: HttpRequestConfiguration?): HttpRequest {
		return if(configuration == null) {
			val uri = URI.create(url)
			HttpRequest.newBuilder(uri).buildMethodRequest(method, bodyPublisher).build()
		} else {
			val uri = URI.create(url.withQuery(configuration.query))
			HttpRequest.newBuilder(uri).buildMethodRequest(method, bodyPublisher).also { builder ->
				configuration.headers.forEach { (name, values) ->
					values.forEach { value ->
						builder.header(name, value)
					}
				}
				configuration.timeout?.let { builder.timeout(it) }
				configuration.expectContinue?.let { builder.expectContinue(it) }
				configuration.version?.let { builder.version(it) }
			}.build()
		}
	}

	private fun HttpRequest.Builder.buildMethodRequest(method: String, bodyPublisher: HttpRequest.BodyPublisher?): HttpRequest.Builder {
		return when(method) {
			"GET" -> this.GET()
			"POST" -> this.POST(bodyPublisher)
			"PUT" -> this.PUT(bodyPublisher)
			"DELETE" -> this.DELETE()
			"HEADER", "BATCH" -> this.method(method, bodyPublisher)
			else -> throw IllegalArgumentException("Http request method is invalid. Current: '$method'.")
		}
	}

	private fun String.withQuery(query: Map<String, List<String>>): String {
		val delimiter = if("?" in this) "&" else "?"
		val querySnippet = if(query.isEmpty()) "" else query.entries.joinToString("&", delimiter) { (name, values) ->
			values.joinToString("&") { value -> "$name=${value.urlEncoded()}" }
		}
		return "$this$querySnippet"
	}

	private fun String.urlEncoded(): String {
		return URLEncoder.encode(this, configuration.encoding)
	}
}
