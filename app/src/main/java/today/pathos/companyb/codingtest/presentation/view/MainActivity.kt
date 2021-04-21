package today.pathos.companyb.codingtest.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import today.pathos.companyb.codingtest.R
import today.pathos.companyb.codingtest.core.extension.toastIt
import today.pathos.companyb.codingtest.core.presentation.BaseActivity
import today.pathos.companyb.codingtest.databinding.ActivityMainBinding
import today.pathos.companyb.codingtest.presentation.view.adapter.MainPagerAdapter
import today.pathos.companyb.codingtest.presentation.viewmodel.MainViewModel
import today.pathos.companyb.codingtest.presentation.viewmodel.MainViewState

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId = R.layout.activity_main

    private val viewModel: MainViewModel by viewModels()
    private lateinit var pagerAdapter: MainPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        viewModel.viewState.observe(this, { handleViewState(it) })

        pagerAdapter = MainPagerAdapter(this)
        bnd.pagerMain.adapter = pagerAdapter

        TabLayoutMediator(bnd.tabMain, bnd.pagerMain) { tab, position ->
            if (position == 0) {
                tab.text = getString(R.string.main_tab_api)
            } else {
                tab.text = getString(R.string.main_tab_local)
            }
        }.attach()
    }

    private fun handleViewState(viewState: MainViewState) {
        when (viewState) {
            is MainViewState.Loading -> {
                bnd.layoutLoading.visibility = View.VISIBLE
            }
            is MainViewState.Success -> {
                bnd.layoutLoading.visibility = View.GONE
            }
            is MainViewState.SuccessAddFavorite -> {
                bnd.layoutLoading.visibility = View.GONE
                toastIt(getString(R.string.main_add_favorite_msg))
            }
            is MainViewState.SuccessDelFavorite -> {
                bnd.layoutLoading.visibility = View.GONE
                toastIt(getString(R.string.main_del_favorite_msg))
            }
            is MainViewState.Error -> {
                bnd.layoutLoading.visibility = View.GONE
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage(viewState.error.message ?: getString(R.string.main_unknown_error_msg))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.main_alert_confirm), null)
                    .show()
            }
            is MainViewState.FinishApp -> {
                bnd.layoutLoading.visibility = View.GONE
                finish()
            }
        }
    }
}