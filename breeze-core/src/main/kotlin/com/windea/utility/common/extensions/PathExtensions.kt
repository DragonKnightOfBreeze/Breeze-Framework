package com.windea.utility.common.extensions

import java.nio.file.*

/**文件扩展名。*/
val Path.fileExtension: String get() = this.fileName.toString().substringAfterLast('.', "")

/**除去扩展名后的文件名。*/
val Path.fileShotName: String get() = this.fileName.toString().substringBeforeLast(".")
