package today.pathos.companyb.codingtest.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import today.pathos.companyb.codingtest.R
import today.pathos.companyb.codingtest.databinding.ItemUserBinding
import today.pathos.companyb.codingtest.domain.entity.GithubUser

class GithubUserAdapter : RecyclerView.Adapter<GithubUserItemViewHolder>() {
    val clickSubject: Subject<GithubUser> = PublishSubject.create<GithubUser>().toSerialized()

    var githubUserList: List<GithubUser> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserItemViewHolder {
        val bnd = DataBindingUtil.inflate<ItemUserBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_user, parent, false
        )
        return GithubUserItemViewHolder(bnd, clickSubject)
    }

    override fun onBindViewHolder(holder: GithubUserItemViewHolder, position: Int) {
        holder.bind(githubUserList[position])
    }

    override fun getItemCount(): Int = githubUserList.size
}