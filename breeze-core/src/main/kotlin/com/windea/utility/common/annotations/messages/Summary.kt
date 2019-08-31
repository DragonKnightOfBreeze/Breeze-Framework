package com.windea.utility.common.annotations.messages

import org.intellij.lang.annotations.*
import java.lang.annotation.*

/**本地化概述的注解。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Summary(
	@Language("Markdown")
	val text: String,
	val locale: String = "Chs"
)
