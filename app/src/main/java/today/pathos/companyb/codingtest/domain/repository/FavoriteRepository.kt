package today.pathos.companyb.codingtest.domain.repository

import io.reactivex.rxjava3.core.Single
import today.pathos.companyb.codingtest.domain.entity.GithubUser

interface FavoriteRepository {
    fun getFavoriteList(keyword: String = ""): Single<List<GithubUser>>
    fun addFavorite(user: GithubUser): Single<GithubUser>
    fun delFavorite(user: GithubUser): Single<GithubUser>
    fun isInFavorite(user: GithubUser): Single<Boolean>
}