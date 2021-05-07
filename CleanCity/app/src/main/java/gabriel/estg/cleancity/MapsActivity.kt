package gabriel.estg.cleancity

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import java.security.Permission

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // Implementation of last know location
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //Implementation of location periodic updates



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //Initiate fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val getOcorrences = request.getAllOcorrences()
        var position:LatLng




        //Fab Listener
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            startActivity(Intent(this, AddOcorrency::class.java).apply {})
        }

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

        val viana = LatLng(41.691372, -8.834796)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana,zoomLevel))

        setUpMap()


    }

    fun setUpMap(){
        //Verificar se utilizador deu permissao
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Request Permission
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            return
        }else{
            mMap.isMyLocationEnabled=true
            fusedLocationClient.lastLocation.addOnSuccessListener(this) {location->
                if(location!=null){
                    lastLocation=location
                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_LONG).show()
                    val currentLatLng= LatLng (location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f))
                }
            }

        }
    }
}