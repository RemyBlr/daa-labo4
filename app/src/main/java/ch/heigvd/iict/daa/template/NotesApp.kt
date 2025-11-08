package ch.heigvd.iict.daa.labo4

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ch.heigvd.iict.daa.template.database.AppDatabase
import ch.heigvd.iict.daa.template.database.NotesRepository

/**
 * Initialization of database and repositories at application start.
 */
class NotesApp : Application() {

    private val scope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this, scope) }

    val repository by lazy { NotesRepository(database.noteDao()) }

}