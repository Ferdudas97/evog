package org.agh.eaiib.dao

import io.kotlintest.Spec
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.agh.eaiib.db.dao.UserDaoImpl
import org.agh.eaiib.db.entity.user.UserEntity
import org.joda.time.DateTime
import user.info.Sex
import java.util.*


class UserDaoTest : StringSpec() {

    val userDao = UserDaoImpl();

    override fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        init()
    }

    val id = UUID.randomUUID().toString()
    val userEntity = UserEntity(
            id = id,
            birthDate = DateTime.now().withoutTime(), firstName = "Radek",
            lastName = "chrzanowski"
            , sex = Sex.MALE)

    init {
        "should save new user" {

            userDao.save(userEntity)
            val userFromDb = userDao.findById(id)

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
            val entity = UserEntity(
                    id = newId,
                    birthDate = DateTime.now().withoutTime(), firstName = "Radek123",
                    lastName = "chrzanowsk132i"
                    , sex = Sex.MALE)
            userDao.update(entity)
            val userFromDb = userDao.findById(newId)

            userFromDb.shouldBeNull()
        }

    }
}