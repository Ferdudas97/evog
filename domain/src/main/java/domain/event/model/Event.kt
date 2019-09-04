package domain.event.model

import domain.event.model.details.EventDetails
import domain.event.model.participiant.Guest
import domain.event.model.participiant.Organizator


data class EventId(val value: String)

enum class Status {
    CANCELED, BEFORE, AFTER, TAKE_PLACE
}

data class EventName(val value: String)
data class Event(val id: EventId,
                 val name: EventName,
                 val details: EventDetails,
                 val guests: Set<Guest> = setOf(),
                 val status: Status,
                 val organizers: Set<Organizator> = setOf())


