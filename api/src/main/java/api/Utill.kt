package api

import arrow.core.Either
import arrow.core.Right
import arrow.core.left


fun <T, R> R?.toEither(f: () -> T) = when (this) {
    null -> f().left()
    else -> Right(this)
}