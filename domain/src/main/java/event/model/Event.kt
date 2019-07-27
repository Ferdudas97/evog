package event.model

import event.model.details.EventDetails
import event.model.participiant.Guest
import event.model.participiant.Organizator


data class EventId(val value: String)

data class Event(val id: EventId,
                 val localization: Localization,
                 val details: EventDetails,
                 val guests: Set<Guest> = setOf(),
                 val organizers: Set<Organizator> = setOf())