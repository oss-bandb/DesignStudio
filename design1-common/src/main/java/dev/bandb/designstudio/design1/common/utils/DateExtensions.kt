package dev.bandb.designstudio.design1.common.utils

import kotlinx.datetime.*

fun Instant.todayTime(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
    toLocalDateTime(timeZone)

fun Instant.today(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate =
    toLocalDateTime(timeZone).date

fun LocalDate.isToday(): Boolean = this == Clock.System.now().today()
fun LocalDate.isTomorrow(): Boolean =
    this == Clock.System.now().today().plus(1, DateTimeUnit.DAY)