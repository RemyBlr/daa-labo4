package ch.heigvd.iict.daa.template.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.Note
import java.text.SimpleDateFormat
import java.util.Locale

class SimpleNote(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.item_title)
    private val text: TextView = view.findViewById(R.id.item_text)
    private val date: TextView = view.findViewById(R.id.item_creation_date)
    private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    fun bind(note: Note) {
        title.text = note.title
        text.text = note.text
        date.text = dateFormatter.format(note.creationDate)
    }
}