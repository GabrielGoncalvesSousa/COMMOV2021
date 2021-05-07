package gabriel.estg.cleancity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.Ocorrency
import gabriel.estg.cleancity.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val getOcorrences = request.getAllOcorrences()
        var position:LatLng




        //Fab Listener
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener({
            startActivity(Intent(this, AddOcorrency::class.java).apply {})
        })

        //GetOcorrences to display markers
        getOcorrences.enqueue(object: Callback<List<Ocorrency>> {
            override fun onResponse(call: Call<List<Ocorrency>>, response: Response<List<Ocorrency>>) {
                if (response.isSuccessful) {
                    var ocorrences = listOf<Ocorrency>()
                    ocorrences  = response.body()!!
                    for(ocorrency in ocorrences){
                        position = LatLng(ocorrency.latitude.toDouble(), ocorrency.longitude.toDouble())
                        mMap.addMarker(MarkerOptions().position(position).title(ocorrency.street))
                    }
                }
            }

            //If not displays a toast
            override fun onFailure(call:Call<List<Ocorrency>>, t:Throwable){
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val zoomLevel = 8.0f

        // Add a marker in Sydney and move the camera
        val viana = LatLng(41.691372, -8.834796)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana,zoomLevel))


    }
}