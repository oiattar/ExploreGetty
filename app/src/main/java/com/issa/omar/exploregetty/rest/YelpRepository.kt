package com.issa.omar.exploregetty.rest

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.issa.omar.exploregetty.BuildConfig
import com.issa.omar.exploregetty.model.BusinessDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object YelpRepository {

    private val TAG: String = YelpRepository::class.java.simpleName

    private val yelpApiService by lazy {
        YelpApiService.create()
    }

    private var disposable: Disposable? = null

    fun getBusinessDetails(id: String, details: MutableLiveData<BusinessDetails>) {
        disposable =
            yelpApiService.getDetails(getYelpApiKey(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> details.value = result },
                    { error -> Log.e(TAG, "error: ${error.message}") }
                )
    }

    private fun getYelpApiKey(): String {
        return "Bearer ${BuildConfig.YelpAPIKey}"
    }
}