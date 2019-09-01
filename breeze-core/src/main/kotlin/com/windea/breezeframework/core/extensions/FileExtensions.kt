package com.windea.breezeframework.core.extensions

import java.io.*

/**不包含扩展名的文件名。*/
val File.shotName: String get() = this.nameWithoutExtension

/**判断是否是文本文件。*/
val File.isTextFile: Boolean get() = TODO()

/**判断是否是图片文件。*/
val File.isImageFile: Boolean get() = TODO()


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
