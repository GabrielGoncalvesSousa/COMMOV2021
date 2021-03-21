package gabriel.estg.cleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {
            })
        }


        findViewById<ImageView>(R.id.imageViewRegisterLocation).setOnClickListener{
            Toast.makeText(applicationContext, "Register Clicked", Toast.LENGTH_LONG).show()
        }


    }
}