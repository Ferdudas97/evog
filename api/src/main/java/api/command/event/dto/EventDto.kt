package api.command.event.dto

import com.fasterxml.jackson.annotation.JsonFormat
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
                    val organizers: ParticipantDto,
                    val isAssigned : Boolean = true,
                    val guest: Set<ParticipantDto>)

const val pattern = "yyyy-MM-dd HH:mm:ss"
data class EventDetailsDto(val minAllowedAge: Int? = null,
                           val maxAllowedAge: Int? = null,
                           val minNumberOfPeople: Int? = null,
                           val maxNumberOfPeople: Int? = null,
                           val description: String? = null,
                           @JsonFormat(pattern = pattern)
                           val startDate: LocalDateTime,
                           @JsonFormat(pattern = pattern)
                           val endTime: LocalDateTime,
                           val localization: LocalizationDto,
                           val category: Category)


data class LocalizationDto(val latitude: Double,
                           val longitude: Double)

data class EventList(val events: List<EventSnapshot>)

data class EventSnapshot(val id: String,
                         val name: String,
                         val localization: LocalizationDto,
                         val minNumberOfPeople: Int?,
                         val maxNumberOfPeople: Int?,
                         val numberOfGuests: Int,
                         @JsonFormat(pattern = pattern)
                         val startTime: LocalDateTime,
                         @JsonFormat(pattern = pattern)
                         val endTime: LocalDateTime,
                         val category: Category)

data class EventFilterDto(val name: String? = null,
                          val minAllowedAge: Int? = null,
                          val maxAllowedAge: Int? = null,
                          val startTime: LocalDateTime = LocalDateTime.MIN,
                          val endTime: LocalDateTime = LocalDateTime.MAX,
                          val localizationRadius: Double,
                          val maxNumberOfPeople: Int? = null,
                          val minNumberOfPeople: Int? = null,
                          val category: Category? = null,
                          val isAssigned: Boolean,
                          val localization: LocalizationDto)