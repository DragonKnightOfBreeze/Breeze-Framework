package com.windea.breezeframework.core.domain.text

import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.net.*


/**地址信息。相比[URL]更加轻量，同时也能进行解构。*/
data class UrlInfo(
	/**完整地址。*/
	val url: String,
	/**不包含查询参数的完整路径。*/
	val fullPath: String,
	/**协议。默认为http。*/
	val protocol: String,
	/**主机。*/
	val host: String,
	/**端口。*/
	val port: String,
	/**路径*/
	val path: String,
	/**查询参数。*/
	val query: String
) : Serializable {
	/**是否存在查询参数。*/
	val hasQueryParam = query.isNotEmpty()
	
	/**查询参数映射。*/
	val queryParamMap = query.toQueryParamMap()
	
	override fun toString(): String {
		return url
	}
}
