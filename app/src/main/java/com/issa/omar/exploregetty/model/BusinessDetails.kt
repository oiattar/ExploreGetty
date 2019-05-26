package com.issa.omar.exploregetty.model

import com.google.gson.annotations.SerializedName

data class BusinessDetails(
    val id: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val url: String,
    @SerializedName("display_phone")
    val phone: String,
    @SerializedName("review_count")
    val reviewCount: Int,
    val categories: List<BusinessCategory>,
    val rating: Float,
    val location: BusinessLocation,
    val coordinates: BusinessCoordinates,
    val photos: List<String>,
    val price: String,
    val hours: List<BusinessHours>
)