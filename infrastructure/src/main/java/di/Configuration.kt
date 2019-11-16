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
import application.mapper.user.DEFAULT_FEMALE_PHOTO
import application.mapper.user.DEFAULT_MALE_PHOTO
import application.query.event.handler.FindEventByIdQueryHandler
import application.query.event.handler.GetFilteredEventsQueryHandler
import application.query.notification.handler.FindNotificationsByUserIdQueryHandler
import application.query.user.handler.FindUserByIdQueryHandler
import application.services.IntervalActionService
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.Kodein.Module
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.reactivestreams.client.MongoDatabase
import common.ImageRepository
import db.dao.account.AccountDao
import db.dao.account.AccountDaoImpl
import db.repository.UserRepositoryImpl
import db.repository.account.AccountRepositoryImpl
import domain.account.repository.AccountRepository
import domain.account.repository.UserRepository
import domain.event.repository.EventRepository
import domain.notification.repository.NotificationRepository
import kotlinx.coroutines.runBlocking
import org.agh.eaiib.MockData
import org.agh.eaiib.db.dao.event.EventDao
import org.agh.eaiib.db.dao.event.EventDaoImpl
import org.agh.eaiib.db.dao.images.ImageDao
import org.agh.eaiib.db.dao.images.ImageDaoImpl
import org.agh.eaiib.db.dao.notification.NotificationDao
import org.agh.eaiib.db.dao.notification.NotificationDaoImpl
import org.agh.eaiib.db.repository.ImageRepositoryImpl
import org.agh.eaiib.db.repository.event.EventRepositoryImpl
import org.agh.eaiib.db.repository.notification.NotificationRepositoryImpl
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.io.File


fun dep() = Kodein {
    import(repositories())
    import(services())
    bind<ChangePasswordHandler>() with singleton { ChangePasswordHandler(instance()) }
    bind<CreateUserCommandHandler>() with singleton {
        CreateUserCommandHandler(instance(), instance()).apply {
            runBlocking {
                MockData.createAccountsCommands.forEach { handle(it) }
            }
        }
    }
    bind<ObjectMapper>() with singleton {
        ObjectMapper().apply {
            registerModule(KotlinModule())
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })

            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())  // support java.time.* types
        }
    }
    bind<LoginCommandHandler>() with singleton { LoginCommandHandler(instance()) }

    bind<UpdateUserHandler>() with singleton { UpdateUserHandler(instance()) }

    bind<CreateEventHandler>() with singleton { CreateEventHandler(instance()) }
    bind<CancelEventHandler>() with singleton { CancelEventHandler(instance()) }
    bind<UpdateEventHandler>() with singleton { UpdateEventHandler(instance()) }

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
                //                MockData.accounts.map { it.toDomain() }.forEach { save(it) }
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

    bind<ImageRepository>() with singleton {
        ImageRepositoryImpl(instance())
    }
    bind<NotificationRepository>() with singleton {
        NotificationRepositoryImpl(instance())
    }
}

private fun dao() = Module {
    bind<NotificationDao>() with singleton { NotificationDaoImpl(instance()) }
    bind<EventDao>() with singleton { EventDaoImpl(instance()) }
    bind<AccountDao>() with singleton { AccountDaoImpl(instance()) }
    bind<GridFSBucket>() with singleton {
        com.mongodb.MongoClient().getDatabase("evog")
                .let { GridFSBuckets.create(it) }
                .apply {
                    this.uploadFromStream(DEFAULT_MALE_PHOTO,
                            File("C:\\Users\\radek\\IdeaProjects\\evog\\infrastructure\\src\\main\\resources\\images\\male_user.jpg").inputStream())
                    this.uploadFromStream(DEFAULT_FEMALE_PHOTO,
                            File("C:\\Users\\radek\\IdeaProjects\\evog\\infrastructure\\src\\main\\resources\\images\\famele_user.png").inputStream())

                }
    }
    bind<ImageDao>() with singleton {
        ImageDaoImpl(instance())
    }
    bind<MongoDatabase>() with singleton { KMongo.createClient().getDatabase("evog") }
    bind<CoroutineDatabase>() with provider {

        val db = instance<MongoDatabase>().coroutine
        runBlocking {
            //            db.drop()
            return@runBlocking db
        }
    }

}


