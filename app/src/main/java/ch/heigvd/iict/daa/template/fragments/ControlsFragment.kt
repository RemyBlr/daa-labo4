package ch.heigvd.iict.daa.labo4.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.heigvd.iict.daa.labo4.R

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteCounter = view.findViewById(R.id.noteCounterLabel)
        generateNotesBtn = view.findViewById(R.id.generateNotesButton)
        deleteAllNotesBtn = view.findViewById(R.id.deleteAllNotesButton)

        // TODO continue

        generateNotesBtn.setOnClickListener {
            // TODO
        }

        deleteAllNotesBtn.setOnClickListener {
            // TODO
        }
    }
}