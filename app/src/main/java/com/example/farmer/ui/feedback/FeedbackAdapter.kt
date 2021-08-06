package com.example.farmer.ui.feedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmer.data.network.responses.FeedbackResponse
import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.databinding.RecyclerViewItemFeedbackBinding


class FeedbackAdapter (): ListAdapter<FeedbackResponse, FeedbackAdapter.ListingViewHolder>(DiffCallback) {
    /**
     * The ListingViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [CustomerProducts] information.
     */
    class ListingViewHolder(private var binding: RecyclerViewItemFeedbackBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(

            listing: FeedbackResponse
        ) {
            binding.listing = listing
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListingViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewItemFeedbackBinding.inflate(view, parent, false)
                return ListingViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ListingResponse]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<FeedbackResponse>() {
        override fun areItemsTheSame(oldItem: FeedbackResponse, newItem: FeedbackResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FeedbackResponse, newItem: FeedbackResponse): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ListingViewHolder {
        return ListingViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing= getItem(position)
        holder.bind(listing)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [FeedbackResponse]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [FeedbackResponse]
     */
    class OnClickListener(val clickListener: (allProductsResponse: FeedbackResponse) -> Unit) {
        fun onClick(products: FeedbackResponse) = clickListener(products)
    }
}