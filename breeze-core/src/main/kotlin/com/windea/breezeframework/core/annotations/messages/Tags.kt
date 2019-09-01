package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.intellij.lang.annotations.*
import java.lang.annotation.*

/**本地化概述的注解。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Tags(
	@Language("Markdown")
	val text: Array<String>,
	val locale: String = LocaleType.SimpleChinese
)
