package com.windea.utility.common.annotations.messages

import org.intellij.lang.annotations.*
import java.lang.annotation.*

/**本地化描述的注解。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Description(
	@Language("Markdown")
	val text: String,
	val locale: String = "Chs"
)



