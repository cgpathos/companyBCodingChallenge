package today.pathos.companyb.codingtest.domain.interactor

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import today.pathos.companyb.codingtest.core.domain.Result
import today.pathos.companyb.codingtest.core.util.IndexUtil
import today.pathos.companyb.codingtest.domain.entity.GithubUser

class ExtractIndexUseCaseTest {
    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    private lateinit var indexUtil: IndexUtil
    private lateinit var extractIndexUseCase: ExtractIndexUseCase

    @Before
    fun setUp() {
        indexUtil = IndexUtil()
        extractIndexUseCase = ExtractIndexUseCase(indexUtil)
    }

    @Test
    fun testExtractKoreanIndex() {
        val user = GithubUser("DUMMY_URL", "김리무버")
        val expectResult = Result.OnSuccess(GithubUser("DUMMY_URL", "김리무버", "ㄱ"))

        extractIndexUseCase.execute(user)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult }
            .assertComplete()
            .dispose()
    }

    @Test
    fun testExtractEnglishIndex() {
        val user = GithubUser("DUMMY_URL", "robert john")
        val expectResult = Result.OnSuccess(GithubUser("DUMMY_URL", "robert john", "R"))

        extractIndexUseCase.execute(user)
            .test()
            .assertNoErrors()
            .assertValue { it == expectResult }
            .assertComplete()
            .dispose()
    }
}