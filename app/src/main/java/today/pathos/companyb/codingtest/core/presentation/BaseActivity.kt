package today.pathos.companyb.codingtest.core.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutId: Int
    protected lateinit var bnd: T

    private val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bnd = DataBindingUtil.setContentView(this, layoutId)
    }

    override fun onDestroy() {
        if (!bag.isDisposed) {
            bag.dispose()
        }

        super.onDestroy()
    }
}