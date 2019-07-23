package org.agh.eaiib.dao

import org.joda.time.DateTime

inline fun DateTime.withoutTime(): DateTime {
    return this.toDateMidnight().toDateTime()
}