// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("FileExtensions")

package com.windea.breezeframework.core.extensions

import java.io.*
import java.net.*

/**不包含扩展名的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

//https://zhidao.baidu.com/question/2078337860385272108.html
//https://www.oschina.net/question/112255_44552
//https://www.iteye.com/blog/chinacheng-841270
//https://www.cnblogs.com/chenglc/p/7117847.html

/**得到文件的MIME类型。*/
val File.mimeType:String?
	get() = URLConnection.guessContentTypeFromName(this.name)

/**得到文件的真实MIME类型。不保证准确性。*/
val File.actualMimeType:String?
	get() = URLConnection.guessContentTypeFromStream(this.inputStream())


/**创建当前文件以及所有的父目录。*/
fun File.createFile():Boolean {
	this.parentFile.mkdirs()
	return this.createNewFile()
}

/**创建当前目录以及所有的父目录。*/
fun File.createDirectory():Boolean {
	return this.mkdirs()
}


/**更改当前文件的上一级目录，返回新的文件对象。*/
@Deprecated("Use File.resolveSibling().",ReplaceWith("this.resolveSibling(newParent)"))
fun File.changeParent(newParent:String):File {
	return File("$newParent\\${this.name}")
}

/**更改当前文件的名字，返回新的文件对象。*/
@Deprecated("Use File.resolveSibling().",ReplaceWith("this.resolveSibling(newParent)"))
fun File.changeName(newName:String):File {
	return File("${this.parent}\\$newName")
}

/**更改当前文件的不包含扩展名在内的名字，返回新的文件对象。*/
@Deprecated("Use File.resolveSibling().",ReplaceWith("this.resolveSibling(newParent)"))
fun File.changeShotName(newShotName: String): File {
	return File("${this.parent}\\$newShotName.${this.extension}")
}

/**更改当前文件的扩展名，返回新的文件对象。*/
@Deprecated("Use File.resolveSibling().",ReplaceWith("this.resolveSibling(newParent)"))
fun File.changeExtension(newExtension: String): File {
	return File("${this.parent}\\${this.shotName}.$newExtension")
}


/**将当前文件转化为统一资源标识符。*/
fun File.toUri(): URI = this.toURI()

/**将当前文件转化为统一资源定位符。*/
fun File.toUrl(): URL = this.toURI().toURL()
