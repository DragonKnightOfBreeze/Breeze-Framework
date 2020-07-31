/***********************************************************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 *
 *                                     ...]]]]]]..
 *                             ...,]OOOOOOOOOOOOOOO].
 *                            ./]/[[[[\OOOOOOOOOO@@@@O].
 *                                        .OOOOOO@@@@@@@@].
 *                                         =OOOOO@@@@@@@@@@@`.
 *                                          =@@OO@@@@@@@@@@@@@\.
 *                                          .\@@@@@@@@@@@@@@@@@@\.
 *                                   .. .    .O@@@@@@@@@@@@@@@@@@@@`.
 *                                    .``=\.  ,@@@@@@@^=@^O@@@@@@@@@@\.              .`  ..,]]]]]].
 *                      ... .....       .=OO` .O@@@@@^=^..O@@@@@@@@@@@.           .,@@]@@@@@@@@@@@.
 *               ........*[]],\OOOOO\]..  .\O^ O@@@@^O@`..@@@@@@@@@@@@@].      ....OO@@@@@@@@@@@@^
 *          ...*.......**[oOOOOOOOOO@@@@@`. =@`=@@@`=@@`.=@@@@@@@@@@@@@@\......./^.,@@@@@@@@@@@@^
 *                .........[\OOO@@@@@@@@@@@`]O@OO@`.=@@.`=@@@@@@@@@@@@@@@@^...,@`.=@@@@@@@@@@@@`
 *                            ......[[\OO@@@O\O@@`...@@@.@@@@@@@@O@@@@@@@@\..//..O@@@@@@O]`.\/.
 *                    ..,]/OOOO@@@@@@@@@@@^,`,`O@....@@\=@@OO@@@@@@@@@@@@@@]@^./@@@@@@@/`..OO@`
 *                 ..*...,]OO@@@@@@@@@@@O`.,,/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/O@@@@@@@@@@@@@@@@@.
 *               ....*[*=o/\OO@@@@@@@@@O@@@@@@@@@@@`.=[\O@@@@@@@@@@@@@@@@@@@@@@@@@@@@O[=@@@@@@@O.
 *             ...*....*]/OOO@@@@@[.,/@@@@@@@@@@@@^...........[OOO@@@@@@@@@@@@@@@/.    .O@@@@@@@^
 *           ........**`,OO/oO/. ,/@@@@@@@@@@@@O`***..........**......*....*.=O[.        .=@@@@/\^
 *           *..   ,`.,`**,.. .,@@@@@@@@@@@@@@@^.**.**.....*`...*...../`.........         .\/[..=^.
 *           .    ..*\*. .. ./O@@@@@@@@@@@@@@@O^/....*....=@@@[[^*...=@OO..*... ..        =@@...@@^
 * \.            .*,.     .,O@@@@@@@@@@@@@@@O@O/^*...*.....\]*....**./=.o`*^.... ..      ,@@@@\=@@@`
 *  .`.          ..      ,OOO@@@@@@@OOO/[[[[..*........................**==.* ..         /@@@@@@@@@@.
 *    .,.               ,/`..            ... .........*................*.**.. ..       ./@@@@@@@@@@@.
 *       ,`            ./.               ..  .........*...=*`......../O...... ..     .O@@@@@@@@@@@@^
 *         ,..    ..   =.                ..   ...`...**...,**,**..][=@@`*=@@@@]]]\.]@@@@@@@@@@@@@@@^
 *    ..     .*.  ..   =.                ...,/@@@@@@@O^`..=*****.,\\*./ooO@@@@@@@@@@@@@@@@@@@@@/[...
 *   ..        .\.......`.                 .,O@@@@@@@@\O`.*****...*^`*O\OO@@@@@@@@@@@@@@@@@@@@/.
 *  ...    ..... .\.....\^..                 ./@@@@@@@O@@**......`...`=@O@@@@@@@@@@@@@@@@@@@`
 *   ...  ..       ....  ....          ....*./@@@@@@@@@@@O\.....*.....*@@@@@@@@@@@@@@@@@@@/.
 *   .... ..        ...`  ................*.=@@@@@@@@@@@@@@@\...`.....**@@@@@@@@@@@@@@@@@@@@`
 *    .......         ..,`.        ....*....[[O@@@@@@@@@@@@@@@\@@@\*.....\@@@@@@@@@@@@@@@@@@@@\.
 *     .......          ..,................./@/=@@@@@@@@@@@@@@@@@@@@@]...*\@@@@@@@@@@@@@@@@@@@@@\.
 *       ........           .*.   .........=@^*\/=@@@@@@@@@@@@@@@@@@@@@@\./@@@@@@@@@@@@@@@@@@@@@@@\.
 *      .. ........           .\.....**]/]/\]o\]]@@@@@@@@@@@@@@@@@@OOOO@@@@@@^/@@@@@@@@@@@@@@@@@@@@@`                 ..
 *       ...............    ......,/@@@@O@@Oo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=@@@O*[@@@@@@@@@@@@@@@@@@@@.               =O`
 *            .......*..........  .[@@O@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O@\..\@@@@@@@@@@@@@@@@@@@@.              /\]
 *   =`                    ,]`    ,/@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@o@@OO\.,@@@@@@@@@@@@@@@@@@@@\.          ./O@O.
 *   =.       .O`        ,@@@@\.,@@@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@o@@OO@O.=@@@@@@@@@@@@@@@@@@@@\.        ,O@@^=.
 * . .\.   =OO@.O.     ,@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O/O@OOOOO`\@@@@@@@@@@@@@@@@@@@@O.     ./@@/=.=.
 * `  .,`. .@,\.O ....OOO@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O=..[\OOOOO`O@@@^ .,\@@@@@O@@@@@/\\]`./@@`. =^^
 * \`    ,\\/@@@`  ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/.=^...\oOOOO\@@@^          ./@@[`=@OO@@/.   .O.
 *  .\].     ./@@]]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`.......***OOOO@@@^        .,@@`  =/@@@/.    .OO.
 *      .[[[[`..\@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`..........**=OO@O@@`        /@@\]]/@@@@^O..  .OOO^
 *              .,O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\*..........*,/@@@O@^]].    ./@@@@@OO@@@@o@@`  =OOO^
 *               .,O@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@Oo*......,O@@@OO^.\@@@].,@@@@@@@O@@@\/O=`  .=OO`.
 * .......`.   .\@@@@@@@@@@@@@@@@@@@@@@@@@/` =@@@@@@@@@@@@@@@@@@@@@@@@@@@@OO\]/@@@OOOO@@@@@@@@@\@@@@@@@@@@@`.    ,OO`.
 *   ,O`. ,O\].. ,@@@@@@@@@@@@@@@@@@@@@/.    ./@@@@@@@@@@@@@\@@OO@***O@@`[\@@O@@@@@@@O/`*@@@@`/@@@@@@@@@@^=O.  ..=O.
 *     .\OOO@O@@@O]`\@@@@@@@@@@@@@@@O\O\]..  .@@@@@@@@@@@@@@\OOo`OO@@@`..,O@@@@//\..[*....O@@@@@@@@@@@@@^./@.   ...
 *           ..[[O@@@@@@\]`. .....,@@@\/@@@@O/@@@@@@@@@.,@@\O@@\=O@@[.../@@@@[.=^.\.......==@@@@@@@@@^.  ,@O.
 *    .,]OO]`..     ..[O@@@@@@@O].. ./@@@@@@@@@@@@@@@@@^O@@@@OOOOoO`*,O@@@O`...=\..,\...,/./@@@@@@@@/.  .@`
 *  .O@@@/\O`...  ..,`.    .,\@@@@@@@@@@OOO@@@@@@@@@@@@\@O@@@@@OoOO@@@@@O......@@`..O..../@@@@@@@@O^.  ,O.
 *  .     .....,@@@O`            .[@@@@@@OOO@@@@@@@@@@@@\@@@@@@@@@@@@/[*......=@@@`...]@@@@O@@@@@/.  .=/.
 *          ..=@@@\.               .O@@OoooO@@@@@@@@@@@@@@@@OO\@OO**..........=@@@@@@@@@@`./@@@@\.  ./^
 *            .*O@@@\.              ....OOO@@@@@@@@@@@@@@@@@OO@@O@\]]]]]]]/@@@@@@@@@/[..../^,@@O\^ =@`
 *            ... .[\O^.          ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@/,\@@@@@@@@@@@@@@]....../O`......,@O.
 *            ....                      ...\@@@@@@@@@@@@@@@@@@@`......[[`.@@@@@@@@/\OO[`......../@^
 *             ...                           .[\O^,@@@@@@@O@@@@..........=@@@@@@@@@`.........=@@@@].
 *             ...                            ...,O@OO@@@OOOO@@..........=@@@@@@@@@@^......,@@@@@@@^
 *             ..*.                           .,/OOO@@@@@OOOO@@`.........@@@@@@@@@@@@@@@@@@@@@@@@@@@\`..
 *             .....                          .OOOO@@@@OO@@@@@@@\......,@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@O\]..
 *              ......                      ./OOOOOOO@@OOO@@@@@@@@@@@@@@@@@@@^.    ./@@@@@@@@@@@@@@@@@@@@@@@@@@O]`..
 *               ......                  .]OOOOOO[./@OOOOO@@@@@@@@@@@@@@@@@/.  .,/@@/`O@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@\]
 *               .........               .......*.,OOOOOO@@@@@@@@@@@@@@@@@@^.]@@@/.   .@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                ...........                 .../OOOOO//@@@@@@@@@@@@@@@@O@@@/`.       =@O@@@@@@@@@@@@@...[\@@@@@@@@@@@O
 *                 .........................*`.]OOOO/`.=@@@@@@@@@@@@@@@@@/\O.          .O/@@@@@@@@@@@O^         .[\O@@@@
 *                   .....................*.,[[[......=@@@`.\@@@@@@@@@@@@^.^            ,^,@@@@@@@@@@.                ..
 *                    .................*.............=@@@`./@@@@@@@@@@@@^ ..             .. \@@@@@@@@^`.         ...*[`.
 *                     ............................*,@@@^/@@@@@@@@@@@@@@.                    =@@@@@@@@..\...*[..
 *                      ...........................=@@@O@@@@@@@@@@@@@@@^                     .@@@@@@@@^. .\.
 *                        ..........................\/@@@@@@@\@@@@@@@@O.                ....*[,@@@@@@@^    .*.
 *                          ........................O@@@@@@/..@@@@@@@@^         ...**[.       .@@@@@@@@.     .,`.
 *                            ..................../@@@@@@@@\..@@@@@@@@^ ...*,[..               =@@@@@@@.        ,`.
 *                              ................/@@@@@@@`@@@^ =@@@@@@@^                        .\@@@@@@^          ,*.
 *                                  ..........,O@@@@@@` ..[O[`=@@@@@@@^                         ,@@@@@@^            .\.
 *                                          ,OO@@@@@/`.       .@@@@@@@O.                         @@@@@@\.             .\
 *                                      ..,/O@@@@@/.          .O@@@@@@@.                         .@@@@@^
 *                             ....*[`. ./OOOO@@/.             =@@@@@@O.                          =@@@@@.
 *                     ....*[..       .,OOOOOOO`               .@@@@@@@.                          =@@@@@^
 *
 * Breeze is blowing ...
 **********************************************************************************************************************/

