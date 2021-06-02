package com.example.farmer.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.farmer.data.room.entities.CustomerProducts
import com.example.farmer.data.room.entities.Products
import com.example.farmer.ui.farmer.FarmerAdapter
import com.example.farmer.ui.productslist.ProductsAdapter


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
fun bindFarmerListRecyclerview(recyclerView: RecyclerView, data: List<Products>?){
    val adapter = recyclerView.adapter as FarmerAdapter
    adapter.submitList(data)
}

@BindingAdapter("productName")
fun TextView.setProductName(item: Products){
    item.let {
        text = item.title
    }
}

@BindingAdapter("productQuality")
fun TextView.setProductQuality(item: Products){
    item.let {
        text = item.quality
    }
}
@BindingAdapter("productUnit")
fun TextView.setProductUnit(item: Products){
    item.let {
        text = item.unit
    }
}
@BindingAdapter("productPrice")
fun TextView.setProductPrice(item: Products){
    item.let {
        text = item.price
    }
}

@BindingAdapter("productStock")
fun TextView.setProductStock(item: Products){
    item.let {
        text = item.stock
    }
}

/**
 * Methods for Customer Product list From SQLite
 */

@BindingAdapter("productsList")
fun bindCustomerProductRecyclerview(recyclerView: RecyclerView, data: List<CustomerProducts>?){
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)
}

@BindingAdapter("productName")
fun TextView.setProductName(item: CustomerProducts){
    item.let {
        text = item.title
    }
}

@BindingAdapter("productQuality")
fun TextView.setProductQuality(item: CustomerProducts){
    item.let {
        text = item.quality
    }
}
@BindingAdapter("productUnit")
fun TextView.setProductUnit(item: CustomerProducts){
    item.let {
        text = item.unit
    }
}
@BindingAdapter("productPrice")
fun TextView.setProductPrice(item: CustomerProducts){
    item.let {
        text = item.price
    }
}
@BindingAdapter("farmerName")
fun TextView.setFarmerName(item: CustomerProducts){
    item.let {
        text = item.farmer_name
    }
}
@BindingAdapter("productDistrict")
fun TextView.setProductDistrict(item: CustomerProducts){
    item.let {
        text = item.district
    }
}
@BindingAdapter("productState")
fun TextView.setProductState(item: CustomerProducts){
    item.let {
        text = item.state
    }
}
@BindingAdapter("productStock")
fun TextView.setProductStock(item: CustomerProducts){
    item.let {
        text = item.stock
    }
}
