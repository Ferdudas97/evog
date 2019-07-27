package org.agh.eaiib.dao

import account.model.user.info.Sex
import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.agh.eaiib.db.dao.AccountDaoImpl
import org.agh.eaiib.db.dao.UserDaoImpl
import org.agh.eaiib.db.entity.account.AccountEntity
import org.agh.eaiib.db.entity.user.UserEntity
import org.joda.time.DateTime
import java.util.*


class AccountDaoTest : StringSpec() {

    val accountDao = AccountDaoImpl(UserDaoImpl())
    val account = AccountEntity("login", "password", DateTime.now(), UserEntity(
            id = UUID.randomUUID().toString(),
            birthDate = DateTime.now().withoutTime(), firstName = "Radek",
            lastName = "chrzanowski"
            , sex = Sex.MALE))

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        init()
    }

    init {
        "should save new account" {

            accountDao.save(account)
            val accountFromDb = accountDao.findByCredentials("login", "password")

            val expected = account.copy(creationDate = DateTime().withoutTime())
            accountFromDb shouldBe expected

        }

        "should update account" {
            val updated = account.copy(creationDate = DateTime().withoutTime(), password = "newPassword")

            accountDao.update(updated)
            val accountFromDb = accountDao.findByCredentials("login", "newPassword")

            accountFromDb shouldBe updated

        }

    }


}