package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**未测试/未通过测试的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotTested(
	val message: String = "Not tested."
)
