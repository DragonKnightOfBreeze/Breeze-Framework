package com.windea.utility.common.annotations.messages

import java.lang.annotation.*


/**本地化名字的注解。*/
@MustBeDocumented
@Repeatable
@Inherited
annotation class Name(
	val text: String,
	val locale: String = "Chs"
)
