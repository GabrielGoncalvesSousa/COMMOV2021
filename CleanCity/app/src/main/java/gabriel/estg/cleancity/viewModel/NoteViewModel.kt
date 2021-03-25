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

    fun delete(id:Int) = viewModelScope.launch {
        repository.delete(id)
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