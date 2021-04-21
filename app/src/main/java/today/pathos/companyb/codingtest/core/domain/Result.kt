package today.pathos.companyb.codingtest.core.domain

import io.reactivex.rxjava3.core.Single

sealed class Result<out T> {
    data class OnSuccess<out T>(val data: T) : Result<T>()
    data class OnError<out T>(val error: Error) : Result<T>()
}

data class Error(
    val message: String? = null,
    val cause: Throwable
)

// extension 함수는 ~core/extension/* 에 모두 모여있지만 하지만 가독성을 위해 예외로 함

fun Error.toThrowable(): Throwable {
    return this.cause
}

fun <T> Throwable.toErrorResult(): Result<T> {
    return Result.OnError(Error(message, this))
}

fun <T> T.toResult(): Result<T> {
    return Result.OnSuccess(this)
}

fun <T> T.toDataSingle(): Single<T> {
    return Single.just(this)
}

fun <T> T.toResultSingle(): Single<Result<T>> {
    return Single.just(Result.OnSuccess(this))
}

fun <T> Result<T>.toDataSingle(): Single<T> {
    return when (this) {
        is Result.OnSuccess -> Single.just(this.data)
        is Result.OnError -> Single.error(this.error.toThrowable())
    }
}