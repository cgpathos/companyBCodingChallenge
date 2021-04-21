package today.pathos.companyb.codingtest.presentation.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.editorActionEvents
import dagger.hilt.android.AndroidEntryPoint
import today.pathos.companyb.codingtest.R
import today.pathos.companyb.codingtest.core.extension.addTo
import today.pathos.companyb.codingtest.core.presentation.BaseFragment
import today.pathos.companyb.codingtest.databinding.FragmentLocalBinding
import today.pathos.companyb.codingtest.presentation.view.adapter.GithubUserAdapter
import today.pathos.companyb.codingtest.presentation.viewmodel.MainViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LocalFragment : BaseFragment<FragmentLocalBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() = LocalFragment()
    }

    override val layoutId = R.layout.fragment_local

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var listAdapter: GithubUserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bnd.lifecycleOwner = this
        bnd.vm = viewModel

        viewModel.searchFavoriteUser()

        bnd.etSearch.editorActionEvents()
            .subscribe {
                if (it.actionId == EditorInfo.IME_ACTION_SEARCH) {
                    favoriteUserSearch()
                }
            }
            .addTo(bag)

        bnd.btnSearch.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe { favoriteUserSearch() }
            .addTo(bag)

        listAdapter = GithubUserAdapter()
        bnd.listUser.adapter = listAdapter

        listAdapter.clickSubject
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe { viewModel.delFavorite(it) }
            .addTo(bag)
    }

    private fun favoriteUserSearch() {
        viewModel.searchFavoriteUser(bnd.etSearch.text.toString())
        (activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            bnd.etSearch.windowToken,
            0
        )
        bnd.etSearch.clearFocus()
        bnd.layoutRoot.requestFocus()
    }
}