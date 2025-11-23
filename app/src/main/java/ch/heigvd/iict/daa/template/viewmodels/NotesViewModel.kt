/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : ViewModel gérant les données temporaires
 */
package ch.heigvd.iict.daa.template.viewmodels

import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.template.database.NotesRepository
import ch.heigvd.iict.daa.labo4.models.Note

class NotesViewModel(private val repository: NotesRepository) : ViewModel() {
    val allNotes = repository.allNotes
    val countNotes = repository.countNotes

    fun generateANoteWithSchedule() {
        val note = Note.generateRandomNote()

        // Callback pour créer un schedule après d'avoir créé la note
        repository.insertNote(note) { noteId ->
            val schedule = Note.generateRandomSchedule()
            if (schedule != null) {
                schedule.ownerId = noteId
                repository.insertSchedule(schedule)
            }
        }
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun sortNotesByCreationDate(){
        repository.setNoteSortingType(0)
    }

    fun sortNotesByScheduleDate(){
        repository.setNoteSortingType(1)
    }
}

class NotesViewModelFactory(private val repository: NotesRepository) :
    androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") //enlève le warning de cast
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
