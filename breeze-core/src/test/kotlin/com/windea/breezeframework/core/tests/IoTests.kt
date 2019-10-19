package com.windea.breezeframework.core.tests

import com.windea.breezeframework.core.extensions.*
import org.junit.*

class IoTests {
	@Test
	fun test1() {
		val location = "/test.json" //相对于classpath
		
		val url = javaClass.getResource(location)
		// file:/../.. & url encoded
		println(url)
		
		val uri = url.toUri().also { println(it) }
		// file:/../.. & url encoded
		println(uri)
		
		val file = url.toFile()
		// ..\..
		println(file)
		
		val filePath = file.path.toPath()
		println(filePath)
		
		val path = url.toPath()
		// ..\..
		println(path)
		
		val externalForm = url.toExternalForm()
		//same to origin
		println(externalForm)
	}
}
