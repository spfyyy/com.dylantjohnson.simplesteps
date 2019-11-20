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

    private val mSteps = MutableLiveData<List<StepsStat>>()
    val steps: LiveData<List<StepsStat>>
        get() = mSteps

    init {
        mUiScope.launch {
            mSteps.value = mData.getStepHistory()
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
