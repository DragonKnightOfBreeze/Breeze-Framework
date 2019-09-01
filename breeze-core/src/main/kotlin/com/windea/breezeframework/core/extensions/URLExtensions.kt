package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.text.*
import java.net.*

/**是否存在查询参数。*/
val URL.hasQueryParam: Boolean get() = this.query.isNotBlank()

/**查询参数映射。*/
val URL.queryParamMap: QueryParamMap get() = this.query.toQueryParamMap()
