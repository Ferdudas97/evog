package org.agh.eaiib.repository

import account.Account
import account.Login
import account.Password
import account.user.User
import account.user.UserId
import account.user.info.*
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.specs.StringSpec
import org.agh.eaiib.dao.init
import org.agh.eaiib.db.dao.AccountDaoImpl
import org.agh.eaiib.db.dao.UserDaoImpl
import org.agh.eaiib.db.repository.AccountRepositoryImpl
import org.joda.time.LocalDate
import java.util.*

class AccountRepositoryTest : StringSpec() {

    val accountRepository = AccountRepositoryImpl(AccountDaoImpl(UserDaoImpl()))


    init {
        init()
        "should save valid account" {
            val account = Account(Login("login"),
                    Password("lol123"),
                    User(UserId(UUID.randomUUID().toString()), PersonalInfo(FirstName("radek"),
                            LastName("chrzanowski"),
                            BirthDate(LocalDate.now()),
                            null,
                            Sex.MALE),
                            ContactInfo(PhoneNumber("576956962"),
                                    Email("ads@pdasd.pl"))))

            val result = accountRepository.save(account)
            result.isRight().shouldBeTrue()
            val accountFromDb = accountRepository.findByCredentials(Login("login"),
                    Password("lol123"))
            accountFromDb.shouldNotBeNull()
        }

        "should not save invalid account" {
            val account = Account(Login("login1"),
                    Password("lol123"),
                    User(UserId(UUID.randomUUID().toString()), PersonalInfo(FirstName("radek"),
                            LastName("chrzanowski"),
                            BirthDate(LocalDate.now()),
                            null,
                            Sex.MALE),
                            ContactInfo(PhoneNumber("adasdasd"),
                                    Email("ads@pdasd.pl"))))

            val result = accountRepository.save(account)
            result.isLeft().shouldBeTrue()
            val accountFromDb = accountRepository.findByCredentials(Login("login1"),
                    Password("lol123"))
            accountFromDb.shouldBeNull()
        }
    }
}