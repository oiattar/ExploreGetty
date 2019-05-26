package com.issa.omar.exploregetty.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.issa.omar.exploregetty.R
import com.issa.omar.exploregetty.model.OpenHours
import java.util.*

class HoursAdapter(private val hours: List<OpenHours>):
    RecyclerView.Adapter<HoursAdapter.HoursViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hours, parent,false)
        return HoursViewHolder(view)
    }

    override fun getItemCount(): Int = hours.size

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        val openHours: OpenHours = hours[position]
        holder.dayText.text = getDay(openHours)
        holder.timeText.text = getTime(openHours)
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

    class HoursViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val dayText: TextView = view.findViewById(R.id.day)
        val timeText: TextView = view.findViewById(R.id.time)
    }
}