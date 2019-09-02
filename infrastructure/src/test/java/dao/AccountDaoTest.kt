package org.agh.eaiib.dao

import db.dao.account.AccountDaoImpl
import domain.account.model.user.info.Sex
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.time.LocalDate


class AccountDaoTest : StringSpec() {

    private val accountDao = AccountDaoImpl(KMongo.createClient().coroutine.getDatabase("evgo"));


    private val id = "id"
    private val userEntity = UserEntity(
            id = id,
            birthDate = LocalDate.now(), firstName = "Radek",
            lastName = "chrzanowski",
            sex = Sex.MALE)
    private val accountEntity = AccountEntity("lol", "lol123", userEntity)

    init {
        "should save new account" {

            accountDao.save(accountEntity)
            val userFromDb = accountDao.findById(accountEntity.login)

            userFromDb shouldBe accountEntity

        }

        "should find by credentials account which is saved" {
            val fromDb = accountDao.findByCredentials("lol", "lol123")

            fromDb shouldBe accountEntity
        }
        "should update user which is saved" {
            val updated = accountEntity.copy(user = userEntity.copy(lastName = "test2"))
            accountDao.update(updated)
            val fromDb = accountDao.findById(accountEntity.login)

            fromDb shouldBe updated
        }

    }
}