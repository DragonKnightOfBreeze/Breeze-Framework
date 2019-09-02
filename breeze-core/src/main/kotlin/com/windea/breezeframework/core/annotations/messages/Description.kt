package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.domain.*
import org.intellij.lang.annotations.*
import org.jetbrains.annotations.*
import java.lang.annotation.*

/**本地化描述。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Description(
	@Language("Markdown")
	@Nls
	val text: String,
	val locale: String = LocaleType.SimpleChinese
)



