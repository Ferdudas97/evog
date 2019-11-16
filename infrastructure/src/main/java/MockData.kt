package org.agh.eaiib

import api.command.account.AccountCommand
import api.command.account.dto.AccountDto
import api.command.account.dto.CredentialsDto
import api.command.account.dto.UserDto
import api.command.event.dto.EventDetailsDto
import api.command.event.dto.EventDto
import api.command.event.dto.LocalizationDto
import api.command.event.dto.ParticipantDto
import domain.account.model.user.info.Sex
import domain.event.model.details.Category
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

object MockData {
    private val path = "C:\\Users\\radek\\IdeaProjects\\evog\\infrastructure\\src\\main\\resources\\images\\"
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

    val photo1 = listOf(File("${path}radek.jpg").readBytes())
    val photo2 = listOf(File("${path}seba.jpg").readBytes())
    val photo3 = listOf(File("${path}klaudia.jpg").readBytes())

    val account1 = AccountDto(credentials = CredentialsDto("lol", "lol123"), user = user1)
    val account3 = AccountDto(credentials = CredentialsDto("klaudia", "bednarz123"), user = user3)
    val account2 = AccountDto(credentials = CredentialsDto("seba", "chrzanowski123"), user = user2)

    val accounts: List<AccountDto> = listOf(account1, account2, account3)

    val createAccountsCommands = listOf(AccountCommand.Create(account1, photo1),
            AccountCommand.Create(account2, photo2),
            AccountCommand.Create(account3, photo3))
    val rzeszow = LocalizationDto(50.01, 22.012)
    val krakow = LocalizationDto(50.08, 19.902)
    val details = EventDetailsDto(minAllowedAge = 18,
            maxAllowedAge = 24,
            minNumberOfPeople = 1,
            startDate = LocalDateTime.now().plusHours(5),
            endTime = LocalDateTime.now().plusHours(10),
            localization = rzeszow,
            category = Category.PARTY)

    fun UserDto.toParticipiant() = ParticipantDto(id = id!!, firstName = firstName, lastName = lastName, age = 22, fileId = "Randoom")
    val event1 = EventDto(name = "urodziny radka",
            imageName = "pool.png",
            details = details,
            organizers = user2.toParticipiant(),
            guest = setOf())
    val event2 = EventDto(name = "urodziny klaudia",
            details = details,
            organizers = user1.toParticipiant(),
            guest = setOf(user2.toParticipiant()))

    val event3 = EventDto(name = "urodziny seny",
            details = details.copy(startDate = LocalDateTime.now().minusDays(1)),
            organizers = user3.toParticipiant(),
            guest = setOf(user2.toParticipiant()))

    val mockedEvents = listOf(event1, event2, event3)
}