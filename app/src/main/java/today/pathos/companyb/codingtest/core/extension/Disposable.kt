package today.pathos.companyb.codingtest.core.extension

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(bag: CompositeDisposable) {
    bag.add(this)
}