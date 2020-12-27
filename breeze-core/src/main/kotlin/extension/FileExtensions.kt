// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("FileExtensions")

package com.windea.breezeframework.core.extension

import java.io.*
import java.net.*

/**不包含扩展名的文件名。*/
val File.shortName: String get() = this.nameWithoutExtension

//https://zhidao.baidu.com/question/2078337860385272108.html
//https://www.oschina.net/question/112255_44552
//https://www.iteye.com/blog/chinacheng-841270
//https://www.cnblogs.com/chenglc/p/7117847.html

/**得到文件的MIME类型。*/
val File.mimeType: String?
	get() = URLConnection.guessContentTypeFromName(this.name)

/**得到文件的真实MIME类型。不保证准确性。*/
val File.actualMimeType: String?
	get() = URLConnection.guessContentTypeFromStream(this.inputStream())


/**创建当前文件以及所有的父目录。*/
fun File.createFile(): Boolean {
	this.parentFile.mkdirs()
	return this.createNewFile()
}

/**创建当前目录以及所有的父目录。*/
fun File.createDirectory(): Boolean {
	return this.mkdirs()
}


/**将当前文件转化为统一资源标识符。*/
fun File.toUri(): URI = this.toURI()

/**将当前文件转化为统一资源定位符。*/
fun File.toUrl(): URL = this.toURI().toURL()
