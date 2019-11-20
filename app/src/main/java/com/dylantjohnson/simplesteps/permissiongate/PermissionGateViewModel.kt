package com.dylantjohnson.simplesteps.permissiongate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for the PermissionGate page.
 * <p>
 * Because the app permissions and Google consent code is intended to be run from the Fragment, this
 * ViewModel simply waits to be notified when that stuff is finished.
 */
class PermissionGateViewModel: ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>(true)
    /**
     * Observable flag for whether the app is waiting for permissions or not.
     */
    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    /**
     * Notify this ViewModel that the Fragment is done loading.
     */
    fun notifyFinishedLoading() {
        mIsLoading.value = false
    }
}
