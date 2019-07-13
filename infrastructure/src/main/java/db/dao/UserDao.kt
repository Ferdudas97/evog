package org.agh.eaiib.db.dao

import org.agh.eaiib.db.entity.user.UserEntity

interface UserDao {
    fun delete(id: String)

    fun findById(id: String): UserEntity?

    fun save(userEntity: UserEntity)

    fun update(userEntity: UserEntity)
}