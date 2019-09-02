package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为未测试/未通过测试的项。*/
@MustBeDocumented
@Inherited
annotation class NotTested(
	val message: String = "Not tested."
)
