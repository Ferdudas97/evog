package org.agh.eaiib

import account.model.user.info.Sex
import com.google.gson.Gson
import command.account.AccountCommand
import dto.AccountDto
import dto.UserDto
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.joda.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ kodeinApp(true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
            val accountDto = AccountDto(login = "login", password = "pass",
                    user = UserDto(id = "1", firstName = "radek",
                            lastName = "Chrzanowski",
                            birthDate = LocalDate.parse("1997-04-20"),
                            description = "description",
                            sex = Sex.MALE,
                            phoneNumber = "123 123 4234",
                            email = "123123@hmail.com")
            )
            handleRequest(HttpMethod.Post, "/accounts/create") {
                setBody(Gson().toJson(AccountCommand.Create(accountDto)))
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }.apply {
                assertEquals(response.status()!!.value.toString(), "sdaasd")
            }
        }
    }
}
