package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.intellij.lang.annotations.*
import org.jetbrains.annotations.*

/**本地化概述。*/
@MustBeDocumented
@Repeatable
annotation class Summary(
	@Language("Markdown")
	@Nls
	val text: String,
	val locale: String = LocaleType.SimpleChinese
)
