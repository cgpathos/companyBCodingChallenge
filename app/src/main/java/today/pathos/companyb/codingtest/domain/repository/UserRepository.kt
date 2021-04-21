package today.pathos.companyb.codingtest.domain.repository

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.domain.entity.GithubUser

interface UserRepository {
    fun searchUser(keyword: String): Single<List<GithubUser>>
}