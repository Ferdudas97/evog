package org.agh.eaiib.db.entity.event

import domain.event.model.Status
import domain.event.model.details.Category
import org.bson.codecs.pojo.annotations.BsonId
import java.time.LocalDateTime


data class Participiant(val id: String,
                        val firstName: String,
                        val lastName: String,
                        val age: Int)

data class EventEntity(
        @BsonId
        val id: String,
        val guests: Set<String>,
        val organizers: Set<String>,
        val status: Status,
        val details: DetailsEntity
)

data class DetailsEntity(
        val minAllowedAge: Int?,
        val maxAllowedAge: Int?,
        val minNumberOfPeople: Int?,
        val maxNumberOfPeople: Int?,
        val description: String?,
        val startDate: LocalDateTime,
        val endTime: LocalDateTime,
        val category: Category,
        val latitude: Double,
        val longitude: Double
)