package today.pathos.companyb.codingtest.domain.interactor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companyb.codingtest.core.domain.Result
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository

class DelFavoriteUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var repository: FavoriteRepository

    private lateinit var getFavoriteUseCase: GetFavoriteUseCase
    private lateinit var delFavoriteUseCase: DelFavoriteUseCase

    @Before
    fun setUp() {
        delFavoriteUseCase = DelFavoriteUseCase(repository)
        getFavoriteUseCase = GetFavoriteUseCase(repository)
    }

    @Test
    fun testDelFavorite() {
        val favoriteList = arrayListOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", true),
            GithubUser("DUMMY_URL1", "김리무버", "ㄱ", true),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", true),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", true),
        )
        val targetFavorite = GithubUser("DUMMY_URL1", "김리무버", "ㄱ", true)
        val expectResult = listOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", isFavorite = true, isHeader = false),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", isFavorite = true, isHeader = true),
        )
        given(repository.delFavorite(targetFavorite))
            .willReturn(Completable.fromAction { favoriteList.remove(targetFavorite) }
                .toSingle { targetFavorite })
        given(repository.getFavoriteList("")).willReturn(Single.just(favoriteList))

        delFavoriteUseCase.execute(targetFavorite)
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(targetFavorite) }
            .assertComplete()
            .dispose()

        getFavoriteUseCase.execute("")
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(expectResult) }
            .assertComplete()
            .dispose()
    }

}
