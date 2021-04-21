package today.pathos.companyb.codingtest.presentation.view.adapter

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.Subject
import today.pathos.companyb.codingtest.databinding.ItemUserBinding
import today.pathos.companyb.codingtest.domain.entity.GithubUser

class GithubUserItemViewHolder(
    private val bnd: ItemUserBinding,
    private val clickSubject: Subject<GithubUser>
) :
    RecyclerView.ViewHolder(bnd.root) {
    fun bind(data: GithubUser) {
        bnd.githubUser = data

        itemView.setOnLongClickListener {
            clickSubject.onNext(data)
            true
        }

        bnd.executePendingBindings()
    }
}