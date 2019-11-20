package com.dylantjohnson.simplesteps.ui

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.os.ConfigurationCompat
import androidx.databinding.BindingAdapter
import com.dylantjohnson.simplesteps.R
import java.util.*

/**
 * Bind a Date to a TextView and format it.
 */
@BindingAdapter("displayDate")
fun TextView.displayDate(date: Date?) {
    date?.let {
        val calendar = Calendar.getInstance().apply {
            time = it
        }
        val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
        val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale)
        val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val display = context.getString(R.string.steps_date_format, dayName, monthName, day)
        text = display
    }
}

/**
 * Bind a number to a TextView, formatted as a step count.
 */
@BindingAdapter("displaySteps")
fun TextView.displaySteps(count: Int?) {
    val display = context.getString(R.string.steps_count_format, count ?: 0)
    text = display
}

/**
 * Change the text of a button to a particular sort order by binding a boolean.
 * <p>
 * True = chronological, false = reverse.
 */
@BindingAdapter("displaySortOrder")
fun Button.displaySortOrder(ascending: Boolean?) {
    val display = if (ascending == true) {
        context.getString(R.string.sort_order_ascending)
    } else {
        context.getString(R.string.sort_order_descending)
    }
    text = display
}

/**
 * Set the visibility of a View by binding a boolean to it.
 */
@BindingAdapter("setVisibility")
fun View.setVisibility(visible: Boolean?) {
    visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
