package org.agh.eaiib.dao

import org.agh.eaiib.db.Accounts
import org.agh.eaiib.db.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun init() {
    connect()
    transaction {
        SchemaUtils.drop(Accounts, Users)
    }
    createSchema()
}

fun connect() = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

fun createSchema() = transaction { SchemaUtils.create(Accounts, Users) }