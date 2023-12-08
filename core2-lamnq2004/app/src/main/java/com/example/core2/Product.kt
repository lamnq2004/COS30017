package com.example.core2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val rating: Float,
    val attributes: List<String>,
    var price: Int,
    val imageID: Int,
    var rented: Boolean = false,
    var returnDate: String? = null,
) : Parcelable
