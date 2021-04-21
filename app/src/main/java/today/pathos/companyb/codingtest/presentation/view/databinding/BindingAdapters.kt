package today.pathos.companyb.codingtest.presentation.view.databinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import today.pathos.companyb.codingtest.R
import today.pathos.companyb.codingtest.domain.entity.GithubUser
import today.pathos.companyb.codingtest.presentation.view.adapter.GithubUserAdapter


@BindingAdapter("app:listData", requireAll = true)
fun setRecyclerViewData(recyclerView: RecyclerView, data: List<GithubUser>) {
    val adapter = recyclerView.adapter as GithubUserAdapter
    adapter.githubUserList = data
}

@BindingAdapter("app:isHeader", requireAll = true)
fun isHeader(textView: TextView, isHeader: Boolean) {
    textView.visibility = if (isHeader) View.VISIBLE else View.GONE
}

@BindingAdapter("app:isFavorite", requireAll = true)
fun isFavorite(imageView: ImageView, isFavorite: Boolean) {
    imageView.setImageResource(if (isFavorite) R.drawable.ic_star_48 else R.drawable.ic_star_border_48)
}

@BindingAdapter("android:src", requireAll = true)
fun setSrc(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
        .load(imageUrl)
        .circleCrop()
        .into(imageView)
}