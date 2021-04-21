package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class DelFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : BaseUseCase {
    fun execute(githubUser: GithubUser): Single<Result<GithubUser>> {
        return favoriteRepository.delFavorite(githubUser)
            .concatMap { it.toResultSingle() }
            .onErrorReturn { it.toErrorResult() }
    }
}