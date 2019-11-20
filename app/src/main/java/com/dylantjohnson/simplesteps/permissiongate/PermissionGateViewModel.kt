package com.dylantjohnson.simplesteps.permissiongate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionGateViewModel: ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    fun notifyFinishedLoading() {
        mIsLoading.value = false
    }
}
