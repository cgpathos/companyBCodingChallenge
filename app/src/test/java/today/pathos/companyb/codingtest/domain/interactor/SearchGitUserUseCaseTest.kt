package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companyb.codingtest.core.domain.toResult
import today.pathos.companyb.codingtest.core.domain.toResultSingle
import today.pathos.companyb.codingtest.core.util.IndexUtil
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.UserRepository

class SearchGitUserUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var userRepo: UserRepository
    
    @Mock
    private lateinit var updateIsFavoriteUseCase: UpdateIsFavoriteUseCase

    private lateinit var extractIndexUseCase: ExtractIndexUseCase
    private lateinit var searchGitUserUseCase: SearchGitUserUseCase

    @Before
    fun setUp() {
        extractIndexUseCase = ExtractIndexUseCase(IndexUtil())
        searchGitUserUseCase =
            SearchGitUserUseCase(userRepo, updateIsFavoriteUseCase, extractIndexUseCase)
    }

    @Test
    fun testSearchUser() {
        val keyword = "김"
        val favoriteUser1 = GithubUser("DUMMY_URL3", "잘생김")
        val favoriteUser2 = GithubUser("DUMMY_URL7", "되세김질")
        val searchResult = listOf(
            GithubUser("DUMMY_URL1", "김리무버"),
            GithubUser("DUMMY_URL2", "김리"),
            favoriteUser1,
            GithubUser("DUMMY_URL4", "김밥"),
            GithubUser("DUMMY_URL5", "김수한무"),
            GithubUser("DUMMY_URL6", "목넘김"),
            favoriteUser2,
        )
        val expectResult = listOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", isFavorite = false, isHeader = true),
            GithubUser("DUMMY_URL1", "김리무버", "ㄱ", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL4", "김밥", "ㄱ", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", isFavorite = false, isHeader = true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", isFavorite = true, isHeader = true),
        )

        given(userRepo.searchUser(keyword)).willReturn(Single.just(searchResult))
        searchResult.forEach {
            if (it.nickname == "잘생김") {
                given(updateIsFavoriteUseCase.execute(it)).willReturn(favoriteUser1.apply { isFavorite = true }.toResultSingle())
            } else if (it.nickname == "되세김질") {
                given(updateIsFavoriteUseCase.execute(it)).willReturn(favoriteUser2.apply { isFavorite = true }.toResultSingle())
            } else {
                given(updateIsFavoriteUseCase.execute(it)).willReturn(it.toResultSingle())
            }
        }

        searchGitUserUseCase.execute(keyword)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult.toResult() }
            .assertComplete()
            .dispose()
    }
}