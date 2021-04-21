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
import today.pathos.companyb.codingtest.core.domain.toResultSingle
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.FavoriteRepository

class AddFavoriteUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @Mock
    private lateinit var repository: FavoriteRepository

    private lateinit var getFavoriteUseCase: GetFavoriteUseCase
    private lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Before
    fun setUp() {
        addFavoriteUseCase = AddFavoriteUseCase(isFavoriteUseCase, repository)
        getFavoriteUseCase = GetFavoriteUseCase(repository)
    }

    @Test
    fun testAddFavorite() {
        val favoriteList = arrayListOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", true),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", true),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", true),
        )
        val newFavorite = GithubUser("DUMMY_URL1", "김리무버", "ㄱ", true)
        val expectResult = listOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL1", "김리무버", "ㄱ", isFavorite = true, isHeader = false),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", isFavorite = true, isHeader = false),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", isFavorite = true, isHeader = true),
        )

        given(isFavoriteUseCase.execute(newFavorite)).willReturn(isInList(favoriteList, newFavorite).toResultSingle())
        given(repository.addFavorite(newFavorite))
            .willReturn(Completable.fromAction { favoriteList.add(1, newFavorite) }
                .toSingle { newFavorite })
        given(repository.getFavoriteList("")).willReturn(Single.just(favoriteList))

        addFavoriteUseCase.execute(newFavorite)
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(newFavorite) }
            .assertComplete()
            .dispose()

        getFavoriteUseCase.execute("")
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(expectResult) }
            .assertComplete()
            .dispose()
    }

    @Test
    fun testPreventMultipleAdd() {
        val favoriteList = arrayListOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", true),
            GithubUser("DUMMY_URL1", "김리무버", "ㄱ", true),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", true),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", true),
        )
        val newFavorite = GithubUser("DUMMY_URL3", "잘생김", "ㅈ", true)
        val expectResult = listOf(
            GithubUser("DUMMY_URL2", "김리", "ㄱ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL1", "김리무버", "ㄱ", isFavorite = true, isHeader = false),
            GithubUser("DUMMY_URL5", "김수한무", "ㄱ", isFavorite = true, isHeader = false),
            GithubUser("DUMMY_URL7", "되세김질", "ㄷ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL6", "목넘김", "ㅁ", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL3", "잘생김", "ㅈ", isFavorite = true, isHeader = true),
        )

        given(isFavoriteUseCase.execute(newFavorite)).willReturn(isInList(favoriteList, newFavorite).toResultSingle())
        given(repository.addFavorite(newFavorite))
            .willReturn(Completable.fromAction { favoriteList.add(newFavorite) }
                .toSingle { newFavorite })
        given(repository.getFavoriteList("")).willReturn(Single.just(favoriteList))

        addFavoriteUseCase.execute(newFavorite)
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(newFavorite) }
            .assertComplete()
            .dispose()

        // 같은 데이터 두번 추가 시도
        given(isFavoriteUseCase.execute(newFavorite)).willReturn(isInList(favoriteList, newFavorite).toResultSingle())
        addFavoriteUseCase.execute(newFavorite)
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(newFavorite) }
            .assertComplete()
            .dispose()

        getFavoriteUseCase.execute("")
            .test()
            .assertNoErrors()
            .assertValue { it == Result.OnSuccess(expectResult) }
            .assertComplete()
            .dispose()
    }

    private fun isInList(list: List<GithubUser>, user: GithubUser): Boolean {
        return list.firstOrNull { it.nickname == user.nickname } != null
    }
}
