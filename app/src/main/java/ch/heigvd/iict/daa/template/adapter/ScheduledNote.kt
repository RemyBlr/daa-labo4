package ch.heigvd.iict.daa.template.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.*
import java.text.SimpleDateFormat
import java.util.Locale

class ScheduledNote(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.item_title)
    private val text: TextView = view.findViewById(R.id.item_text)
    private val creationDate: TextView = view.findViewById(R.id.item_creation_date)
    //private val scheduleDate: TextView = view.findViewById(R.id.item_schedule_date)
    private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    fun bind(note: Note) {
        title.text = note.title
        text.text = note.text
        creationDate.text = "Créé le: ${dateFormatter.format(note.creationDate)}"

        // TODO: Scheduled date en plus ?!?
    }
}