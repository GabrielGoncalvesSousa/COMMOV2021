package gabriel.estg.cleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.ServiceBuilder
import gabriel.estg.cleancity.notes.NotesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call= request.getAllUsers()


        findViewById<Button>(R.id.buttonNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {
            })
        }
        
        findViewById<Button>(R.id.buttonNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {
            })
        }

        findViewById<ImageView>(R.id.imageViewRegisterLocation).setOnClickListener{
            Toast.makeText(applicationContext, "Register Clicked", Toast.LENGTH_LONG).show()
        }
    }
}