package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val favoriteRepository: FavoriteRepository,
) : BaseUseCase {
    fun execute(githubUser: GithubUser): Single<Result<GithubUser>> {
        return isFavoriteUseCase.execute(githubUser)
            .concatMap { it.toDataSingle() }
            .concatMap {
                when (it) {
                    true -> githubUser.toDataSingle()
                    false -> favoriteRepository.addFavorite(githubUser)
                }
            }
            .concatMap { it.toResultSingle() }
            .onErrorReturn { it.toErrorResult() }
    }


}