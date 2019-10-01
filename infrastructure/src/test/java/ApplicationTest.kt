package org.agh.eaiib

import api.command.account.dto.AccountDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ kodeinApp(true) }) {
            handleRequest(HttpMethod.Post, "/accounts") {
                setBody(MockData.account1.toJson())
                addHeader(HttpHeaders.ContentType,ContentType.Application.Json.toString())
            }.apply {
                response.content shouldNotBe (null)
                val res = response.toClass(AccountDto::class.java)
                res shouldBe MockData.account1
            }
        }
    }

}

private fun Any.toJson() = mapper.writeValueAsString(this)
private val mapper = ObjectMapper().apply { registerModule(JavaTimeModule()) }
private fun <T> TestApplicationResponse.toClass(clazz : Class<T>) = mapper.readValue(content,clazz)