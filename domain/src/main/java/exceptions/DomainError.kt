package exceptions


sealed class DomainError
data class ValidationError(val msg: String) : DomainError()
data class SavingError(val msg: String) : DomainError()
data class UpdateError(val msg: String) : DomainError()
data class DeleteError(val msg: String) : DomainError()