package com.windea.breezeframework.http

import java.net.*
import java.net.http.*
import java.nio.charset.*

/**
 * Http连接。基于java11的[HttpClient]。
 * @see java.net.http.HttpClient
 * @see java.net.http.HttpRequest
 * @see java.net.http.HttpResponse
 */
class Http(
	val config: HttpConfig = HttpConfig()
) {
	constructor(block: HttpConfig.Builder.() -> Unit) : this(HttpConfig.Builder().apply(block).build())

	internal val client: HttpClient = buildClient()

	private fun buildClient(): HttpClient {
		return HttpClient.newBuilder().also { builder ->
			config.cookieHandler?.let { builder.cookieHandler(it) }
			config.connectTimeout?.let { builder.connectTimeout(it) }
			config.sslContext?.let { builder.sslContext(it) }
			config.sslParameters?.let { builder.sslParameters(it) }
			config.executor?.let { builder.executor(it) }
			config.followRedirects?.let { builder.followRedirects(it) }
			config.version?.let { builder.version(it) }
			config.priority?.let { builder.priority(it) }
			config.proxy?.let { builder.proxy(it) }
			config.authenticator?.let { builder.authenticator(it) }
		}.build()
	}

	/**复制Http连接。*/
	fun copy(): Http {
		return Http(config)
	}


	/**发送GET请求。*/
	fun get(url: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("GET", url, null, config)
	}

	/**发送GET请求，附带额外配置。*/
	fun get(url: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("GET", url, null, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	/**发送POST请求。*/
	fun post(url: String, body: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("POST", url, body, config)
	}

	/**发送POST请求，附带额外配置。*/
	fun post(url: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("POST", url, null, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	/**发送PUT请求。*/
	fun put(url: String, body: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("PUT", url, body, config)
	}

	/**发送PUT请求，附带额外配置。*/
	fun put(url: String, body: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("PUT", url, body, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	/**发送DELETE请求。*/
	fun delete(url: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("DELETE", url, null, config)
	}

	/**发送DELETE请求，附带额外配置。*/
	fun delete(url: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("DELETE", url, null, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	/**发送HEAD请求。*/
	fun head(url: String, body: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("HEAD", url, body, config)
	}

	/**发送HEAD请求，附带额外配置。*/
	fun head(url: String, body: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("HEAD", url, body, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	/**发送BATCH请求。*/
	fun batch(url: String, body: String, config: HttpRequestConfig? = null): HttpRequest {
		return request("BATCH", url, body, config)
	}

	/**发送BATCH请求，附带额外配置。*/
	fun batch(url: String, body: String, configBlock: HttpRequestConfig.Builder.() -> Unit): HttpRequest {
		return request("BATCH", url, body, HttpRequestConfig.Builder().apply(configBlock).build())
	}

	private fun request(method: String, url: String, body: String?, config: HttpRequestConfig?): HttpRequest {
		val charset = Charset.forName(config?.encoding ?: "UTF-8")
		val bodyPublisher = if(body != null) HttpRequest.BodyPublishers.ofString(body, charset) else null
		return buildRequest(method, url, bodyPublisher, config)
	}

	private fun buildRequest(method: String, url: String, bodyPublisher: HttpRequest.BodyPublisher?,
		config: HttpRequestConfig?): HttpRequest {
		return if(config == null) {
			val uri = URI.create(url)
			HttpRequest.newBuilder(uri).buildMethodRequest(method, bodyPublisher).build()
		} else {
			val uri = URI.create(url.withQuery(config.query))
			HttpRequest.newBuilder(uri).buildMethodRequest(method, bodyPublisher).also { builder ->
				config.headers.forEach { (name, values) ->
					values.forEach { value ->
						builder.header(name, value)
					}
				}
				config.timeout?.let { builder.timeout(it) }
				config.expectContinue?.let { builder.expectContinue(it) }
				config.version?.let { builder.version(it) }
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
		return URLEncoder.encode(this, config.encoding)
	}
}
