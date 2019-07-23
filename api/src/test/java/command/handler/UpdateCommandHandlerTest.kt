package command.handler

import arrow.core.Left
import arrow.core.right
import command.user.UserCommand
import command.user.UserCommandResult
import command.user.handler.UpdateUserHandler
import dto.UserDto
import exceptions.UpdateError
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.joda.time.LocalDate
import user.info.Sex


class UpdateCommandHandlerTest : StringSpec() {

    val handler = UpdateUserHandler(UserRepositoryMock(), {})

    init {
        "User should be updated when exists" {
            val dto = UserDto(id = "1", firstName = "radek",
                    lastName = "Chrzanowski",
                    birthDate = LocalDate.parse("1997-04-20"),
                    description = "description",
                    sex = Sex.MALE,
                    phoneNumber = "123 123 4234",
                    email = "123123@hmail.com")
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