package api.command.event.dto

import domain.event.model.Status
import domain.event.model.details.Category
import java.time.LocalDateTime


data class ParticipantDto(val id: String,
                          val firstName: String,
                          val lastName: String,
                          val age: Int)

data class EventDto(val id: String,
                    val status: Status,
                    val details: EventDetailsDto,
                    val organizers: Set<ParticipantDto>,
                    val guest: Set<ParticipantDto>)

data class EventDetailsDto(val minAllowedAge: Int?,
                           val maxAllowedAge: Int?,
                           val minNumberOfPeople: Int?,
                           val maxNumberOfPeople: Int?,
                           val description: String?,
                           val startDate: LocalDateTime,
                           val endTime: LocalDateTime,
                           val localization: LocalizationDto,
                           val category: Category)


data class LocalizationDto(val latitude: Double,
                           val longitude: Double)