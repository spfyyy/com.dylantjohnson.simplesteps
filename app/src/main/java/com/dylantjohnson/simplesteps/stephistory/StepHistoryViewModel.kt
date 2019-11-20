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

class StepHistoryViewModel(data: FitnessData): ViewModel() {
    private val mViewModelJob = Job()
    private val mUiScope = CoroutineScope(Dispatchers.Main + mViewModelJob)
    private val mData = data

    private val mSteps = MutableLiveData<List<StepsStat>>(null)
    val steps: LiveData<List<StepsStat>>
        get() = mSteps

    private val mOrderAscending = MutableLiveData<Boolean>(true)
    val orderAscending: LiveData<Boolean>
        get() = mOrderAscending

    private val mIsLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = mIsLoading

    init {
        mUiScope.launch {
            mIsLoading.value = true
            mSteps.value = mData.getStepHistory()
            mIsLoading.value = false
        }
    }

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

    override fun onCleared() {
        super.onCleared()
        mViewModelJob.cancel()
    }

    class Factory(private val data: FitnessData): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StepHistoryViewModel::class.java)) {
                @Suppress("unchecked_cast")
                return StepHistoryViewModel(data) as T
            }
            throw IllegalArgumentException("Unexpected ViewModel class")
        }
    }
}
