package com.dylantjohnson.simplesteps.stephistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylantjohnson.simplesteps.databinding.ListItemStepsBinding
import com.dylantjohnson.simplesteps.models.StepsStat

/**
 * This class is in charge of adapting a list of StepsStats into Views for a RecyclerView.
 */
class StepHistoryRecyclerViewAdapter:
        ListAdapter<StepsStat, StepsViewHolder>(StepsDiffCallback()) {
    /**
     * Instantiate a fresh StepsViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder.from(parent)
    }

    /**
     * Attach a particular StepsStat to a StepsViewHolder.
     */
    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * Callback used by DiffUtil to efficiently update a list of StepStats when it changes.
 */
class StepsDiffCallback: DiffUtil.ItemCallback<StepsStat>() {
    /**
     * Check if two StepStats are for the same day.
     */
    override fun areItemsTheSame(oldItem: StepsStat, newItem: StepsStat): Boolean {
        return oldItem.day.time == newItem.day.time
    }

    /**
     * Check if two StepStats have any differences between them.
     */
    override fun areContentsTheSame(oldItem: StepsStat, newItem: StepsStat): Boolean {
        return oldItem.day.time == newItem.day.time && oldItem.total == newItem.total
    }

}

/**
 * This class is used by a RecyclerView to generate new Views for StepsStats.
 */
class StepsViewHolder(binding: ListItemStepsBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding = binding

    /**
     * Bind a specific StepsStat to the View.
     */
    fun bind(steps: StepsStat) {
        mBinding.steps = steps
        mBinding.executePendingBindings()
    }

    companion object {
        /**
         * Inflate a fresh View inside a specific ViewGroup.
         */
        fun from(parent: ViewGroup): StepsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemStepsBinding.inflate(inflater, parent, false)
            return StepsViewHolder(binding)
        }
    }
}
