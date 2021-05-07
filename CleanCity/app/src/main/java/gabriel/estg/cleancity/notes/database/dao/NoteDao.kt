package gabriel.estg.cleancity.notes.database.dao

import androidx.room.*
import gabriel.estg.cleancity.notes.database.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Update
    suspend fun updateNote(note:Note)

//    @Delete
//    suspend fun deleteNote(id:Int)
}