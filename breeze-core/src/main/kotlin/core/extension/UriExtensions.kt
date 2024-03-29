// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("UriExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import java.io.*
import java.net.*
import java.nio.file.*

//uri是编码后的url，当转化时可能需要进行适当的编码和解码

/**包括主机名在内的完整路径。*/
val URI.fullPath get() = "$host$path"

/**是否存在查询参数。*/
val URI.hasQueryParams: Boolean get() = this.query != null

/**查询参数映射。*/
val URI.queryParams: Map<String, List<String>>? get() = this.query?.toQueryParams()


/**将当前同一资源标识符转化为文件。*/
inline fun URI.toFile(): File = File(this)

/**将当前统一资源标识符转化为路径。*/
inline fun URI.toPath(): Path = this.toFile().toPath()

/**将当前统一资源标识符转化为统一资源定位符。*/
inline fun URI.toUrl(): URL = this.toURL()
