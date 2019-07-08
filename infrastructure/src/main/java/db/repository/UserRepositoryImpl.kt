package db.repository

import org.agh.eaiib.db.Users
import org.agh.eaiib.db.entity.user.UserEntity
import org.agh.eaiib.db.mapper.UserMapper
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import repository.UserRepository
import user.User
import user.UserId
import java.util.*


class UserRepositoryImpl() : UserRepository {

    override fun delete(id: UserId)  {
        Users.deleteWhere {
            Users.id eq UUID.fromString(id.id)

        }
    }

    override fun findById(id: UserId): User? {
        val entity = Users.select { Users.id eq UUID.fromString(id.toString()) }
                .map(Users::toUserEntity)
                .first()
        return UserMapper.mapToDomain(entity)
    }

    override fun save(obj: User) {
        val entity = UserMapper.mapToEntity(obj)
        transaction {
            Users.insert {
                it[Users.id] = UUID.fromString(entity.id)
                it[Users.firstName] = entity.firstName
                it[Users.lastName] = entity.lastName
                it[Users.sex] = entity.sex
                it[Users.description] = entity.description
                it[Users.birthDate] = entity.birthDate
                it[Users.email] = entity.email
                it[Users.phoneNumber] = entity.phoneNumber
            }
            commit()
        }

    }

    override fun update(obj: User) {
        val entity = UserMapper.mapToEntity(obj)
        transaction {
            Users.update(where = { Users.id eq UUID.fromString(entity.id) },
                    limit = 1,
                    body = {
                        it[Users.id] = UUID.fromString(entity.id)
                        it[Users.firstName] = entity.firstName
                        it[Users.lastName] = entity.lastName
                        it[Users.sex] = entity.sex
                        it[Users.description] = entity.description
                        it[Users.birthDate] = entity.birthDate
                        it[Users.email] = entity.email
                        it[Users.phoneNumber] = entity.phoneNumber
                    })
            commit()
        }
    }



}