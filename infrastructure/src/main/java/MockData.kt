package org.agh.eaiib

import api.command.account.dto.AccountDto
import api.command.account.dto.CredentialsDto
import api.command.account.dto.UserDto
import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import api.command.event.dto.LocalizationDto
import api.command.event.dto.ParticipantDto
import domain.account.model.user.info.Sex
import domain.event.model.details.Category
import java.time.LocalDate
import java.time.LocalDateTime

object MockData {
    val user1 = UserDto(id = "1",
            firstName = "Radek",
            lastName = "Chrzanowski",
            birthDate = LocalDate.parse("1997-04-20"),
            description = "Radek",
            sex = Sex.MALE,
            email = "chrzrado@gmail.com",
            phoneNumber = "576590022")

    val user2 = UserDto(id = "2",
            firstName = "Sebastian",
            lastName = "Chrzanowski",
            birthDate = LocalDate.parse("1997-04-20"),
            description = "Seba",
            sex = Sex.MALE)

    val user3 = UserDto(id = "3",
            firstName = "Klaudia",
            lastName = "Bednarz",
            birthDate = LocalDate.parse("1997-05-07"),
            description = "Klaudia",
            sex = Sex.FEMALE)

    val account1 = AccountDto(credentials = CredentialsDto("lol", "lol123"), user = user1)
    val account3 = AccountDto(credentials = CredentialsDto("klaudia", "bednarz123"), user = user3)
    val account2 = AccountDto(credentials = CredentialsDto("seba", "chrzanowski123"), user = user2)

    val accounts: List<AccountDto> = listOf(account1, account2, account3)

    val details = EventDetailsDto(minAllowedAge = 18,
            maxAllowedAge = 24,
            minNumberOfPeople = 1,
            startDate = LocalDateTime.now(),
            endTime = LocalDateTime.now().plusDays(1),
            localization = LocalizationDto(50.06, 19.92),
            category = Category.PARTY)

    fun UserDto.toParticipiant() = ParticipantDto(id = id!!, firstName = firstName, lastName = lastName, age = 22)
    val event1 = EventDto(name = "urodziny radka",
            imageName = "pool.png",
            details = details,
            organizers = user2.toParticipiant(),
            guest = setOf())
    val event2 = EventDto(name = "urodziny klaudia",
            details = details,
            organizers = user1.toParticipiant(),
            guest = setOf(user2.toParticipiant()))

    val mockedEvents = listOf(event1, event2)
}