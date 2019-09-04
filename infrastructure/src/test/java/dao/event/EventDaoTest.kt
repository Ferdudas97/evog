package org.agh.eaiib.dao.event

import domain.event.model.Status
import domain.event.model.details.Category
import kotlinx.coroutines.runBlocking
import org.agh.eaiib.db.entity.event.DetailsEntity
import org.agh.eaiib.db.entity.event.EventEntity
import org.litote.kmongo.coroutine.KMongoCoroutineBaseTest
import org.litote.kmongo.coroutine.first
import org.litote.kmongo.coroutine.insertOne
import java.time.LocalDateTime
import kotlin.test.Test


class EventDaoTest : KMongoCoroutineBaseTest<EventEntity>() {


    private val event = EventEntity("123", "name", setOf(), setOf(), Status.CANCELED,
            DetailsEntity(1, 5, 6, 7, ".",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    Category.OTHER, 0.0, 12.5))


    @Test
    fun `event should be saved in db`() {
        runBlocking {
            col.insertOne(event)
            val result = col.find().first()
        }

    }
}