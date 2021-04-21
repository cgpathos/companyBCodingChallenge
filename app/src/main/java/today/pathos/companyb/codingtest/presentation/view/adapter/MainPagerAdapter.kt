package today.pathos.companyb.codingtest.presentation.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import today.pathos.companyb.codingtest.presentation.view.APIFragment
import today.pathos.companyb.codingtest.presentation.view.LocalFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            APIFragment.newInstance()
        } else {
            LocalFragment.newInstance()
        }
    }
}