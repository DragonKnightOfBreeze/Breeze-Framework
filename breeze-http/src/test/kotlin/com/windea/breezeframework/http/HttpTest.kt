package com.windea.breezeframework.http

import java.net.*
import java.net.http.*
import kotlin.test.*

val http = Http()

class HttpTest {
	@Test
	fun test1() {
		val response = http.get("https://httpbin.org/")
		println(response)
		
		val response2 = http.get("https://httpbin.org") {
			query("name", "Windea")
			query("weapon", "BreezesLanding")
			query("weaponObject", """{"name":"BreezesLanding",category:"Katana"}""")
		}
		println(response2)
	}
	
	@Test
	fun test2() {
		val client = HttpClient.newHttpClient()
		val request = HttpRequest.newBuilder(URI.create("https://httpbin.org/")).GET().build()
		val response = client.send(request, HttpResponse.BodyHandlers.ofString())
		
		println(response.statusCode())
		println(response.body())
	}
}
