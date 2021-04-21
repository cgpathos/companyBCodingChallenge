package today.pathos.companyb.codingtest.core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    abstract val layoutId: Int
    protected lateinit var bnd: T

    internal val bag = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bnd = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return bnd.root
    }

    override fun onDestroy() {
        if (!bag.isDisposed) {
            bag.dispose()
        }

        super.onDestroy()
    }
}