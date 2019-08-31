package com.windea.utility.common.annotations.marks

import java.lang.annotation.*

/**不适用的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotSuitable(
	val message: String = "Not suitable."
)
