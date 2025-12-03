package com.example.vendoapp.data.model.cartModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("productName")
    val productName: String = "",

    @SerializedName("productPrice")
    val productPrice: Double = 0.0,

    @SerializedName("quantity")
    val quantity: Int = 0,

    @SerializedName("color")
    val color: String? = null,

    @SerializedName("size")
    val size: String? = null,

    @SerializedName("discountPrice")
    val discountPrice: Double? = null,

    @SerializedName("status")
    val status: String = "ACTIVE",

    @SerializedName("totalPrice")
    val totalPrice: Double = 0.0,

    @SerializedName("selectionStatus")
    val selectionStatus: String = "SELECTED",

    @SerializedName("image")
    val image: String? = null
) : Parcelable {
    fun isSelected(): Boolean = selectionStatus.equals("SELECTED", ignoreCase = true)
}

data class CartSummary(
    @SerializedName("items")
    val items: List<CartItem>,

    @SerializedName("itemsCount")
    val itemsCount: Int,

    @SerializedName("subtotal")
    val subtotal: Double,

    @SerializedName("discount")
    val discount: Double,

    @SerializedName("shipping")
    val shipping: Double,

    @SerializedName("total")
    val total: Double
)

data class CartItemRequest(
    val quantity: Int,
    val color: String? = null,
    val size: String? = null
)
