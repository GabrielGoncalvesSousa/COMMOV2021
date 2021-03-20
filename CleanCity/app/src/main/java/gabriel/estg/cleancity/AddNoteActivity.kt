package gabriel.estg.cleancity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        var toolbar = findViewById<Toolbar>(R.id.AddNoteToolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {})
        }

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
    }
}