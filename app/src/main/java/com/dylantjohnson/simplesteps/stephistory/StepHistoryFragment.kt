package com.dylantjohnson.simplesteps.stephistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dylantjohnson.simplesteps.data.FitnessData

import com.dylantjohnson.simplesteps.databinding.FragmentStepHistoryBinding

/**
 * This Fragment is the Step History page.
 * <p>
 * Using the Google Fit API, this page simply displays a list of the user's daily step counts. The
 * sort order of the list can be toggled between chronological and reverse ordering.
 */
class StepHistoryFragment : Fragment() {
    private lateinit var mBinding: FragmentStepHistoryBinding
    private lateinit var mViewModel: StepHistoryViewModel

    /**
     * Set up ViewModel binding.
     * <p>
     * This fragment is in charge of observing its ViewModel's step list and updating the
     * RecyclerView when it changes.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val fitnessData = FitnessData(context!!)
        mViewModel = ViewModelProviders
            .of(this, StepHistoryViewModel.Factory(fitnessData))
            .get(StepHistoryViewModel::class.java)
        mBinding = FragmentStepHistoryBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel

        val stepsAdapter = StepHistoryRecyclerViewAdapter()
        mBinding.stepHistoryRecyclerView.adapter = stepsAdapter
        mBinding.stepHistoryRecyclerView.layoutManager = LinearLayoutManager(context)

        mViewModel.steps.observe(this, Observer {
            stepsAdapter.submitList(it)
        })

        return mBinding.root
    }
}
