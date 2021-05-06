package gabriel.estg.cleancity.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import gabriel.estg.cleancity.notes.database.dao.NoteDao
import gabriel.estg.cleancity.notes.database.entities.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Note::class], version = 1, exportSchema = false)
public abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteDatabase {


            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )   .addCallback(NoteDatabaseCallback(scope))
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
                    populateDatabase(database.noteDao())
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            // Delete all content here.
            noteDao.deleteAll()

            // Add sample words.
            var note = Note(
                20,
                "Rua partida",
                "Rua Das couves",
                "Cidade das couves",
                "4213",
                "3/21/2021",
                "Ta tudo partido",
                false
            )

            var note1 = Note(
                1,
                "Caes a solta",
                "Rua Dos blimblins",
                "Cidade do mato",
                "4213",
                "3/21/2021",
                "TOWINFOIEHRGIOHEROGIEROIG9OERUHJGPOIEHWGIOPUHERWPIOUHGIPUREWHGPUIHWREUIHGUIWERHGIUWHERIUGHEWIURGHIUWERHGIUWEHRGIUEHRGIUHWEIROUGHIWEOURHGIEUOWRHGIOUEWHRGIUOEHWRIOGUHEIWRGHIOEURWOHGIUHIRUGH",
                false
            )
            noteDao.insert(note)
            noteDao.insert(note1)

        }
    }

}





