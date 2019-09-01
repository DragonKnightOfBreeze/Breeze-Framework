package com.windea.breezeframework.time.extensions

import java.time.*

/**得到距今当前时长之前的本地日期时间。*/
inline val Duration.ago: LocalDateTime get() = LocalDateTime.now() - this

/**得到距今当前时长之后的本地日期时间。*/
inline val Duration.after: LocalDateTime get() = LocalDateTime.now() + this

/**得到距今当前时长之前的本地日期。*/
inline val Period.ago: LocalDate get() = LocalDate.now() - this

/**得到距今当前时长之后的本地日期。*/
inline val Period.after: LocalDate get() = LocalDate.now() + this
