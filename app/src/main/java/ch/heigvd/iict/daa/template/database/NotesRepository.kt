package ch.heigvd.iict.daa.template.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.database.entities.NoteDao

/**
 * Repository used to access notes from the database.
 */
class NotesRepository(private val noteDao: NoteDao) {
    private val noteSortingType = MutableLiveData<Int>(0)

    val countNotes: LiveData<Int> = noteDao.getNotesCount()

    val allNotes: LiveData<List<NoteAndSchedule>> = noteSortingType.switchMap { sortType ->
        when (sortType) {
            0 -> noteDao.getAllNotesSortedCreationDate()
            1 -> noteDao.getAllNotesSortedScheduleDate()
            else -> noteDao.getAllNotesSortedCreationDate()
        }
    }

    fun setNoteSortingType(sortType: Int) {
        noteSortingType.value = sortType
    }

    suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }

    suspend fun insertSchedule(schedule: Schedule): Long {
        return noteDao.insertSchedule(schedule)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllSchedules()
        noteDao.deleteAllNotes()
    }
}