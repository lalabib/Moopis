package com.latihan.lalabib.moopis.ui.detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihan.lalabib.moopis.R
import com.latihan.lalabib.moopis.data.remote.response.ReviewsEntity
import com.latihan.lalabib.moopis.databinding.ItemReviewsBinding
import com.latihan.lalabib.moopis.utils.IMG_URL

class ReviewAdapter : ListAdapter<ReviewsEntity, ReviewAdapter.ReviewViewHolder>(DIFFUTIL) {

    private object DIFFUTIL : DiffUtil.ItemCallback<ReviewsEntity>() {
        override fun areItemsTheSame(oldItem: ReviewsEntity, newItem: ReviewsEntity): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: ReviewsEntity, newItem: ReviewsEntity): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(ItemReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class ReviewViewHolder(private val binding: ItemReviewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(review: ReviewsEntity) {
                binding.apply {
                    tvName.text = review.author
                    tvRate.text = review.authorDetail?.rating
                    tvReview.text = review.review

                    Glide.with(itemView.context)
                        .load(IMG_URL + review.authorDetail?.avatarPath)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_avatar))
                        .into(ivAvatar)
                }
            }
    }
}