/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : Adadpter décidant du type de note à afficher
 */
package ch.heigvd.iict.daa.template.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule

class NotesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = listOf<NoteAndSchedule>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == 0) {
            val view = inflater.inflate(R.layout.item_simple, parent, false)
            SimpleNoteViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_schedule, parent, false)
            ScheduledNoteViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is SimpleNoteViewHolder -> holder.bind(item.note)
            is ScheduledNoteViewHolder -> holder.bind(item)
        }
    }

    // Note simple ou scheduled
    override fun getItemViewType(position: Int): Int {
        return if (items[position].schedule == null) 0 else 1
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<NoteAndSchedule>) {
        this.items = newItems
        notifyDataSetChanged()
    }
}