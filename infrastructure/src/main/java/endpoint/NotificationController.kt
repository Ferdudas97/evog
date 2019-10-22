package org.agh.eaiib.endpoint

import api.command.event.EventCommand
import api.query.notification.NotificationQuery
import application.command.event.handler.notification.AcceptEventInvitationRequestHandler
import application.command.event.handler.notification.DeleteNotificationHandler
import application.command.event.handler.notification.RejectEventInvitationRequestHandler
import application.query.notification.handler.FindNotificationsByUserIdQueryHandler
import arrow.core.Either
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.*


fun Route.notificationRoute(rejectEventInvitationRequestHandler: RejectEventInvitationRequestHandler,
                            findNotificationsByUserIdQueryHandler: FindNotificationsByUserIdQueryHandler,
                            deleteNotificationHandler: DeleteNotificationHandler,
                            acceptEventInvitationRequestHandler: AcceptEventInvitationRequestHandler) = route("/notifications") {
    route("/{id}") {
        post("/accept") {

            val notificationId = call.parameters["id"]!!
            val command = EventCommand.Notification.Accept(notificationId)
            val result = acceptEventInvitationRequestHandler.handle(command)
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, result.a)
                is Either.Right -> call.respond(HttpStatusCode.OK)
            }
        }

        post("/reject") {
            val notificationId = call.parameters["id"]!!
            val command = EventCommand.Notification.Reject(notificationId)
            val result = rejectEventInvitationRequestHandler.handle(command)
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, result.a)
                is Either.Right -> call.respond(HttpStatusCode.OK)
            }
        }
        delete {
            val id = call.parameters["id"]!!
            val command = EventCommand.Notification.Delete(id)
            val result = deleteNotificationHandler.handle(command)
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, result.a)
                is Either.Right -> call.respond(HttpStatusCode.OK)
            }
        }
    }
    get() {
        val userId = call.getUserId()
        val notifications = findNotificationsByUserIdQueryHandler.exevute(NotificationQuery.FindByUserId(userId))
        call.respond(notifications)
    }


}
