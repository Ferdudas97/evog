package domain.event.model

import domain.event.model.details.EventDetails
import domain.event.model.participiant.Guest
import domain.event.model.participiant.Organizator


data class EventId(val value: String)

data class Event(val id: EventId,
                 val localization: Localization,
                 val details: EventDetails,
                 val guests: Set<Guest> = setOf(),
                 val organizers: Set<Organizator> = setOf())