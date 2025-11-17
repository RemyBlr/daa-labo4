package ch.heigvd.iict.daa.template.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.*

class ScheduledNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val icon: ImageView = view.findViewById(R.id.item_icon)
    private val title: TextView = view.findViewById(R.id.item_title)
    private val text: TextView = view.findViewById(R.id.item_text)
    private val deadline: TextView = view.findViewById(R.id.item_deadline)

    fun bind(item: NoteAndSchedule) {
        val note = item.note

        val iconRes = when (note.type) {
            Type.SHOPPING -> R.drawable.shopping
            Type.TODO -> R.drawable.todo
            Type.WORK -> R.drawable.work
            Type.FAMILY -> R.drawable.family
            else -> R.drawable.note
        }

        icon.setImageResource(iconRes)

        title.text = note.title
        text.text = note.text

        val scheduleTimestamp = item.schedule?.date?.timeInMillis
        if (scheduleTimestamp == null) {
            deadline.visibility = View.GONE
        } else {
            deadline.visibility = View.VISIBLE
            deadline.text = formatTimeLeft(scheduleTimestamp)
        }
    }

    private fun formatTimeLeft(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = timestamp - now

        if (diff < 0) return "late"

        val daysLeft = diff / (1000 * 60 * 60 * 24)

        return when {
            daysLeft == 0L -> "today"
            daysLeft == 1L -> "1 day"
            daysLeft < 7 -> "$daysLeft days"
            daysLeft < 14 -> "1 week"
            daysLeft < 30 -> "${daysLeft / 7} weeks"
            daysLeft < 60 -> "1 month"
            else -> "${daysLeft / 30} months"
        }
    }
}