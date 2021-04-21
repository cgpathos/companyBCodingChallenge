package today.pathos.companyb.codingtest.core.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    internal val bag = CompositeDisposable()

    override fun onCleared() {
        if (!bag.isDisposed) {
            bag.dispose()
        }
    }
}