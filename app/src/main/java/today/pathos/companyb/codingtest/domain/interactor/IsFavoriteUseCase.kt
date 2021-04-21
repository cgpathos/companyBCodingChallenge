package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.BaseUseCase
import today.pathos.companyb.codingtest.core.domain.Result
import today.pathos.companyb.codingtest.core.domain.toErrorResult
import today.pathos.companyb.codingtest.core.domain.toResultSingle
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

/**
 * 즐겨찾기 등록 여부 검사 UseCase
 */
class IsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase {
    fun execute(githubUser: GithubUser): Single<Result<Boolean>> {
        return favoriteRepository.isInFavorite(githubUser)
            .concatMap { it.toResultSingle() }
            .onErrorReturn { it.toErrorResult() }
    }
}