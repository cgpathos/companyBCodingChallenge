package today.pathos.companyb.codingtest.data.remote

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.UserRepository
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(
    private val githubUserService: GithubUserService,
    private val networkGithubUserMapper: NetworkGithubUserMapper
) : UserRepository {
    override fun searchUser(keyword: String): Single<List<GithubUser>> {
        return githubUserService.searchUser(convertQueryValue(keyword))
            .subscribeOn(Schedulers.io())
            .map { networkGithubUserMapper.map(it.items ?: emptyList()) }
    }

    private fun convertQueryValue(keyword: String): String {
        return "$keyword in:fullname"
    }
}


