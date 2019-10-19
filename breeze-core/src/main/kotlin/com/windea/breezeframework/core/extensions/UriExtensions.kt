@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*
import java.net.*
import java.nio.file.*

//REGION Convert extensions

/**将当前同一资源标识符转化为文件。*/
inline fun URI.toFile(): File = File(this)

/**将当前统一资源标识符转化为路径。*/
inline fun URI.toPath(): Path = this.toFile().toPath()

/**将当前统一资源标识符转化为统一资源定位符。*/
inline fun URI.toUrl(): URL = this.toURL()
