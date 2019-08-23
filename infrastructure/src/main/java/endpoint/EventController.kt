package org.agh.eaiib.endpoint

import api.command.event.EventCommand
import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import application.command.event.handler.CancelEventHandler
import application.command.event.handler.CreateEventHandler
import application.command.event.handler.UpdateEventHandler
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.routing.*


fun Route.eventRoute(cancelEventHandler: CancelEventHandler,
                     createEventHandler: CreateEventHandler,
                     updateEventHandler: UpdateEventHandler) = route("/events") {

    post("") {
        val dto = call.receive<EventDto>()
        createEventHandler.handle(EventCommand.Create(dto))
    }

    put("/{id}") {
        val details = call.receive<EventDetailsDto>()
        val id = call.parameters["id"]
        id?.let { updateEventHandler.handle(EventCommand.Update(id, details)) }
    }
    delete("/{id}") {
        val id = call.parameters["id"]
        id?.let { cancelEventHandler.handle(EventCommand.Cancel(id)) }
    }
}