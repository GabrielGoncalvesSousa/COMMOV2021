package gabriel.estg.cleancity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.widget.Toolbar

class NotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        var toolbar = findViewById<Toolbar>(R.id.notesToolbar)
        setSupportActionBar(toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_action_back)

        toolbar.setNavigationOnClickListener({
            startActivity(Intent(this, MainActivity::class.java).apply {
            })
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_notes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView = item.itemId
        when (itemView) {
            R.id.addNote -> startActivity(Intent(this, AddNoteActivity::class.java).apply {
            })
        }
        return false
    }

}