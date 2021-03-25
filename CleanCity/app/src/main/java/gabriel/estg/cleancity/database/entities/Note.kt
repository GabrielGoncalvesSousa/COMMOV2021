package gabriel.estg.cleancity.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var assunto: String,
    var rua: String?,
    var cidade: String?,
    var codigo_postal: String?,
    var data: String?,
    var observacoes: String?,
    var expandable: Boolean
)
