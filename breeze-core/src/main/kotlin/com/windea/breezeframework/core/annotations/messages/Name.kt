package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import java.lang.annotation.*

/**本地化名字的注解。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Name(
	val text: String,
	val locale: String = LocaleType.SimpleChinese
)
