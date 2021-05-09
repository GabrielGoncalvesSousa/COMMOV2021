package gabriel.estg.cleancity

import OcorrencesListAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.Ocorrency
import gabriel.estg.cleancity.api.ServiceBuilder
import gabriel.estg.cleancity.viewModel.OcorrencesViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMyOcorrences : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_my_ocorrences)

        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.myOwnOcorrencesToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java).apply {})
        }

        //RecylerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerviewoOcorrences)
        val adapter = OcorrencesListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

//        //Get the data
//        var ocorrences = listOf<Ocorrency>()
//        val request = ServiceBuilder.buildService(EndPoints::class.java)
//
//        request.getAllOcorrences().enqueue(object : Callback<List<Ocorrency>> {
//            override fun onResponse(
//                call: Call<List<Ocorrency>>,
//                response: Response<List<Ocorrency>>
//            ) {
//                if (response.isSuccessful) {
//                    Log.i("size", "it was sucessful")
//                    ocorrences = response.body()!!
//                }
//            }
//
//            //If not displays a toast
//            override fun onFailure(call: Call<List<Ocorrency>>, t: Throwable) {
//                Log.i("ee", t.message.toString())
//            }
//        })
//
//        request.getAllOcorrences().enqueue(object : Callback<List<Ocorrency>> {
//            override fun onResponse(
//                call: Call<List<Ocorrency>>,
//                response: Response<List<Ocorrency>>
//            ) {
//                if (response.isSuccessful) {
//                    Log.i("size", "it was sucessful")
//                    ocorrences = response.body()!!
//                }
//            }
//
//            //If not displays a toast
//            override fun onFailure(call: Call<List<Ocorrency>>, t: Throwable) {
//                Log.i("ee", t.message.toString())
//            }
//        })
//
//        val ocorrencesViewModel: OcorrencesViewModel = OcorrencesViewModel(ocorrences)
//
//        ocorrencesViewModel.ocorrencyLiveData.observe(this,androidx.lifecycle.Observer{
//            // Update the cached copy of the words in the adapter.
//            adapter.submitList(it)
//        })
    }
}
