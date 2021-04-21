package today.pathos.companyb.codingtest.data.remote

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import today.pathos.companyb.codingtest.data.remote.dto.DtoResult

interface GithubUserService {

    @GET("search/users")
    fun searchUser(
        @Query("q") keyword: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1,
    ): Single<DtoResult>
}