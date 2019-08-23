package org.agh.eaiib.di

import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import application.command.account.user.handler.UpdateUserHandler
import com.github.salomonbrys.kodein.*
import db.dao.account.UserDao
import db.dao.account.UserDaoImpl
import db.repository.UserRepositoryImpl
import db.repository.account.AccountRepositoryImpl
import domain.account.repository.AccountRepository
import domain.account.repository.UserRepository
import integration.DomainEvent
import integration.ProducerFactory
import org.agh.eaiib.integration.events.send
import org.apache.kafka.clients.producer.Producer
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


fun dep() = Kodein {
    extend(repositories())
    bind<(DomainEvent) -> Unit>() with singleton { sender(ProducerFactory.createProducer()) }
    bind<ChangePasswordHandler>() with provider { ChangePasswordHandler(instance(), instance()) }
    bind<CreateUserCommandHandler>() with provider { CreateUserCommandHandler(instance(), instance()) }
    bind<UpdateUserHandler>() with provider { UpdateUserHandler(instance(), instance()) }
}

private fun repositories() = Kodein {
    extend(dao())
    bind<AccountRepository>() with singleton { AccountRepositoryImpl(instance()) }
    bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
}

private fun dao() = Kodein {
    bind<UserDao>() with singleton { UserDaoImpl(instance()) }
    bind<CoroutineDatabase>() with provider { KMongo.createClient().coroutine.getDatabase("evog") }

}

private fun sender(producer: Producer<String, String>): (DomainEvent) -> Unit = { domainEvent -> producer.send(domainEvent) }

