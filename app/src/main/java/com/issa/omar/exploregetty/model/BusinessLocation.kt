package com.issa.omar.exploregetty.model

import com.google.gson.annotations.SerializedName

data class BusinessLocation(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    @SerializedName("zip_code")
    val zipCode: String,
    val country: String,
    val state: String,
    @SerializedName("display_address")
    val displayAddress: List<String>,
    @SerializedName("cross_streets")
    val crossStreets: String
)