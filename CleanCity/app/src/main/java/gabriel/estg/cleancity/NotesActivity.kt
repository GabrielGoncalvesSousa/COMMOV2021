package gabriel.estg.cleancity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gabriel.estg.cleancity.adapters.NoteListAdapter
import gabriel.estg.cleancity.database.NotesApplication
import gabriel.estg.cleancity.database.entities.Note
import gabriel.estg.cleancity.viewModel.NoteViewModel
import gabriel.estg.cleancity.viewModel.NoteViewModelFactory

class NotesActivity : AppCompatActivity() {


    val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NotesApplication).repository)
    }

    private val newListActivityRequestCode = 1
    private val editListActivityRequestCode = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.notesToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {})
        }

        //RecylerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NoteListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //ViewModel
        noteViewModel.allNotes.observe(this, Observer { notes ->
            // Update the cached copy of the words in the adapter.
            notes?.let { adapter.submitList(it) }
        })

        //Swipe Left to Delete
        val swipeDelete = object : SwipeToDeleteCallback(this, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                for (note in noteViewModel.allNotes.value!!) {
                    var idHolder = viewHolder.itemView.findViewById<TextView>(R.id.noteId).text
                    var idInt = Integer.parseInt(idHolder.toString())
                    if (note.id == idInt) {
                        noteViewModel.deleteNote(idInt)
                    }
                }
            }
        }

        //Swipe Right to Edit
        val swipeEdit = object : SwipeToEditCallback(this, 0, ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                for (note in noteViewModel.allNotes.value!!) {
                    var idHolder = viewHolder.itemView.findViewById<TextView>(R.id.noteId).text
                    var idInt = Integer.parseInt(idHolder.toString())
                    if (note.id == idInt) {

                        val intent = Intent(this@NotesActivity, EditNoteActivity::class.java)
                        intent.putExtra("id", note.id!!)
                        intent.putExtra("subject", note.assunto)
                        intent.putExtra("street", note.rua)
                        intent.putExtra("locality", note.cidade)
                        intent.putExtra("postalCode", note.codigo_postal)
                        intent.putExtra("date", note.data)
                        intent.putExtra("observations", note.observacoes)
                        startActivityForResult(intent, editListActivityRequestCode)
                    }
                }
            }
        }

        //Updates the recyclerView
        val itemTouchHelper = ItemTouchHelper(swipeDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val itemTouchHelperEdit = ItemTouchHelper(swipeEdit)
        itemTouchHelperEdit.attachToRecyclerView(recyclerView)
    }


    //Inflate Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_notes, menu)
        return true
    }

    //Items from the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemView = item.itemId
        when (itemView) {
            R.id.addNote -> {
                val intent = Intent(this@NotesActivity, AddNoteActivity::class.java)
                startActivityForResult(intent, newListActivityRequestCode)
            }
        }
        return false
    }

    //Get Results from Adding and Edit note
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        Log.i("ee", requestCode.toString())
        //Result from the Add Notes Activity
        if ((requestCode == 1 && resultCode == Activity.RESULT_OK)) {
            //Getting the data from the intent result
            var subject = intentData?.getStringExtra("subject")
            var street = intentData?.getStringExtra("street")
            var locality = intentData?.getStringExtra("locality")
            var postalCode = intentData?.getStringExtra("postalCode")
            var date = intentData?.getStringExtra("date")
            var observations = intentData?.getStringExtra("observations")


            val note = Note(
                null,
                subject,
                street,
                locality,
                postalCode,
                date,
                observations,
                false
            )
            noteViewModel.insert(note)
            Toast.makeText(
                applicationContext, R.string.toastSuccessAddNotesPage,
                Toast.LENGTH_LONG
            ).show()

        } else if (requestCode == 1 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(
                applicationContext,
                R.string.toastFailAddNotesPage,
                Toast.LENGTH_LONG
            ).show()
        } else if (requestCode == 20 && resultCode == Activity.RESULT_OK) { //Result from the Edit Notes Activity
            //Getting the data from the intent result
            var id = intentData?.getIntExtra("id", 0)
            var subject = intentData?.getStringExtra("subject")
            var street = intentData?.getStringExtra("street")
            var locality = intentData?.getStringExtra("locality")
            var postalCode = intentData?.getStringExtra("postalCode")
            var date = intentData?.getStringExtra("date")
            var observations = intentData?.getStringExtra("observations")

            val note = Note(
                id,
                subject,
                street,
                locality,
                postalCode,
                date,
                observations,
                false
            )
            noteViewModel.updateNote(note)
            Toast.makeText(
                applicationContext, R.string.editNotesPage_NoteEditedSuccessfully,
                Toast.LENGTH_LONG
            ).show()

        } else {
            Toast.makeText(
                applicationContext,
                R.string.editNotesPage_NoteEditedFail,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}