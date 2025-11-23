package ch.heigvd.iict.daa.template.database.entities

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.NoteAndSchedule
import ch.heigvd.iict.daa.labo4.models.Schedule

/**
 * Data Access Object for operations on Note entities
 */
@Dao
interface NoteDao {

    //////////////////////////////////
    ////////////// Notes /////////////
    //////////////////////////////////
    @Insert
    fun insertNote(note: Note): Long

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Transaction
    @Query("SELECT * FROM Note Order BY creationDate DESC")
    fun getAllNotesSortedCreationDate(): LiveData<List<NoteAndSchedule>>

    @Transaction
    @Query("""
        SELECT * FROM Note
    LEFT JOIN Schedule ON Note.noteId = Schedule.ownerId
    ORDER BY Schedule.date DESC
    """)
    fun getAllNotesSortedScheduleDate(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT COUNT(*) FROM Note")
    fun getNotesCount(): LiveData<Int>

    //////////////////////////////////
    ////////// Schedules /////////////
    //////////////////////////////////

    @Insert
    fun insertSchedule(schedule: Schedule): Long

    @Query("DELETE FROM Schedule")
    fun deleteAllSchedules()
}