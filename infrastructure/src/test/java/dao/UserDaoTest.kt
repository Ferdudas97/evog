package org.agh.eaiib.dao

import db.dao.account.UserDaoImpl
import domain.account.model.user.info.Sex
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.time.LocalDate
import java.util.*


class UserDaoTest : StringSpec() {

    private val userDao = UserDaoImpl(KMongo.createClient().coroutine.getDatabase("evgo"));


    private val id = "id"
    private val userEntity = UserEntity(
            id = id,
            birthDate = LocalDate.now(), firstName = "Radek",
            lastName = "chrzanowski",
            sex = Sex.MALE,
            account = AccountEntity("lol", "lol123"))

    init {
        "should save new user" {

            userDao.save(userEntity)
            val userFromDb = userDao.findById(id)

            userFromDb shouldBe userEntity

            val account = userDao.findByCredentials(userFromDb!!.account.login, userFromDb.account.password)
            account shouldBe userFromDb
        }

        "should find by credentials user which is saved" {
            val userFromDb = userDao.findByCredentials("lol", "lol123")

            userFromDb shouldBe userEntity
        }
        "should update user which is saved" {
            val updated = userEntity.copy(lastName = "test2")
            userDao.update(updated)
            val userFromDb = userDao.findById(id)

            userFromDb shouldBe updated
        }


        "should not update user which isn't saved" {
            val newId = UUID.randomUUID().toString()
            val entity = userEntity.copy(id = "notSavedId")
            userDao.update(entity)
            val userFromDb = userDao.findById(newId)

            userFromDb.shouldBeNull()
        }

    }
}