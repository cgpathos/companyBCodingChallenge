package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import javax.inject.Inject

/**
 * 즐겨찾기 등록 여부 검사 UseCase
 */
class UpdateIsFavoriteUseCase @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase
) : BaseUseCase {
    fun execute(githubUser: GithubUser): Single<Result<GithubUser>> {
        return isFavoriteUseCase.execute(githubUser)
            .concatMap { it.toDataSingle() }
            .concatMap { githubUser.copy(isFavorite = it).toResultSingle() }
            .onErrorReturn { it.toErrorResult() }
    }
}