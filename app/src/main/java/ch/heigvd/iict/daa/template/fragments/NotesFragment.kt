package ch.heigvd.iict.daa.labo4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.labo4.NotesApp
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.template.viewmodels.NotesViewModel
import ch.heigvd.iict.daa.template.viewmodels.NotesViewModelFactory

import androidx.recyclerview.widget.LinearLayoutManager
import ch.heigvd.iict.daa.template.adapter.NotesAdapter

/**
 * A fragment displaying notes in a RecyclerView.
 */
class NotesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApp).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.notesRecyclerView)
        notesAdapter = NotesAdapter()

        // Dire au RecyclerView d'utiliser cet adapter et de s'afficher en liste verticale
        recyclerView.adapter = notesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        notesViewModel.allNotes.observe(viewLifecycleOwner) { scheduledAndNotes ->
            scheduledAndNotes?.let { notes ->
                val list = notes.map { scheduledAndNotes -> scheduledAndNotes.note }
                notesAdapter.updateData(list)
            }
        }
    }
}