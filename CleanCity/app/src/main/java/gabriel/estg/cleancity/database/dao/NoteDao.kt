package gabriel.estg.cleancity.database.dao

import androidx.room.*
import gabriel.estg.cleancity.database.entities.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

//
    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

//    @Delete
//    suspend fun deleteNote(id:Int)
}