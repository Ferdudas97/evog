package org.agh.eaiib.di

import application.command.account.handler.ChangePasswordHandler
import application.command.account.handler.CreateUserCommandHandler
import application.command.account.handler.LoginCommandHandler
import application.command.account.user.handler.UpdateUserHandler
import application.command.event.NotificationService
import application.command.event.handler.CancelEventHandler
import application.command.event.handler.CreateEventHandler
import application.command.event.handler.RemoveGuestEventHandler
import application.command.event.handler.UpdateEventHandler
import application.command.event.handler.notification.AcceptEventInvitationRequestHandler
import application.command.event.handler.notification.AssignEventHandler
import application.command.event.handler.notification.DeleteNotificationHandler
import application.command.event.handler.notification.RejectEventInvitationRequestHandler
import application.mapper.event.toDomain
import application.mapper.user.toDomain
import application.query.event.handler.FindEventByIdQueryHandler
import application.query.event.handler.GetFilteredEventsQueryHandler
import application.query.notification.handler.FindNotificationsByUserIdQueryHandler
import application.query.user.handler.FindUserByIdQueryHandler
import application.services.IntervalActionService
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.Kodein.Module
import com.mongodb.reactivestreams.client.MongoDatabase
import db.dao.account.AccountDao
import db.dao.account.AccountDaoImpl
import db.repository.UserRepositoryImpl
import db.repository.account.AccountRepositoryImpl
import domain.account.repository.AccountRepository
import domain.account.repository.UserRepository
import domain.event.repository.EventRepository
import domain.notification.repository.NotificationRepository
import integration.DomainEvent
import kotlinx.coroutines.runBlocking
import org.agh.eaiib.MockData
import org.agh.eaiib.db.dao.event.EventDao
import org.agh.eaiib.db.dao.event.EventDaoImpl
import org.agh.eaiib.db.dao.notification.NotificationDao
import org.agh.eaiib.db.dao.notification.NotificationDaoImpl
import org.agh.eaiib.db.repository.event.EventRepositoryImpl
import org.agh.eaiib.db.repository.notification.NotificationRepositoryImpl
import org.agh.eaiib.integration.events.send
import org.apache.kafka.clients.producer.Producer
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


fun dep() = Kodein {
    import(repositories())
    import(services())
    bind<(DomainEvent) -> Unit>() with singleton { { e: DomainEvent -> } }
    bind<ChangePasswordHandler>() with singleton { ChangePasswordHandler(instance(), instance()) }
    bind<CreateUserCommandHandler>() with singleton { CreateUserCommandHandler(instance(), instance()) }
    bind<LoginCommandHandler>() with singleton { LoginCommandHandler(instance()) }

    bind<UpdateUserHandler>() with singleton { UpdateUserHandler(instance(), instance()) }

    bind<CreateEventHandler>() with singleton { CreateEventHandler(instance(), instance()) }
    bind<CancelEventHandler>() with singleton { CancelEventHandler(instance(), instance()) }
    bind<UpdateEventHandler>() with singleton { UpdateEventHandler(instance(), instance()) }

    bind<RemoveGuestEventHandler>() with singleton { RemoveGuestEventHandler(instance(), instance(), instance()) }
    bind<AssignEventHandler>() with singleton { AssignEventHandler(instance(), instance(), instance()) }
    bind<DeleteNotificationHandler>() with singleton { DeleteNotificationHandler(instance()) }
    bind<RejectEventInvitationRequestHandler>() with singleton { RejectEventInvitationRequestHandler(instance()) }
    bind<AcceptEventInvitationRequestHandler>() with singleton { AcceptEventInvitationRequestHandler(instance(), instance()) }
    bind<FindNotificationsByUserIdQueryHandler>() with singleton { FindNotificationsByUserIdQueryHandler(instance(), instance()) }
    bind<FindUserByIdQueryHandler>() with singleton { FindUserByIdQueryHandler(instance()) }
    bind<FindEventByIdQueryHandler>() with singleton { FindEventByIdQueryHandler(instance()) }
    bind<GetFilteredEventsQueryHandler>() with singleton { GetFilteredEventsQueryHandler(instance()) }


}


private fun services() = Module {
    bind<IntervalActionService>() with singleton { IntervalActionService(instance(), instance()) }
    bind<NotificationService>() with singleton { NotificationService(instance()) }
}

private fun repositories() = Module {
    import(dao())
    bind<AccountRepository>() with singleton {
        AccountRepositoryImpl(instance()).apply {
            runBlocking {
                MockData.accounts.map { it.toDomain() }.forEach { save(it) }
            }
        }
    }
    bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }

    bind<EventRepository>() with singleton {
        EventRepositoryImpl(instance()).apply {
            runBlocking {
                MockData.mockedEvents.map { it.toDomain() }.forEach { save(it) }
            }
        }
    }

    bind<NotificationRepository>() with singleton {
        NotificationRepositoryImpl(instance())
    }
}

private fun dao() = Module {
    bind<NotificationDao>() with singleton { NotificationDaoImpl(instance()) }
    bind<EventDao>() with singleton { EventDaoImpl(instance()) }
    bind<AccountDao>() with singleton { AccountDaoImpl(instance()) }
//    bind<GridFSBucket>() with singleton {
//        com.mongodb.MongoClient().getDatabase("evog")
//                .let { GridFSBuckets.create(it) }
//    }
    bind<MongoDatabase>() with singleton { KMongo.createClient().getDatabase("evog") }
    bind<CoroutineDatabase>() with provider {

        val db = instance<MongoDatabase>().coroutine
        runBlocking {
            //            db.drop()
            return@runBlocking db
        }
    }

}

private fun sender(producer: Producer<String, String>): (DomainEvent) -> Unit = { domainEvent -> producer.send(domainEvent) }

