package com.windea.breezeframework.http

import java.net.http.*
import java.net.http.HttpResponse.*
import java.util.concurrent.*

private val stringBodyHandler = BodyHandlers.ofString()

fun HttpRequest.send(http: Http): HttpResponse<String> {
	return http.client.send(this, stringBodyHandler)
}

fun <T> HttpRequest.send(http: Http, bodyHandler: BodyHandler<T>): HttpResponse<T>? {
	return http.client.send(this, bodyHandler)
}

fun <T> HttpRequest.sendAsync(http: Http): CompletableFuture<HttpResponse<String>> {
	return http.client.sendAsync(this, stringBodyHandler)
}

fun <T> HttpRequest.sendAsync(http: Http, bodyHandler: BodyHandler<T>): CompletableFuture<HttpResponse<T>> {
	return http.client.sendAsync(this, bodyHandler)
}

fun <T> HttpRequest.sendAsync(http: Http, bodyHandler: BodyHandler<T>, pushPromiseHandler: PushPromiseHandler<T>): CompletableFuture<HttpResponse<T>> {
	return http.client.sendAsync(this, bodyHandler, pushPromiseHandler)
}
