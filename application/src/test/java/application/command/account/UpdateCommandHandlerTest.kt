package application.command.account

import api.command.account.dto.UserDto
import api.command.account.user.UserCommand
import api.command.account.user.UserCommandResult
import application.command.account.user.handler.UpdateUserHandler
import application.mapper.user.toDomain
import arrow.core.Left
import arrow.core.right
import domain.account.model.user.info.Sex
import exceptions.UpdateError
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDate


class UpdateCommandHandlerTest : StringSpec() {

    private val repositoryMock = UserRepositoryMock()
    private val handler = UpdateUserHandler(repositoryMock)

    init {

        "User should be updated when exists" {
            val dto = UserDto(id = "1", firstName = "radek",
                    lastName = "Chrzanowski",
                    birthDate = LocalDate.parse("1997-04-20"),
                    description = "description",
                    sex = Sex.MALE,
                    phoneNumber = "123 123 4234",
                    email = "123123@hmail.com")
            repositoryMock.update(dto.copy(lastName = "").toDomain())
            val result = handler.handle(command = UserCommand.Update(dto))

            result shouldBe UserCommandResult.Update(dto).right()
        }

        "User shouldn't be updated when doesn't exists" {
            val dto = UserDto(id = "4", firstName = "radek",
                    lastName = "Chrzanowski",
                    birthDate = LocalDate.parse("1997-04-20"),
                    description = "description",
                    sex = Sex.MALE,
                    phoneNumber = "123 123 4234",
                    email = "123123@hmail.com")
            val result = handler.handle(command = UserCommand.Update(dto))

            result shouldBe Left(UpdateError("error"))
        }
    }
}