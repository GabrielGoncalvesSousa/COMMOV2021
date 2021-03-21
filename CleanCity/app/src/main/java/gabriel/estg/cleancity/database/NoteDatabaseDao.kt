package gabriel.estg.cleancity.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDatabaseDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAlphabetizedWords(): List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()
}