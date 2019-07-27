package account.validation

import account.model.Account
import account.model.Password
import account.model.user.User
import account.model.user.info.Email
import account.model.user.info.PhoneNumber
import arrow.core.*
import exceptions.DomainError
import exceptions.ValidationError


fun Account.validateAccount(): Either<DomainError, Account> {

    return password.validate()
            .flatMap { user.validate() }
            .map { this }
}


fun Password.validate(): Either<ValidationError, Password> = when (value.length) {
    in 0..5 -> ValidationError("Password should contain min 6 chars").left()
    else -> this.right()
}

fun User.validate(): Either<ValidationError, User> {
    val emailValidation = this.contactInfo.email?.validate()
    val phoneNumberValidation = this.contactInfo.phoneNumber?.validate()

    return when (phoneNumberValidation) {
        is Either.Left -> phoneNumberValidation
        else ->
            when (emailValidation) {
                is Either.Left -> emailValidation
                else -> Right(this)


            }
    }

}


fun Email.validate(): Either<ValidationError, Email> = when {
    value.matches(".+@.+".toRegex()) -> Right(this)
    else -> Left(ValidationError("Email should contains @ and . after that"))
}

fun PhoneNumber.validate(): Either<ValidationError, PhoneNumber> = when {
    number.matches("[0-9]+".toRegex()) -> Right(this)
    else -> Left(ValidationError("Phone Number should contains only digits"))
}



