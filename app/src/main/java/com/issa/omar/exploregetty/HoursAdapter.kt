package com.issa.omar.exploregetty

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.issa.omar.exploregetty.model.OpenHours
import java.util.*

class HoursAdapter(private val context: Context, private val hours: List<OpenHours>) : BaseAdapter() {

    override fun getView(pos: Int, view: View?, parent: ViewGroup?): View {
        val viewholder: ViewHolder
        var dailyHour = view

        if (dailyHour == null) {
            dailyHour = LayoutInflater.from(parent?.context).inflate(R.layout.item_hours, parent,false)
            viewholder = ViewHolder(dailyHour)
            dailyHour.tag = viewholder
        } else {
            viewholder = dailyHour.tag as ViewHolder
        }

        val openHours: OpenHours = getItem(pos)
        viewholder.dayText.text = getDay(openHours)
        viewholder.timeText.text = getTime(openHours)

        return dailyHour!!
    }

    private fun getDay(openHours: OpenHours): String {
        return when(openHours.day) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> ""
        }
    }

    private fun getTime(openHours: OpenHours): String {
        if (openHours.start == "")
            return "Closed"
        val open = convertTime(openHours.start)
        val close = convertTime(openHours.end)
        return "$open - $close"
    }

    private fun convertTime(time24: String): String {
        val time: Calendar = Calendar.getInstance()
        time.set(Calendar.HOUR_OF_DAY, time24.substring(0..1).toInt())
        time.set(Calendar.MINUTE, time24.substring(2..3).toInt())
        val hour = time.get(Calendar.HOUR)
        val minute = String.format("%02d", time.get(Calendar.MINUTE))
        val am_pm = if (time.get(Calendar.AM_PM) == 0) "AM" else "PM"
        return "$hour:$minute $am_pm"
    }

    override fun getItem(pos: Int): OpenHours {
        return hours[pos]
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getCount(): Int {
        return hours.size
    }

    private class ViewHolder(view: View) {
        val dayText: TextView = view.findViewById(R.id.day)
        val timeText: TextView = view.findViewById(R.id.time)
    }
}