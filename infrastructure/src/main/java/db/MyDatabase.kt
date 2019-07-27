package org.agh.eaiib.db

import account.model.user.info.Sex
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*


object Accounts : Table() {
    val login = varchar("login", 40).primaryKey()
    val password = varchar("password", 50).primaryKey()
    val creationDate = date("creation_date")
    val userId = uuid("user_id").uniqueIndex().references(Users.id)

    fun toAccountEntity(row: ResultRow) = AccountEntity(
            login = row[login],
            password = row[password],
            creationDate = row[creationDate],
            user = row[userId].let { uuid -> Users.select(where = { Users.id eq uuid }) }
                    .map(Users::toUserEntity)
                    .first()
    )
}


object Users : Table() {
    val id: Column<UUID> = uuid("user_id").primaryKey()
    val firstName: Column<String> = varchar("first_name", 40)
    val lastName: Column<String> = varchar("last_name", 40)
    val birthDate: Column<DateTime> = date("birth_date")
    val description: Column<String?> = text("description").nullable()
    val sex = enumeration("sex", Sex::class)
    val phoneNumber = varchar("phone_number", 12).nullable()
    val email = varchar("email", 40).nullable()

    fun toUserEntity(row: ResultRow) = UserEntity(
            id = row[id].toString(),
            firstName = row[firstName],
            lastName = row[lastName],
            sex = row[sex],
            description = row[description],
            birthDate = row[birthDate],
            email = row[email],
            phoneNumber = row[phoneNumber]
    )
}


object MyDatabase {
    fun init() {
        connectDatabase()
        createDatabase()
    }

    private fun createDatabase() =
            transaction {
                SchemaUtils.create(Accounts, Users)
            }

    private fun connectDatabase() = Database.connect("jdbc:postgresql://localhost/admin",
            user = "admin", password = "admin",
            driver = "org.postgresql.Driver")

}