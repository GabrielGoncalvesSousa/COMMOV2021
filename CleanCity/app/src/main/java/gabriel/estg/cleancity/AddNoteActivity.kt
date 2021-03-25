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
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        //EditTexts
        var subject = findViewById<EditText>(R.id.editTextSubject)
        var street = findViewById<EditText>(R.id.editTextStreet)
        var locality = findViewById<EditText>(R.id.editTextLocality)
        var postalCode =findViewById<EditText>(R.id.editTextPostal)
        var date =findViewById<EditText>(R.id.editTextDate)
        var observations =findViewById<EditText>(R.id.editTextObservations)



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
            Log.i("onActivityResult","Button was pressed")
            val replyIntent = Intent()
            if (TextUtils.isEmpty(subject.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
                Log.i("onActivityResult","Got canceled")

            } else {
                val note = subject.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, note)
                setResult(Activity.RESULT_OK, replyIntent)
                Log.i("onActivityResult","result OK")
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.notelistsql.REPLY"
    }
}