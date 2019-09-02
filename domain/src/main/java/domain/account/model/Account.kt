package domain.account.model

import domain.account.model.user.User
import org.joda.time.LocalDate

data class Login(val value: String)
data class Password(val value: String)
data class CreationDate(val date: LocalDate)

// Todo: add it if everything works
enum class State {
    ACTIVE, INACTIVE, DELETED
}
data class Account(val login: Login,
                   val password: Password,
                   val user: User)
