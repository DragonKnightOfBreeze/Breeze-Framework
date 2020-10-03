// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TemporalAccessorExtensions")

package com.windea.breezeframework.time.extensions

import java.time.*
import java.time.temporal.*

//region temporal query extensions
inline val TemporalAccessor.year: Year? get() = query(Year::from)

inline val TemporalAccessor.yearMonth: YearMonth? get() = query(YearMonth::from)

inline val TemporalAccessor.month: Month? get() = query(Month::from)

inline val TemporalAccessor.monthDay: MonthDay? get() = query(MonthDay::from)

inline val TemporalAccessor.dayOfWeek: DayOfWeek? get() = query(DayOfWeek::from)

inline val TemporalAccessor.instant: Instant? get() = query(Instant::from)

inline val TemporalAccessor.localDate: LocalDate? get() = query(LocalDate::from)

inline val TemporalAccessor.localTime: LocalTime? get() = query(LocalTime::from)

inline val TemporalAccessor.localDateTime: LocalDateTime? get() = query(LocalDateTime::from)

inline val TemporalAccessor.zoneOffset: ZoneOffset? get() = query(ZoneOffset::from)

inline val TemporalAccessor.offsetTime: OffsetTime? get() = query(OffsetTime::from)

inline val TemporalAccessor.offsetDateTime: OffsetDateTime? get() = query(OffsetDateTime::from)

inline val TemporalAccessor.zone: ZoneId? get() = query(TemporalQueries.zone())

inline val TemporalAccessor.zoneId: ZoneId? get() = query(ZoneId::from)

inline val TemporalAccessor.zonedDateTime: ZonedDateTime? get() = query(ZonedDateTime::from)

inline val TemporalAccessor.precision: TemporalUnit? get() = query(TemporalQueries.precision())
//endregion

//region common extensions
/**判断是否支持指定的是时间域。*/
infix fun TemporalAccessor.supports(temporalField: TemporalField) = isSupported(temporalField)
//endregion

