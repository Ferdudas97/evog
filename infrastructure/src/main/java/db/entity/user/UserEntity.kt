package org.agh.eaiib.db.entity.user

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import user.info.Sex
import java.time.LocalDate
import java.util.*


object Users: Table() {
    val id : Column<UUID>  = uuid("user_id").primaryKey()
    val firstName : Column<String> = varchar("first_name",40)
    val lastName : Column<String> = varchar("last_name", 40)
    val birthDate: Column<DateTime> = date("birth_date")
    val sex = enumeration("sex", Sex::class)
    val phoneNumber = varchar("phone_number",12).nullable()
    val email = varchar("email",40).nullable()
}