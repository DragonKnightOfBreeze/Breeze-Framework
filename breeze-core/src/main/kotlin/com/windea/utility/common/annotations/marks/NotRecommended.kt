package com.windea.utility.common.annotations.marks

import java.lang.annotation.*

/**不推荐的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotRecommended(
	val message: String = "Not recommended."
)
