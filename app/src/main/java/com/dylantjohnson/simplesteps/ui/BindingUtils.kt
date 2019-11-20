package com.dylantjohnson.simplesteps.ui

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
