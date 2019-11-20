package com.dylantjohnson.simplesteps.ui

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.ConfigurationCompat
import androidx.databinding.BindingAdapter
import com.dylantjohnson.simplesteps.R
import java.util.*

@BindingAdapter("displayDate")
fun TextView.displayDate(date: Date) {
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
    val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
    val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val display = context.getString(R.string.steps_date_format, dayName, monthName, day)
    text = display
}

@BindingAdapter("displaySteps")
fun TextView.displaySteps(count: Int) {
    val display = context.getString(R.string.steps_count_format, count)
    text = display
}

@BindingAdapter("displaySortOrder")
fun Button.displaySortOrder(ascending: Boolean) {
    val display = if (ascending) {
        context.getString(R.string.sort_order_ascending)
    } else {
        context.getString(R.string.sort_order_descending)
    }
    text = display
}

@BindingAdapter("setVisibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
