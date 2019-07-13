package org.agh.eaiib.db.dao

import org.agh.eaiib.db.Users
import org.agh.eaiib.db.entity.user.UserEntity
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.*

class UserDaoImpl : UserDao {

    override fun delete(id: String) {
        Users.deleteWhere {
            Users.id eq UUID.fromString(id)

        }
    }

    override fun findById(id: String): UserEntity? {
        return Users.select { Users.id eq UUID.fromString(id.toString()) }
                .map(Users::toUserEntity)
                .first()
    }

    override fun save(entity: UserEntity) {
        transaction {
            Users.insert {
                it[id] = UUID.fromString(entity.id)
                it[firstName] = entity.firstName
                it[lastName] = entity.lastName
                it[sex] = entity.sex
                it[description] = entity.description
                it[birthDate] = entity.birthDate
                it[email] = entity.email
                it[phoneNumber] = entity.phoneNumber
            }
            commit()
        }

    }

    override fun update(entity: UserEntity) {
        transaction {
            Users.update(where = { Users.id eq UUID.fromString(entity.id) },
                    limit = 1,
                    body = {
                        it[id] = UUID.fromString(entity.id)
                        it[firstName] = entity.firstName
                        it[lastName] = entity.lastName
                        it[sex] = entity.sex
                        it[description] = entity.description
                        it[birthDate] = entity.birthDate
                        it[email] = entity.email
                        it[phoneNumber] = entity.phoneNumber
                    })
            commit()
        }
    }
}