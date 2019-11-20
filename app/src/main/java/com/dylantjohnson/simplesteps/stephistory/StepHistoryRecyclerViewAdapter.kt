package com.dylantjohnson.simplesteps.stephistory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dylantjohnson.simplesteps.databinding.ListItemStepsBinding
import com.dylantjohnson.simplesteps.models.StepsStat

class StepHistoryRecyclerViewAdapter:
        ListAdapter<StepsStat, StepsViewHolder>(StepsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StepsDiffCallback: DiffUtil.ItemCallback<StepsStat>() {
    override fun areItemsTheSame(oldItem: StepsStat, newItem: StepsStat): Boolean {
        return oldItem.day.time == newItem.day.time
    }

    override fun areContentsTheSame(oldItem: StepsStat, newItem: StepsStat): Boolean {
        return oldItem.day.time == newItem.day.time && oldItem.total == newItem.total
    }

}

class StepsViewHolder(binding: ListItemStepsBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding = binding

    fun bind(steps: StepsStat) {
        mBinding.steps = steps
        mBinding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): StepsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemStepsBinding.inflate(inflater, parent, false)
            return StepsViewHolder(binding)
        }
    }
}
