package org.agh.eaiib.di

import com.github.salomonbrys.kodein.*
import command.account.handler.ChangePasswordHandler
import command.account.handler.CreateAccountHandler
import command.user.handler.UpdateUserHandler
import db.repository.UserRepositoryImpl
import integration.DomainEvent
import integration.ProducerFactory
import org.agh.eaiib.db.dao.AccountDao
import org.agh.eaiib.db.dao.AccountDaoImpl
import org.agh.eaiib.db.dao.UserDao
import org.agh.eaiib.db.dao.UserDaoImpl
import org.agh.eaiib.db.repository.AccountRepositoryImpl
import org.agh.eaiib.integration.events.send
import org.apache.kafka.clients.producer.Producer
import repository.AccountRepository
import repository.UserRepository


fun dep() = Kodein {
    extend(repositories())
    bind<(DomainEvent) -> Unit>() with singleton { sender(ProducerFactory.createProducer()) }
    bind<ChangePasswordHandler>() with provider { ChangePasswordHandler(instance(), instance()) }
    bind<CreateAccountHandler>() with provider { CreateAccountHandler(instance(), instance()) }
    bind<UpdateUserHandler>() with provider { UpdateUserHandler(instance(), instance()) }
}

private fun repositories() = Kodein {
    extend(dao())
    bind<AccountRepository>() with singleton { AccountRepositoryImpl(instance()) }
    bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
}

private fun dao() = Kodein {
    bind<AccountDao>() with singleton { AccountDaoImpl(instance()) }
    bind<UserDao>() with singleton { UserDaoImpl() }
}

private fun sender(producer: Producer<String, String>): (DomainEvent) -> Unit = { domainEvent -> producer.send(domainEvent) }

