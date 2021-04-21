package today.pathos.companyb.codingtest.data.remote

import today.pathos.companyb.codingtest.data.remote.dto.DtoGithubUser
import today.pathos.companyb.codingtest.domain.Mapper
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkGithubUserMapper @Inject constructor() : Mapper<DtoGithubUser, GithubUser>() {
    override fun map(value: DtoGithubUser): GithubUser {
        return GithubUser(value.avatarUrl ?: "", value.login ?: "")
    }

    override fun reverseMap(value: GithubUser): DtoGithubUser {
        TODO("사용하지 않음")
    }
}