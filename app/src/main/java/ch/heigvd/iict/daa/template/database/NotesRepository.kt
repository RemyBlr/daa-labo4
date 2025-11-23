/**
 * DAA - labo4
 * Autors : Bleuer Rémy, Changanaqui Yoann, Rajadurai Thirusan
 * Date : 23.11.2025
 * Description : Access point for updating the database for the notes
 */
package ch.heigvd.iict.daa.template.database

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.database.entities.NoteDao
import kotlin.concurrent.thread

/**
 * Repository used to access notes from the database.
 */
class NotesRepository(private val noteDao: NoteDao) {
    private val _noteSortingType = MutableLiveData<Int>(0)

    val countNotes: LiveData<Int> = noteDao.getNotesCount()

    val allNotes: LiveData<List<NoteAndSchedule>> = _noteSortingType.switchMap { sortType ->
        when (sortType) {
            0 -> noteDao.getAllNotesSortedCreationDate()
            1 -> noteDao.getAllNotesSortedScheduleDate()
            else -> noteDao.getAllNotesSortedCreationDate()
        }
    }

    fun setNoteSortingType(sortType: Int) {
        _noteSortingType.value = sortType
    }

    fun insertNote(note: Note, callback: ((Long) -> Unit)? = null) {
        thread {
            val noteId = noteDao.insertNote(note)
            callback?.invoke(noteId)
        }
    }

    fun insertSchedule(schedule: Schedule, callback: ((Long) -> Unit)? = null) {
        thread {
            val scheduleId = noteDao.insertSchedule(schedule)
            callback?.invoke(scheduleId)
        }
    }

    fun deleteAllNotes() {
        thread {
            noteDao.deleteAllSchedules()
            noteDao.deleteAllNotes()
        }
    }
}