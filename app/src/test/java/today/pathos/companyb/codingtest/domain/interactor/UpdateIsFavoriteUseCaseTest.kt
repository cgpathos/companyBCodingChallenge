package today.pathos.companyb.codingtest.domain.interactor

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import today.pathos.companyb.codingtest.core.domain.toResult
import today.pathos.companyb.codingtest.core.domain.toResultSingle
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.repository.UserRepository

class UpdateIsFavoriteUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Mock
    private lateinit var userRepo: UserRepository

    @Mock
    private lateinit var isFavoriteUseCase: IsFavoriteUseCase

    private lateinit var updateIsFavoriteUseCase: UpdateIsFavoriteUseCase

    @Before
    fun setUp() {
        updateIsFavoriteUseCase = UpdateIsFavoriteUseCase(isFavoriteUseCase)
    }

    @Test
    fun testUpdateIsFavorite() {
        val favoriteUser = GithubUser("DUMMY_URL3", "잘생김")
        val expectResult = GithubUser("DUMMY_URL3", "잘생김", "", true)

        given(isFavoriteUseCase.execute(favoriteUser)).willReturn(true.toResultSingle())

        updateIsFavoriteUseCase.execute(favoriteUser)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult.toResult() }
            .assertComplete()
            .dispose()
    }
}