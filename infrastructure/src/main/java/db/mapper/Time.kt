package org.agh.eaiib.db.mapper

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.time.LocalDate
import java.time.LocalDateTime


inline fun LocalDate.toJodaDateTime() = DateTime(DateTimeZone.UTC).withDate(year, monthValue, dayOfMonth).withTime(0, 0, 0, 0)


inline fun LocalDateTime.toJodaDateTime() = DateTime(DateTimeZone.UTC).withDate(year, monthValue, dayOfMonth).withTime(hour, minute, second, 0)

inline fun DateTime.toJavaLocalDateTime() = LocalDateTime.of(this.year, monthOfYear, dayOfMonth, hourOfDay, minuteOfDay)

inline fun DateTime.toJavaLocalDate() = LocalDate.of(this.year, monthOfYear, dayOfMonth)