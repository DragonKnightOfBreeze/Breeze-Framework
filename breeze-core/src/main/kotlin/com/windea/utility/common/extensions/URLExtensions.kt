package com.windea.utility.common.extensions

import com.windea.utility.common.domain.text.*
import java.net.*

/**是否存在查询参数。*/
val URL.hasQueryParam: Boolean get() = this.query.isNotBlank()

/**查询参数映射。*/
val URL.queryParamMap: QueryParamMap get() = this.query.toQueryParamMap()
