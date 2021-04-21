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
import today.pathos.companyb.codingtest.core.extension.toastIt
import today.pathos.companyb.codingtest.core.presentation.BaseFragment
import today.pathos.companyb.codingtest.databinding.FragmentApiBinding
import today.pathos.companyb.codingtest.presentation.view.adapter.GithubUserAdapter
import today.pathos.companyb.codingtest.presentation.viewmodel.MainViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class APIFragment : BaseFragment<FragmentApiBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() = APIFragment()
    }

    override val layoutId = R.layout.fragment_api

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var listAdapter: GithubUserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bnd.lifecycleOwner = this
        bnd.vm = viewModel

        bnd.etSearch.editorActionEvents()
            .subscribe {
                if (it.actionId == EditorInfo.IME_ACTION_SEARCH) {
                    githubUserSearch()
                }
            }
            .addTo(bag)

        bnd.btnSearch.clicks()
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe { githubUserSearch() }
            .addTo(bag)

        listAdapter = GithubUserAdapter()
        bnd.listUser.adapter = listAdapter

        listAdapter.clickSubject
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .subscribe { viewModel.toggleFavorite(it) }
            .addTo(bag)
    }

    private fun githubUserSearch() {
        if (bnd.etSearch.text.toString().trim().isEmpty()) {
            activity?.toastIt(getString(R.string.main_search_hint))
            return
        }

        viewModel.searchUser(bnd.etSearch.text.toString())
        (activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            bnd.etSearch.windowToken,
            0
        )
        bnd.etSearch.clearFocus()
        bnd.layoutRoot.requestFocus()
    }
}