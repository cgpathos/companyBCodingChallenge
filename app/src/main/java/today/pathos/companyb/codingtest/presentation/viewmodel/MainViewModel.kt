package today.pathos.companyb.codingtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import today.pathos.companyb.codingtest.BuildConfig
import today.pathos.companyb.codingtest.core.domain.Result
import today.pathos.companyb.codingtest.core.domain.toDataSingle
import today.pathos.companyb.codingtest.core.extension.addTo
import today.pathos.companyb.codingtest.core.extension.defaultScheduler
import today.pathos.companyb.codingtest.core.presentation.BaseViewModel
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.domain.interactor.AddFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.DelFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.GetFavoriteUseCase
import today.pathos.companyb.codingtest.domain.interactor.SearchGitUserUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchGitUserUseCase: SearchGitUserUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val delFavoriteUseCase: DelFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
) : BaseViewModel() {
    val viewState = MutableLiveData<MainViewState>()
    val searchResult = MutableLiveData<List<GithubUser>>()
    val favorite = MutableLiveData<List<GithubUser>>()

    private val searchUserList: ArrayList<GithubUser> = ArrayList()

    init {
        searchResult.value = emptyList()
        favorite.value = emptyList()
    }

    fun searchUser(keyword: String) {
        searchGitUserUseCase.execute(keyword)
            .defaultScheduler()
            .doOnSubscribe { viewState.value = MainViewState.Loading }
            .doOnSuccess { viewState.value = MainViewState.Success }
            .subscribe({
                when (it) {
                    is Result.OnSuccess -> handleSearchSuccess(it.data)
                    is Result.OnError -> handleError(it.error.cause)
                }
            }, { handleError(it) })
            .addTo(bag)
    }

    private fun handleSearchSuccess(data: List<GithubUser>) {
        searchUserList.clear()
        searchUserList.addAll(data)
        searchResult.value = searchUserList
    }

    fun searchFavoriteUser(keyword: String = "") {
        getFavoriteUseCase.execute(keyword)
            .defaultScheduler()
            .doOnSubscribe { viewState.value = MainViewState.Loading }
            .doOnSuccess { viewState.value = MainViewState.Success }
            .subscribe({
                when (it) {
                    is Result.OnSuccess -> handleFavoriteSearchSuccess(it.data)
                    is Result.OnError -> handleError(it.error.cause)
                }
            }, { handleError(it) })
            .addTo(bag)
    }

    private fun handleFavoriteSearchSuccess(data: List<GithubUser>) {
        favorite.value = data
    }

    fun toggleFavorite(githubUser: GithubUser) {
        if(githubUser.isFavorite) {
            delFavorite(githubUser)
        }
        else {
            addFavorite(githubUser)
        }
    }

    fun addFavorite(githubUser: GithubUser) {
        addFavoriteUseCase.execute(githubUser)
            .defaultScheduler()
            .doOnSuccess { viewState.value = MainViewState.SuccessAddFavorite }
            .concatMap { it.toDataSingle() }
            .startWith(Completable.fromAction { viewState.value = MainViewState.Loading })
//            .onErrorReturn { it.toErrorResult() }
            .subscribe { updateList(it) }
            .addTo(bag)
    }

    fun delFavorite(githubUser: GithubUser) {
        delFavoriteUseCase.execute(githubUser)
            .defaultScheduler()
            .doOnSuccess { viewState.value = MainViewState.SuccessDelFavorite }
            .concatMap { it.toDataSingle() }
            .startWith(Completable.fromAction { viewState.value = MainViewState.Loading })
            .subscribe { updateList(it) }
            .addTo(bag)
    }

    private fun updateList(githubUser: GithubUser) {
        searchUserList.find { it.nickname == githubUser.nickname }?.isFavorite = githubUser.isFavorite
        searchResult.value = searchUserList
        searchFavoriteUser()
    }

    private fun handleError(error: Throwable) {
        viewState.value = MainViewState.Error(error)
        if (BuildConfig.DEBUG) {
            error.printStackTrace()
        }
    }
}