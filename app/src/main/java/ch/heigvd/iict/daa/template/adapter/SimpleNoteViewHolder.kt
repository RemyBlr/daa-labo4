package ch.heigvd.iict.daa.template.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Type
import java.text.SimpleDateFormat
import java.util.Locale

class SimpleNoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val icon: ImageView = view.findViewById(R.id.item_icon)
    private val title: TextView = view.findViewById(R.id.item_title)
    private val text: TextView = view.findViewById(R.id.item_text)
//    private val creationDate: TextView = view.findViewById(R.id.item_creation_date)
//
//    private val dateFormatter =
//        SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())

    fun bind(note: Note) {
        title.text = note.title
        text.text = note.text

        val iconRes = when (note.type) {
            Type.SHOPPING -> R.drawable.shopping
            Type.TODO -> R.drawable.todo
            Type.WORK -> R.drawable.work
            Type.FAMILY -> R.drawable.family
            else -> R.drawable.note
        }

        icon.setImageResource(iconRes)

        // Ne pas afficher ??? (pas sûr)
        //creationDate.text = dateFormatter.format(note.creationDate.time)
    }
}