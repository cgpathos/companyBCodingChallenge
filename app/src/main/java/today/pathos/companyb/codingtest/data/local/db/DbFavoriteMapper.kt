package today.pathos.companyb.codingtest.data.local.db

import today.pathos.companyb.codingtest.domain.Mapper
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbFavoriteMapper @Inject constructor() : Mapper<FavoriteTable, GithubUser>() {
    override fun map(value: FavoriteTable): GithubUser {
        return GithubUser(value.profileUrl, value.nickname, value.index, true)
    }

    override fun reverseMap(value: GithubUser): FavoriteTable {
        return FavoriteTable(0, value.profileUrl, value.nickname, value.index)
    }
}