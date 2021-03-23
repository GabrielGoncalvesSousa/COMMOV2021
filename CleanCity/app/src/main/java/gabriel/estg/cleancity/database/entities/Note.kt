package gabriel.estg.cleancity.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val assunto: String,
    val rua: String?,
    val cidade: String?,
    val codigo_postal: String?,
    val data: String?,
    val observacoes: String?
)
