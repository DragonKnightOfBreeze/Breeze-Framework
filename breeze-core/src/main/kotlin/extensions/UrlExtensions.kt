// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("UrlExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.collections.*
import java.io.*
import java.net.*
import java.nio.file.*

/**包括主机名在内的完整路径。*/
val URL.fullPath get() = "$host$path"

/**是否存在查询参数。*/
val URL.hasQueryParams: Boolean get() = this.query != null

/**查询参数映射。*/
val URL.queryParams:Map<String, List<String>>? get() = this.query?.split("&")?.groupBy({ it.substringBefore("=") }, { it.substringAfter("=") })


/**将当前统一资源定位符转化为文件。*/
inline fun URL.toFile(): File = File(this.toURI().normalize()) //需要规范化

/**将当前统一资源定位符转化为路径。*/
inline fun URL.toPath(): Path = this.toFile().toPath()

/**将当前地址转化为统一资源标识符。*/
inline fun URL.toUri(): URI = this.toURI()
