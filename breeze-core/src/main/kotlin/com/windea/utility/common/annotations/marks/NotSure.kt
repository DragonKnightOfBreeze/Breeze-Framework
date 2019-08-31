package com.windea.utility.common.annotations.marks

import java.lang.annotation.*

/**未确定的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotSure(
	val message: String = "Not sure."
)
