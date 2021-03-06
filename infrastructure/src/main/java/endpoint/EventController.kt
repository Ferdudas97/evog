package org.agh.eaiib.endpoint

import api.command.event.EventCommand
import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import api.command.event.dto.EventFilterDto
import api.command.event.dto.MessageDto
import api.query.event.EventQuery
import application.command.event.handler.CancelEventHandler
import application.command.event.handler.CreateEventHandler
import application.command.event.handler.RemoveGuestEventHandler
import application.command.event.handler.UpdateEventHandler
import application.command.event.handler.discussion.AddMessageHandler
import application.command.event.handler.notification.AssignEventHandler
import application.query.event.handler.FindEventByIdQueryHandler
import application.query.event.handler.GetFilteredEventsQueryHandler
import arrow.core.Either
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*


fun Route.eventRoute(cancelEventHandler: CancelEventHandler,
                     createEventHandler: CreateEventHandler,
                     updateEventHandler: UpdateEventHandler,
                     findEventByIdQueryHandler: FindEventByIdQueryHandler,
                     getFilteredEventsQueryHandler: GetFilteredEventsQueryHandler,
                     removeGuestEventHandler: RemoveGuestEventHandler,
                     addMessageHandler: AddMessageHandler,
                     assignEventHandler: AssignEventHandler) = route("/events") {

    post("") {
        val dto = call.receive<EventDto>()
        val result = createEventHandler.handle(EventCommand.Create(dto))
        when (result) {
            is Either.Left -> call.respond(HttpStatusCode.NotAcceptable, result.a)
            is Either.Right -> call.respond(HttpStatusCode.Created, result.b)
        }
    }

    route("/{id}") {
        post("/message") {
            val message = call.receive<MessageDto>()
            val id = call.parameters["id"]!!
            val result = addMessageHandler.handle(EventCommand.Discussion.AddMessage(id, message))
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, " Cannot add message")
                is Either.Right -> call.respond(HttpStatusCode.OK, "ok")
            }

        }
        put {
            val details = call.receive<EventDetailsDto>()
            val id = call.parameters["id"]
            id?.let { updateEventHandler.handle(EventCommand.Update(id, details)) }
        }
        delete {
            val id = call.parameters["id"]
            id?.let { cancelEventHandler.handle(EventCommand.Cancel(id)) }
        }

        get {
            val id = call.parameters["id"]
            id?.let {
                val userId = call.getUserId()
                val query = EventQuery.FindById(id, userId)
                findEventByIdQueryHandler.exevute(query).apply {
                    when (this) {
                        null -> call.respond(HttpStatusCode.NotFound, " Event with id $id not found")
                        else -> call.respond(this)
                    }
                }

            }
        }
        post("/assign") {
            val userId = call.getUserId()
            val eventId = call.parameters["id"]!!
            val command = EventCommand.Notification.Assign(eventId, userId)
            val result = assignEventHandler.handle(command)
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, result.a)
                is Either.Right -> call.respond(HttpStatusCode.OK)
            }
        }

        delete("guests/{guestId}") {
            val userId = call.getUserId()
            val guestId = call.parameters["guestId"]!!
            val eventId = call.parameters["id"]!!
            val command = EventCommand.RemoveGuest(eventId, guestId, userId)
            val result = removeGuestEventHandler.handle(command)
            when (result) {
                is Either.Left -> call.respond(HttpStatusCode.InternalServerError, result.a)
                is Either.Right -> call.respond(HttpStatusCode.OK)
            }
        }

    }

    post("/filter") {
        val filter = call.receive<EventFilterDto>()
        val userId = call.getUserId()
        val snapshots = getFilteredEventsQueryHandler.exevute(EventQuery.FindBy(filter, userId))
        call.respond(snapshots)
    }
}