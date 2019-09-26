package api.command.event.dto

import domain.event.model.Status
import domain.event.model.details.Category
import java.time.LocalDateTime


data class ParticipantDto(val id: String,
                          val firstName: String,
                          val lastName: String,
                          val age: Int)

data class EventDto(val id: String? = null,
                    val name: String,
                    val status: Status = Status.BEFORE,
                    val details: EventDetailsDto,
                    val organizers: Set<ParticipantDto>,
                    val guest: Set<ParticipantDto>)

data class EventDetailsDto(val minAllowedAge: Int? = null,
                           val maxAllowedAge: Int? = null,
                           val minNumberOfPeople: Int? = null,
                           val maxNumberOfPeople: Int? = null,
                           val description: String? = null,
                           val startDate: LocalDateTime,
                           val endTime: LocalDateTime,
                           val localization: LocalizationDto,
                           val category: Category)


data class LocalizationDto(val latitude: Double,
                           val longitude: Double)

data class EventList(val events: List<EventSnapshot>)

data class EventSnapshot(val name: String,
                         val localization: LocalizationDto,
                         val minNumberOfPeople: Int? = null,
                         val maxNumberOfPeople: Int? = null,
                         val numberOfGuests: Int,
                         val category: Category)

data class EventFilterDto(val name: String? = null,
                          val minAllowedAge: Int? = null,
                          val maxAllowedAge: Int? = null,
                          val startTime: LocalDateTime? = null,
                          val endTime: LocalDateTime? = null,
                          val localizationRadius: Double,
                          val maxNumberOfPeople: Int? = null,
                          val minNumberOfPeople: Int? = null,
                          val category: Category? = null,
                          val localization: LocalizationDto)