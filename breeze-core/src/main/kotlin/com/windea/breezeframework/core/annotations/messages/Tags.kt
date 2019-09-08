package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.jetbrains.annotations.*

/**本地化标签。*/
@MustBeDocumented
@Repeatable
annotation class Tags(
	@Nls
	val text: Array<String>,
	val locale: String = LocaleType.SimpleChinese
)
