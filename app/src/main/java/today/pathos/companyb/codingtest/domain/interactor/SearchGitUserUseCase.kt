package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.core.domain.*
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.UserRepository
import java.util.*
import javax.inject.Inject

/**
 * 사용자 검색 UseCase
 */
class SearchGitUserUseCase @Inject constructor(
    private val userRepo: UserRepository,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase,
    private val extractIndexUseCase: ExtractIndexUseCase
) : BaseUseCase {
    fun execute(keyword: String): Single<Result<List<GithubUser>>> {
        var lastIndex = ""
        return userRepo.searchUser(keyword)
            .concatMap { result -> result.sortedBy { it.nickname.toUpperCase(Locale.ROOT) }.toDataSingle() }
            .flattenAsObservable { it }

            // 즐겨찾기 정보 업데이트
            .concatMapSingle { updateIsFavoriteUseCase.execute(it) }
            .concatMapSingle { it.toDataSingle() }

            // index 추출
            .concatMapSingle { extractIndexUseCase.execute(it) }
            .concatMapSingle { it.toDataSingle() }

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