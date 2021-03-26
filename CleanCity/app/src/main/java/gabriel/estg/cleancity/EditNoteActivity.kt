package gabriel.estg.cleancity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import gabriel.estg.cleancity.database.NotesApplication
import gabriel.estg.cleancity.database.entities.Note
import gabriel.estg.cleancity.viewModel.NoteViewModel
import gabriel.estg.cleancity.viewModel.NoteViewModelFactory
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)


        //EditTexts
        var subject = findViewById<EditText>(R.id.editTextSubject)
        var street = findViewById<EditText>(R.id.editTextStreet)
        var locality = findViewById<EditText>(R.id.editTextLocality)
        var postalCode = findViewById<EditText>(R.id.editTextPostal)
        var date = findViewById<EditText>(R.id.editTextDate)
        var observations = findViewById<EditText>(R.id.editTextObservations)


        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.AddNoteToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {})
        }

        //Calendar
        var calendarView = Calendar.getInstance()
        var year = calendarView.get(Calendar.YEAR)
        var month = calendarView.get(Calendar.MONTH)
        var day = calendarView.get(Calendar.DAY_OF_MONTH)
        var dateView = findViewById<EditText>(R.id.editTextDate)
        var buttonImagecalendar = findViewById<ImageView>(R.id.imageButtonCalendar)
        buttonImagecalendar.setOnClickListener {
            var datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear: Int, mMonth: Int, mDay: Int ->
                    dateView.setText("" + mDay + "/" + (mMonth + 1) + "/" + mYear)
                }, year, month, day
            )
            datePicker.show()
        }

        //Save Button
        val button = findViewById<Button>(R.id.buttonSave)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(subject.text) || TextUtils.isEmpty(street.text) || TextUtils.isEmpty(
                    locality.text
                ) || TextUtils.isEmpty(postalCode.text)
                || TextUtils.isEmpty(date.text) || TextUtils.isEmpty(observations.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra("subject", subject.text.toString())
                replyIntent.putExtra("street", street.text.toString())
                replyIntent.putExtra("locality", locality.text.toString())
                replyIntent.putExtra("postalCode", postalCode.text.toString())
                replyIntent.putExtra("date", date.text.toString())
                replyIntent.putExtra("observations", observations.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.notelistsql.REPLY"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)


        var id :Int? = intentData?.getIntExtra("id",0)

        val noteViewModel: NoteViewModel by viewModels {
            NoteViewModelFactory((application as NotesApplication).repository)
        }

        for (note in noteViewModel.allNotes.value!!) {
            if (note.assunto == viewHolder.itemView.findViewById<TextView>(R.id.subject).text) {
                noteId = note.id!!
                noteViewModel.deleteNote(noteId)
            }
        }
    }
}

