package gabriel.estg.cleancity.viewModel

import androidx.lifecycle.*
import gabriel.estg.cleancity.database.NoteRepository
import gabriel.estg.cleancity.database.entities.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes.asLiveData()


    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        repository.deleteNote(id)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}