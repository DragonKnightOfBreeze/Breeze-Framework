package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import java.io.*
import java.net.*

/**不包含扩展名的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

/**得到文件的MIME类型。*/
val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(this.name)

//https://zhidao.baidu.com/question/2078337860385272108.html
//https://www.oschina.net/question/112255_44552
//https://www.iteye.com/blog/chinacheng-841270
//https://www.cnblogs.com/chenglc/p/7117847.html
/**得到文件的真实MIME类型。*/
@TrickImplementationApi("Performance and accuracy problem.")
val File.actualMimeType: String?
	get() = URLConnection.guessContentTypeFromStream(this.inputStream())


/**更改当前文件的上一级目录，返回新的文件对象。*/
fun File.changeParent(newParent: String): File {
	return File("$newParent\\${this.name}")
}

/**更改当前文件的名字，返回新的文件对象。*/
fun File.changeName(newName: String): File {
	return File("${this.parent}\\$newName")
}

/**更改当前文件的不包含扩展名在内的名字，返回新的文件对象。*/
fun File.changeShotName(newShotName: String): File {
	return File("${this.parent}\\$newShotName.${this.extension}")
}

/**更改当前文件的扩展名，返回新的文件对象。*/
fun File.changeExtension(newExtension: String): File {
	return File("${this.parent}\\${this.shotName}.$newExtension")
}

//REGION Convert extensions

/**将当前文件转化为统一资源标识符。*/
fun File.toUri(): URI = this.toURI()

/**将当前文件转化为统一资源定位符。*/
fun File.toUrl(): URL = this.toURI().toURL()
