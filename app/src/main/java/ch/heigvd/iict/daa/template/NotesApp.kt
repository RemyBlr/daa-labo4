package ch.heigvd.iict.daa.labo4

import android.app.Application
import ch.heigvd.iict.daa.template.database.AppDatabase
import ch.heigvd.iict.daa.template.database.NotesRepository

/**
 * Initialization of database and repositories at application start.
 */
class NotesApp : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { NotesRepository(database.noteDao()) }

}