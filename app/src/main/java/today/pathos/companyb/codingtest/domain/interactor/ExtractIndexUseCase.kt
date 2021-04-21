package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.core.util.IndexUtil
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import javax.inject.Inject

/**
 * index 추출 UseCase
 */
class ExtractIndexUseCase @Inject constructor(
    private val indexUtil: IndexUtil
) : BaseUseCase {
    fun execute(githubUser: GithubUser): Single<Result<GithubUser>> {
        return Single.just(githubUser)
            .map { it.copy(index = indexUtil.extractIndex(it.nickname)) }
            .map { it.toResult() }
            .onErrorReturn { it.toErrorResult() }
    }
}