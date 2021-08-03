package com.example.farmer.ui.farmer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmer.data.network.responses.ProductResponse
import com.example.farmer.data.room.entities.Products

import com.example.farmer.databinding.RecyclerViewItemFarmerProductsBinding


class FarmerProductsAdapter(
//    private val onClickListener: OnClickListener? = null
) : ListAdapter<ProductResponse, FarmerProductsAdapter.ListingViewHolder>(DiffCallback) {
    /**
     * The ListingViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Products] information.
     */
    class ListingViewHolder(private var binding: RecyclerViewItemFarmerProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
//            call: OnClickListener,
            listing: ProductResponse
        ) {
            binding.itemList = listing
//            binding.callClickListener = call
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ListingViewHolder {
                val view = LayoutInflater.from(parent.context)
                val binding = RecyclerViewItemFarmerProductsBinding.inflate(view, parent, false)
                return ListingViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ListingResponse]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ProductResponse>() {
        override fun areItemsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductResponse, newItem: ProductResponse): Boolean {
            return true
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingViewHolder {
        return ListingViewHolder.from(parent)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener?.onClick(listing)
//        }
        holder.bind(listing)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Products]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Products]
     */
    class OnClickListener(val clickListener: (products: ProductResponse) -> Unit) {
        fun onClick(products: ProductResponse) = clickListener(products)
    }
}
