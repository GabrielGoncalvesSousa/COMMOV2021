package gabriel.estg.cleancity.viewModel


import androidx.lifecycle.*
import gabriel.estg.cleancity.api.Ocorrency
import kotlinx.coroutines.flow.asFlow

class OcorrencesViewModel(private val ocorrences: List<Ocorrency>) : ViewModel() {
    var ocorrencyLiveData: LiveData<List<Ocorrency>> = listOf(ocorrences).asFlow().asLiveData()
}



