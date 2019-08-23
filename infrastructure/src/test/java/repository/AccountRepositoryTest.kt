package org.agh.eaiib.repository

import db.dao.account.UserDaoImpl
import db.repository.UserRepositoryImpl
import db.repository.account.AccountRepositoryImpl
import domain.account.model.Account
import domain.account.model.Login
import domain.account.model.Password
import domain.account.model.user.User
import domain.account.model.user.UserId
import domain.account.model.user.info.*
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.specs.StringSpec
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.time.LocalDate

class UserRepositoryTest : StringSpec() {
    private val db = UserDaoImpl(KMongo.createClient().coroutine.getDatabase("evog"));
    private val accountRepository = AccountRepositoryImpl(db)
    private val userRepository = UserRepositoryImpl(db)

    private val user = User(
            id = UserId("id"),
            personalInfo = PersonalInfo(FirstName("radek"),
                    LastName("chrzanowski"),
                    BirthDate(LocalDate.now()),
                    null,
                    Sex.MALE),
            contactInfo = ContactInfo(PhoneNumber("576956962"),
                    Email("ads@pdasd.pl")),
            account = Account(Login("lol"), Password("lol123")))

    init {
        "should save valid user" {
            val result = userRepository.save(user)
            result.isRight().shouldBeTrue()
            val userFromDb = userRepository.findById(UserId("id"))
            userFromDb.shouldNotBeNull()
            val accountFromDb = accountRepository.findByCredentials(Login("lol"),
                    Password("lol123"))
            accountFromDb.shouldNotBeNull()

        }

        "should not save invalid user" {

            val result = userRepository.save(user.copy(contactInfo =
            user.contactInfo.copy(email = Email("@"))))
            result.isLeft().shouldBeTrue()
            val accountFromDb = accountRepository.findByCredentials(Login("login1"),
                    Password("lol123"))
            accountFromDb.shouldBeNull()
        }
    }
}