package gabriel.estg.cleancity.database

import androidx.annotation.WorkerThread
import gabriel.estg.cleancity.database.dao.NoteDao
import gabriel.estg.cleancity.database.entities.Note
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<Note>> = noteDao.getNotes()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }
}