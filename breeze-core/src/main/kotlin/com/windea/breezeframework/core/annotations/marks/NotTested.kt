package com.windea.breezeframework.core.annotations.marks

/**标注为未测试/未通过测试的项。*/
@MustBeDocumented
annotation class NotTested(
	val message: String = "Not tested."
)
