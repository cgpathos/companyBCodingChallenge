package today.pathos.companyb.codingtest.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import today.pathos.companyb.codingtest.core.domain.toResultSingle
import today.pathos.companyb.codingtest.core.rx.RxSchedulerRule
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.interactor.AddFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.DelFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.GetFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.SearchGitUserUseCase

class MainViewModelTest {
    companion object {
        const val SEARCH_KEYWORD = "DUMMY_KEYWORD"
    }

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    @Mock
    lateinit var searchUseCase: SearchGitUserUseCase

    @Mock
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    @Mock
    lateinit var delFavoriteUseCase: DelFavoriteUseCase

    @Mock
    lateinit var getFavoriteUseCase: GetFavoriteUseCase

    @Mock
    lateinit var viewStateObserver: Observer<MainViewState>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycle: Lifecycle
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel = MainViewModel(
            searchUseCase,
            addFavoriteUseCase,
            delFavoriteUseCase,
            getFavoriteUseCase,
        )
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun `?????? ????????? ViewState ?????? ?????????`() {
        val listUser = listOf(
            GithubUser("DUMMY_URL2", "??????", "???", isFavorite = false, isHeader = true),
            GithubUser("DUMMY_URL1", "????????????", "???", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL4", "??????", "???", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL5", "????????????", "???", isFavorite = false, isHeader = false),
            GithubUser("DUMMY_URL7", "????????????", "???", isFavorite = true, isHeader = true),
            GithubUser("DUMMY_URL6", "?????????", "???", isFavorite = false, isHeader = true),
            GithubUser("DUMMY_URL3", "?????????", "???", isFavorite = true, isHeader = true),
        )
        BDDMockito.given(searchUseCase.execute(SEARCH_KEYWORD)).willReturn(listUser.toResultSingle())

        viewModel.searchUser(SEARCH_KEYWORD)
        Mockito.verify(viewStateObserver).onChanged(MainViewState.Loading)
        Mockito.verify(viewStateObserver).onChanged(MainViewState.Success)
    }

}