package com.dylantjohnson.simplesteps.stephistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dylantjohnson.simplesteps.databinding.FragmentStepHistoryBinding

class StepHistoryFragment : Fragment() {
    private lateinit var mBinding: FragmentStepHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        mBinding = FragmentStepHistoryBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }
}
