package gabriel.estg.cleancity.notes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.widget.Toolbar
import gabriel.estg.cleancity.R
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)


        //Get Data From Notes Activity
        var id: Int? = intent.getIntExtra("id", 0)
        var subjectInfo = intent.getStringExtra("subject")
        var streetInfo = intent.getStringExtra("street")
        var localityInfo = intent.getStringExtra("locality")
        var postalCodeInfo = intent.getStringExtra("postalCode")
        var dateInfo = intent.getStringExtra("date")
        var observationsInfo = intent.getStringExtra("observations")


        //Get Views Edit Texts
        var subject = findViewById<EditText>(R.id.editTextSubjectEDIT)
        var street = findViewById<EditText>(R.id.editTextStreetEDIT)
        var locality = findViewById<EditText>(R.id.editTextLocalityEDIT)
        var postalCode = findViewById<EditText>(R.id.editTextPostalEDIT)
        var date = findViewById<EditText>(R.id.editTextDateEDIT)
        var observations = findViewById<EditText>(R.id.editTextObservationsEDIT)

        //Set Texts to Views
        subject.setText(subjectInfo)
        street.setText(streetInfo)
        locality.setText(localityInfo)
        postalCode.setText(postalCodeInfo)
        date.setText(dateInfo)
        observations.setText(observationsInfo)


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
        var dateView = findViewById<EditText>(R.id.editTextDateEDIT)
        var buttonImagecalendar = findViewById<ImageView>(R.id.imageButtonCalendarEDIT)
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
        val button = findViewById<Button>(R.id.buttonSaveEDIT)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(subject.text) || TextUtils.isEmpty(street.text) || TextUtils.isEmpty(
                    locality.text
                ) || TextUtils.isEmpty(postalCode.text)
                || TextUtils.isEmpty(date.text) || TextUtils.isEmpty(observations.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra("id",id)
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
}

