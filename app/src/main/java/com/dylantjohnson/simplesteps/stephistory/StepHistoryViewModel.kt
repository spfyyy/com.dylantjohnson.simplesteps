package com.dylantjohnson.simplesteps.stephistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dylantjohnson.simplesteps.data.FitnessData
import com.dylantjohnson.simplesteps.models.StepsStat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * ViewModel for the StepHistory page.
 */
class StepHistoryViewModel(data: FitnessData): ViewModel() {
    private val mViewModelJob = Job()
    private val mUiScope = CoroutineScope(Dispatchers.Main + mViewModelJob)
    private val mData = data

    private val mSteps = MutableLiveData<List<StepsStat>>(null)
    /**
     * Observable list of the user's step data.
     */
    val steps: LiveData<List<StepsStat>>
        get() = mSteps

    private val mOrderAscending = MutableLiveData<Boolean>(true)
    /**
     * Observable flag for the current sorting order of the user's step data.
     */
    val orderAscending: LiveData<Boolean>
        get() = mOrderAscending

    private val mIsLoading = MutableLiveData<Boolean>(true)
    /**
     * Observable flag for whether the user's step data has been fetched or not.
     */
    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    init {
        // Begin fetching user step data.
        mUiScope.launch {
            mIsLoading.value = true
            mSteps.value = mData.getStepHistory()
            mIsLoading.value = false
        }
    }

    /**
     * Reverse the chronological sorting order the user's step data.
     */
    fun reverseSortOrder() {
        mOrderAscending.value = !mOrderAscending.value!!
        mSteps.value?.let {
            val newList = it.toMutableList()
            newList.sortWith(Comparator { oldSteps, newSteps ->
                if (mOrderAscending.value!!) {
                    oldSteps.day.compareTo(newSteps.day)
                } else {
                    newSteps.day.compareTo(oldSteps.day)
                }
            })
            mSteps.value = newList
        }
    }

    /**
     * Cancel any running coroutines when this ViewModel is removed.
     */
    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }

    /**
     * ViewModel Factory class for generating StepHistoryViewModels.
     */
    class Factory(private val data: FitnessData): ViewModelProvider.Factory {
        /**
         * Generate a StepHistoryViewModel.
         */
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StepHistoryViewModel::class.java)) {
                @Suppress("unchecked_cast")
                return StepHistoryViewModel(data) as T
            }
            throw IllegalArgumentException("Unexpected ViewModel class")
        }
    }
}
