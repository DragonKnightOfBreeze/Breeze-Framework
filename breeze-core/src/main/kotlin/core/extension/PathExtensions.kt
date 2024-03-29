// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("PathExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import java.net.*
import java.nio.file.*

/**文件扩展名。*/
val Path.fileExtension: String get() = this.fileName.toString().substringAfterLast('.', "")

/**除去扩展名后的文件名。*/
val Path.fileShortName: String get() = this.fileName.toString().substringBeforeLast(".")


/**判断对应文件是否存在。*/
fun Path.exists(): Boolean = Files.exists(this)


/**将当前地址转化为统一资源定位符。*/
inline fun Path.toUrl(): URL = this.toUri().toURL()
