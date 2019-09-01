package com.windea.breezeframework.core.extensions

import java.nio.file.*

/**文件扩展名。*/
val Path.fileExtension: String get() = this.fileName.toString().substringAfterLast('.', "")

/**除去扩展名后的文件名。*/
val Path.fileShotName: String get() = this.fileName.toString().substringBeforeLast(".")


/**判断对应文件是否存在。*/
fun Path.exists(): Boolean = Files.exists(this)

/**递归删除对应目录下的所有内容。*/
fun Path.deleteRecursively(): Boolean = this.toFile().deleteRecursively()
