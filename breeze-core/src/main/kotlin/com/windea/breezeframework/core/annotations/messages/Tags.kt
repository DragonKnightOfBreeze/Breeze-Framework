package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.jetbrains.annotations.*
import java.lang.annotation.*

/**本地化标签。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Tags(
	@Nls
	val text: Array<String>,
	val locale: String = LocaleType.SimpleChinese
)
