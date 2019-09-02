package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.jetbrains.annotations.*
import java.lang.annotation.*

/**本地化名字。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Name(
	@Nls
	val text: String,
	val locale: String = LocaleType.SimpleChinese
)
