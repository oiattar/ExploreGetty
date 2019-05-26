package com.issa.omar.exploregetty.model

import com.google.gson.annotations.SerializedName

data class OpenHours (
    @SerializedName("is_overnight")
    val isOvernight: Boolean,
    val start: String,
    val end: String,
    val day: Int
)