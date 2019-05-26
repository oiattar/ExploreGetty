package com.issa.omar.exploregetty.model

import com.google.gson.annotations.SerializedName

data class BusinessHours(
    val open: List<OpenHours>,
    val closed: List<OpenHours>,
    @SerializedName("hours_type")
    val hoursType: String,
    @SerializedName("is_open_now")
    val openNow: Boolean
)
