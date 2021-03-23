package gabriel.estg.cleancity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import gabriel.estg.cleancity.database.dao.NoteDao
import gabriel.estg.cleancity.database.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
public abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDatabaseDao(): NoteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                 .build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.noteDatabaseDao())
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            // Delete all content here.
            noteDao.deleteAll()

            // Add sample words.
            var note = Note(
                0,
                "Rua partida",
                "Rua Das couves",
                "Cidade das couves",
                "4213",
                "3/21/2021",
                "Ta tudo partido"
            )
            noteDao.insert(note)

            // TODO: Add your own words!
        }
    }
}