package com.example.farmer.ui.customer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmer.data.network.responses.AllProductsResponse

import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.databinding.RecyclerViewItemCustomerProductsBinding


class ProductsAdapter(private val onClickListener: OnClickListener,private val onClickListener1: OnClickListener,private val onClickListener3: OnClickListener) : ListAdapter<AllProductsResponse, ProductsAdapter.ListingViewHolder>(DiffCallback) {
    /**
     * The ListingViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [CustomerProducts] information.
     */
    class ListingViewHolder(private var binding: RecyclerViewItemCustomerProductsBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(
            call: OnClickListener,
            whats: OnClickListener,
            feedback: OnClickListener,
            listing: AllProductsResponse) {
            binding.listing = listing
            binding.callClickListener = call
            binding.whatsAppClickListener = whats
            binding.feedbackClickListener = feedback
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListingViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewItemCustomerProductsBinding.inflate(view, parent, false)
                return ListingViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ListingResponse]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<AllProductsResponse>() {
        override fun areItemsTheSame(oldItem: AllProductsResponse, newItem: AllProductsResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AllProductsResponse, newItem: AllProductsResponse): Boolean {
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
        holder.bind(onClickListener,onClickListener1,onClickListener3,listing)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [AllProductsResponse]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [AllProductsResponse]
     */
    class OnClickListener(val clickListener: (allProductsResponse: AllProductsResponse) -> Unit) {
        fun onClick(products:AllProductsResponse) = clickListener(products)
    }
}