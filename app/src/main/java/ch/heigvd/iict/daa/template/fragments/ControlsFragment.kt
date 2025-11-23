/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : Fragment displaying a control panel for notes (tablet mode only)
 */
package ch.heigvd.iict.daa.labo4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.labo4.NotesApp
import ch.heigvd.iict.daa.labo4.R
import ch.heigvd.iict.daa.template.viewmodels.NotesViewModel
import ch.heigvd.iict.daa.template.viewmodels.NotesViewModelFactory

/**
 * A fragment containing controls to manage notes, only visible in tablet mode.
 */
class ControlsFragment : Fragment() {

    private lateinit var noteCounter : TextView
    private lateinit var generateNotesBtn : Button
    private lateinit var deleteAllNotesBtn : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_controls, container, false)
    }

    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApp).repository, requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteCounter = view.findViewById(R.id.noteCounterLabel)
        generateNotesBtn = view.findViewById(R.id.generateNotesButton)
        deleteAllNotesBtn = view.findViewById(R.id.deleteAllNotesButton)

        notesViewModel.countNotes.observe(viewLifecycleOwner) { count ->
            noteCounter.text = "$count"
        }


        generateNotesBtn.setOnClickListener {
            notesViewModel.generateANoteWithSchedule()
        }

        deleteAllNotesBtn.setOnClickListener {
            notesViewModel.deleteAllNotes()
        }
    }
}