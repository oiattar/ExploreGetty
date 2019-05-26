package com.issa.omar.exploregetty.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.issa.omar.exploregetty.R
import com.issa.omar.exploregetty.model.BusinessDetails
import com.issa.omar.exploregetty.model.OpenHours
import com.issa.omar.exploregetty.rest.YelpRepository

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val gettyId: String = "zRlDhJgcwXEphTUhMaCfyw"

    private val details: MutableLiveData<BusinessDetails> by lazy {
        MutableLiveData<BusinessDetails>().also {
            YelpRepository.getBusinessDetails(gettyId, it)
        }
    }
    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val rating: MutableLiveData<Float> by lazy {
        MutableLiveData<Float>()
    }
    val reviews: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val categories: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val address: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val phone: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var hours: List<OpenHours> = mutableListOf()
    var photos: List<String> = mutableListOf()
    var imageUrl: String = ""
    var url: String = ""

    fun getDetails(): LiveData<BusinessDetails> {
        return details
    }

    fun setDetails() {
        name.value = details.value!!.name
        rating.value = details.value!!.rating
        reviews.value = getApplication<Application>().getString(R.string.reviews, details.value!!.reviewCount)
        categories.value = details.value!!.categories.joinToString { it.title }
        address.value = details.value!!.location.displayAddress.joinToString(separator = "\n") {it}
        phone.value = details.value!!.phone
        hours = getFixedHours()
        photos = details.value!!.photos
        imageUrl = details.value!!.imageUrl
    }

    private fun getFixedHours(): List<OpenHours> {
        val hours = details.value!!.hours[0].open.toMutableList()
        for (i in 0..6) {
            if(hours[i].day != i)
                hours.add(i, OpenHours(false, "", "", i))
        }
        return hours.toList()
    }

    fun getLatLong(): LatLng {
        return LatLng(details.value!!.coordinates.latitude, details.value!!.coordinates.longitude)
    }
}