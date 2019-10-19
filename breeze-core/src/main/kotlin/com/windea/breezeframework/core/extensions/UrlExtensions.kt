@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.text.*
import java.io.*
import java.net.*
import java.nio.file.*

/**是否存在查询参数。*/
val URL.hasQueryParam: Boolean get() = this.query.isNotBlank()

/**查询参数映射。*/
val URL.queryParamMap: QueryParamMap get() = this.query.toQueryParamMap()

//REGION Convert extensions

/**将当前统一资源定位符转化为文件。*/
inline fun URL.toFile(): File = File(this.toURI())

/**将当前统一资源定位符转化为路径。*/
inline fun URL.toPath(): Path = this.toFile().toPath()

/**将当前地址转化为统一资源标识符。*/
inline fun URL.toUri(): URI = this.toURI()
