package ch.heigvd.iict.daa.template.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.daa.labo4.models.Note
import ch.heigvd.iict.daa.labo4.models.Schedule
import ch.heigvd.iict.daa.template.database.entities.NoteDao
import kotlin.concurrent.thread


/**
 * Room database class.
 */

@Database(entities = [Note::class, Schedule::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                )
                    .addCallback(NotesDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class NotesDatabaseCallback : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                     thread {
                        populateDatabase(database.noteDao())
                     }
                }
            }

            fun populateDatabase(noteDao: NoteDao) {
                repeat(10) {
                    val note = Note.generateRandomNote()
                    val noteId = noteDao.insertNote(note)
                    val schedule = Note.generateRandomSchedule()
                    // allow null schedules
                    schedule?.let {
                        it.ownerId = noteId
                        noteDao.insertSchedule(it)
                    }
                }
            }
        }
    }
}