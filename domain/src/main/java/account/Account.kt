package account

import user.User
import java.time.LocalDate

data class Login(val value: String)
data class Password(val value: String)
data class CreationDate(val date: LocalDate)
enum class State {
    ACTIVE, INACTIVE, DELETED
}
data class Account(val login: Login,
                   val password: Password,
                   val creationDate: CreationDate,
                   val user: User,
                   val state: State)
