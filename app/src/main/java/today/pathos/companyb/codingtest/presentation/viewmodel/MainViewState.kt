package today.pathos.companyb.codingtest.presentation.viewmodel

sealed class MainViewState {
    object Loading: MainViewState()
    object Success: MainViewState()
    object SuccessAddFavorite: MainViewState()
    object SuccessDelFavorite: MainViewState()
    data class Error(val error: Throwable): MainViewState()
    object FinishApp: MainViewState()
}