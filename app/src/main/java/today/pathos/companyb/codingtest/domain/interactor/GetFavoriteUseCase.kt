package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository
import java.util.*
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val favoriteRepo: FavoriteRepository
) : BaseUseCase {
    fun execute(keyword: String): Single<Result<List<GithubUser>>> {
        var lastIndex = ""

        return favoriteRepo.getFavoriteList(keyword)
            .concatMap { result -> result.sortedBy { it.nickname.toUpperCase(Locale.ROOT) }.toDataSingle() }
            .flattenAsObservable { it }

            // header 여부 처리
            .map {
                if(lastIndex != it.index) {
                    lastIndex = it.index
                    it.isHeader = true
                }
                it
            }

            // 결과처리
            .toList()
            .concatMap { it.toResultSingle() }
            .onErrorReturn { it.toErrorResult() }
    }
}