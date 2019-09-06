package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.internal.*
import java.io.*
import java.net.*

/**不包含扩展名的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

/**得到文件的MIME类型。*/
val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(this.name)

/**得到文件的真实MIME类型。*/
@Reference("<https://zhidao.baidu.com/question/2078337860385272108.html>")
@Reference("<https://www.oschina.net/question/112255_44552>")
@Reference("<https://www.iteye.com/blog/chinacheng-841270>")
@Reference("<https://www.cnblogs.com/chenglc/p/7117847.html>")
@TrickImplementationApi("Performance and accuracy problem.")
val File.actualMimeType: String?
	get() = URLConnection.guessContentTypeFromStream(this.inputStream())


/**更改当前文件的上一级目录，返回新的文件对象。*/
fun File.changeParent(newParent: String): File {
	return File("$newParent\\${this.name}")
}

/**更改当前文件的名字，返回新的文件对象。*/
fun File.changeName(newName: String): File {
	return File("${this.path}\\$newName")
}

/**更改当前文件的不包含扩展名在内的名字，返回新的文件对象。*/
fun File.changeShotName(newShotName: String): File {
	return File("${this.parent}\\$newShotName.${this.extension}")
}

/**更改当前文件的扩展名，返回新的文件对象。*/
fun File.changeExtension(newExtension: String): File {
	return File("${this.parent}\\${this.shotName}.$newExtension")
}
