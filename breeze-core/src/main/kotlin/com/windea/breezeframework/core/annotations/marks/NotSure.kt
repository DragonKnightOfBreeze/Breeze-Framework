package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为未确定的项。*/
@MustBeDocumented
@Inherited
annotation class NotSure(
	val message: String = "Not sure."
)
