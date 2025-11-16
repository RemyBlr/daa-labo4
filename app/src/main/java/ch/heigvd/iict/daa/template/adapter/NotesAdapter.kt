package ch.heigvd.iict.daa.template.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule

class NotesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var notesList = listOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val view = inflater.inflate(R.layout.item_simple, parent, false)
            SimpleNote(view)
        } else {
            val view = inflater.inflate(R.layout.item_schedule, parent, false)
            ScheduledNote(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = notesList[position]
        when (holder) {
            is SimpleNote -> holder.bind(note)
            is ScheduledNote -> holder.bind(note)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateData(newNotes: List<Note>) {
        this.notesList = newNotes
        notifyDataSetChanged()
    }
}