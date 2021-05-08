package gabriel.estg.cleancity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
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
import gabriel.estg.cleancity.notes.EditNoteActivity
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
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //Initiate fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Initiate Location variable of occurrences
        var position:LatLng

        //Initiate Location variable of the user
        var loc:LatLng

        //Calls service to know occurrences
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val getOcorrences = request.getAllOcorrences()


        //Fab Listener
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)


        //Implementation of location periodic updates
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0:LocationResult){
                super.onLocationResult(p0)
                lastLocation= p0.lastLocation
                loc= LatLng(lastLocation.latitude,lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,15.0f))
                Log.i("e","Coords - "+loc.latitude + " - " + loc.longitude)

                fab.setOnClickListener {
                    startActivity(Intent(applicationContext, AddOcorrency::class.java).apply {
                        putExtra("latitude",loc.latitude)
                        putExtra("longitude",loc.longitude)
                    })
                }
            }
        }

        createLocationRequest()

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

//        setUpMap()


    }

//    fun setUpMap(){
//        //Verificar se utilizador deu permissao
//        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            //Request Permission
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
//            return
//        }else{
//            mMap.isMyLocationEnabled=true
//            fusedLocationClient.lastLocation.addOnSuccessListener(this) {location->
//                if(location!=null){
//                    lastLocation=location
//                    Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_LONG).show()
//                    val currentLatLng= LatLng (location.latitude, location.longitude)
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f))
//                }
//            }
//
//        }
//    }

    private fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            mMap.isMyLocationEnabled=true
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval=10000
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

}