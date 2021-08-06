package com.example.farmer.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmer.data.network.responses.AllProductsResponse
import com.example.farmer.data.network.responses.FeedbackResponse
import com.example.farmer.data.network.responses.ProductsResponseItem
import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.ui.farmer.FarmerProductsAdapter
import com.example.farmer.ui.customer.ProductsAdapter


///**
// * Methods for Customer Product list From Api
// */
//@BindingAdapter("productsList")
//fun bindListingRecyclerview(recyclerView: RecyclerView, data: List<Listing>?){
//    val adapter = recyclerView.adapter as ProductsAdapter
//    adapter.submitList(data)
//}
//
//@BindingAdapter("productName")
//fun TextView.setProductName(item: Listing){
//    item.let {
//        text = item.product_Name
//    }
//}
//
//@BindingAdapter("productQuality")
//fun TextView.setProductQuality(item: Listing){
//    item.let {
//        text = item.product_Quality
//    }
//}
//@BindingAdapter("productUnit")
//fun TextView.setProductUnit(item: Listing){
//    item.let {
//        text = item.product_Unit
//    }
//}
//@BindingAdapter("productPrice")
//fun TextView.setProductPrice(item: Listing){
//    item.let {
//        text = item.product_Price
//    }
//}
//@BindingAdapter("farmerName")
//fun TextView.setFarmerName(item: Listing){
//    item.let {
//        text = item.farmer_Name
//    }
//}
//@BindingAdapter("productDistrict")
//fun TextView.setProductDistrict(item: Listing){
//    item.let {
//        text = item.product_District
//    }
//}
//@BindingAdapter("productState")
//fun TextView.setProductState(item: Listing){
//    item.let {
//        text = item.product_State
//    }
//}
//@BindingAdapter("productStock")
//fun TextView.setProductStock(item: Listing){
//    item.let {
//        text = item.product_Stock
//    }
//}

/**
 * Methods for Farmer Product list
 */

@BindingAdapter("farmerList")
fun bindFarmerListRecyclerview(recyclerView: RecyclerView, data: List<ProductsResponseItem>?){
    val adapter = recyclerView.adapter as FarmerProductsAdapter
    adapter.submitList(data)
}

@BindingAdapter("productName")
fun TextView.setProductName(item: ProductsResponseItem){
    item.let {
        text = item.title
    }
}

@BindingAdapter("productQuality")
fun TextView.setProductQuality(item: ProductsResponseItem){
    item.let {
        text = item.quality
    }
}
@BindingAdapter("productStatus")
fun TextView.setProductStatus(item: ProductsResponseItem){
    item.let {
        text = item.status
    }
}
@BindingAdapter("productPrice")
fun TextView.setProductPrice(item: ProductsResponseItem){
    item.let {
        text = item.price
    }
}

@BindingAdapter("productStock")
fun TextView.setProductStock(item: ProductsResponseItem){
    item.let {
        text = item.stock
    }
}

@BindingAdapter("productUnit")
fun TextView.setProductUnit(item: ProductsResponseItem){
    item.let {
        text = item.unit
    }
}

/**
 * Methods for Customer Product list From SQLite
 */

@BindingAdapter("productsList")
fun bindCustomerProductRecyclerview(recyclerView: RecyclerView, data: List<AllProductsResponse>?){
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)
}

@BindingAdapter("productName")
fun TextView.setProductName(item: AllProductsResponse){
    item.let {
        text = item.title
    }
}

@BindingAdapter("productQuality")
fun TextView.setProductQuality(item: AllProductsResponse){
    item.let {
        text = item.quality
    }
}
@BindingAdapter("productUnit")
fun TextView.setProductUnit(item: AllProductsResponse){
    item.let {
        text = item.unit
    }
}
@BindingAdapter("productPrice")
fun TextView.setProductPrice(item: AllProductsResponse){
    item.let {
        text = item.price
    }
}
@BindingAdapter("farmerName")
fun TextView.setFarmerName(item: AllProductsResponse){
    item.let {
        text = item.name
    }
}
@BindingAdapter("productDistrict")
fun TextView.setProductDistrict(item: AllProductsResponse){
    item.let {
        text = item.address
    }
}
@BindingAdapter("productState")
fun TextView.setProductState(item: AllProductsResponse){
    item.let {
        text = item.state
    }
}
@BindingAdapter("productStock")
fun TextView.setProductStock(item: AllProductsResponse){
    item.let {
        text = item.stock
    }
}

@BindingAdapter("feedbackName")
fun TextView.setFeedbackName(item: FeedbackResponse){
    item.let {
        text = item.name
    }
}
@BindingAdapter("feedbackComment")
fun TextView.setFeedbackComment(item: FeedbackResponse){
    item.let {
        text = item.comment
    }
}

