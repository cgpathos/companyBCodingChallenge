package today.pathos.companyb.codingtest.domain.interactor

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

class IsFavoriteUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var repository: FavoriteRepository

    private lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @Before
    fun setUp() {
        isFavoriteUseCase = IsFavoriteUseCase(repository)
    }

    @Test
    fun testIsFavorite() {
        val user = GithubUser("DUMMY_URL", "김리무버")
        val expectResult = Result.OnSuccess(true)

        given(repository.isInFavorite(user)).willReturn(Single.just(true))

        isFavoriteUseCase.execute(user)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult }
            .assertComplete()
            .dispose()
    }

    @Test
    fun testIsNotFavorite() {
        val user = GithubUser("DUMMY_URL", "김리무버", "ㄱ")
        val expectResult = Result.OnSuccess(false)

        given(repository.isInFavorite(user)).willReturn(Single.just(false))

        isFavoriteUseCase.execute(user)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult }
            .assertComplete()
            .dispose()
    }
}