package gabriel.estg.cleancity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import gabriel.estg.cleancity.api.*
import gabriel.estg.cleancity.notes.EditNoteActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.Permission
import kotlin.math.log

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

        //Top Navigation Bar
        findViewById<ImageView>(R.id.activityMapsOwnOcorrencesimageView).setOnClickListener {
            startActivity(Intent(applicationContext,ListMyOcorrences::class.java).apply {})
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val sharedPreferences = getSharedPreferences(R.string.sharedPreferences_file_key.toString(),Context.MODE_PRIVATE)

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


        //Store the selected category and subCategory ID
        var selectedCategoryId:Int =0
        var selectedSubCategoryId:Int =0

        //Get Info for the category dropdown
        val getCategories = request.getAllCategories()

        //Stores all the info from the database
        var allOcorrences = listOf<Ocorrency>()
        var allCategories = listOf<Category>()
        var allSubCategories = listOf<SubCategory>()
        var allMarkers = listOf<MyMarker>()


        //Calls the API to get the Categories data
        getCategories.enqueue(object: Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {

                    //Variable that receives the body
                    var categories = listOf<Category>()
                    categories  = response.body()!!
                    allCategories = categories

                    //Add the values to the dropwdown
                    val arrayAdapterCategory= ArrayAdapter(applicationContext,R.layout.dropdown_item, categories)
                    val dropdownViewCategory = findViewById<AutoCompleteTextView>(R.id.MapscategoryDropdown).setAdapter(arrayAdapterCategory)
                }
            }
            //If not displays a toast
            override fun onFailure(call: Call<List<Category>>, t:Throwable){
                Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
            }
        })


        //Gets All Ocorrences
        getOcorrences.enqueue(object: Callback<List<Ocorrency>> {
            override fun onResponse(call: Call<List<Ocorrency>>, response: Response<List<Ocorrency>>) {
                if (response.isSuccessful) {
                    var ocorrences = listOf<Ocorrency>()
                    ocorrences  = response.body()!!
                    allOcorrences = ocorrences

                    //Adds markers for all ocorrences
                    for(ocorrency in allOcorrences){

                        var position = LatLng(ocorrency.latitude.toDouble(), ocorrency.longitude.toDouble())
                        if(ocorrency.user_id==sharedPreferences.getInt("id",0)){

//                            //Call marker
//                            val getNamesforMarkers = request.getNamesforMarkers(ocorrency.id)
//                            getNamesforMarkers.enqueue(object: Callback<List<MyMarker>> {
//                                override fun onResponse(call: Call<List<MyMarker>>, response: Response<List<MyMarker>>) {
//                                    if (response.isSuccessful) {
//
//                                        //Variable that receives the body
//                                        var markers: List<MyMarker> = response.body()!!
//                                        allMarkers = markers
//                                        Log.i("marker",allMarkers.toString())
//                                    }
//                                }
//                                //If not displays a toast
//                                override fun onFailure(call: Call<List<MyMarker>>, t:Throwable){
//                                    Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
//                                }
//                            })

                            val tempMarkerInfo :MarkerInfo = MarkerInfo()
                            tempMarkerInfo.id=ocorrency.id
                            tempMarkerInfo.category= ocorrency.category_id.toString()
                            tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                            tempMarkerInfo.streetName=ocorrency.street
                            tempMarkerInfo.description=ocorrency.description
                            tempMarkerInfo.PointofReference=ocorrency.reference_point
                            tempMarkerInfo.date=ocorrency.date
                            val gson :Gson = Gson()
                            val markerString = gson.toJson(tempMarkerInfo)
                            val markerNames = gson.toJson(allMarkers)


                            mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames).icon(
                                BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                        }else{

                            val tempMarkerInfo :MarkerInfo = MarkerInfo()
                            tempMarkerInfo.id=ocorrency.id
                            tempMarkerInfo.category= ocorrency.category_id.toString()
                            tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                            tempMarkerInfo.streetName=ocorrency.street
                            tempMarkerInfo.description=ocorrency.description
                            tempMarkerInfo.PointofReference=ocorrency.reference_point
                            tempMarkerInfo.date=ocorrency.date
                            val gson :Gson = Gson()
                            val markerString = gson.toJson(tempMarkerInfo)
                            val markerNames = gson.toJson(allMarkers)
                            mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames))
                        }
                    }

                }
            }
            //If not displays a toast
            override fun onFailure(call:Call<List<Ocorrency>>, t:Throwable){
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
            }
        })



        val getSubCategoriesByCategoryId = request.getAllSubCategories()
        getSubCategoriesByCategoryId.enqueue(object: Callback<List<SubCategory>> {
            override fun onResponse(call: Call<List<SubCategory>>, response: Response<List<SubCategory>>) {

                if (response.isSuccessful) {
                    //Variable that receives the body
                    var subCategories = listOf<SubCategory>()
                    subCategories  = response.body()!!
                    allSubCategories=subCategories
                }
            }
            //If not displays a toast
            override fun onFailure(call: Call<List<SubCategory>>, t:Throwable){
                Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
            }
        })

        //Sets a OnItemListener to get different subCategories for the category the user selected
        var categorySelected = findViewById<AutoCompleteTextView>(R.id.MapscategoryDropdown).setOnItemClickListener { parent, view, position, id ->
            val selectedCategory=parent.getItemAtPosition(position) as Category

            //Stores the Id of the selected Item
            selectedCategoryId=selectedCategory.id

            var temp = arrayListOf<SubCategory>()
            for (subcat in allSubCategories){
                if (subcat.category_id == selectedCategoryId) temp.add(subcat)
            }

            mMap.clear()

            for(ocorrency in allOcorrences){
                var position = LatLng(ocorrency.latitude.toDouble(), ocorrency.longitude.toDouble())
                if(ocorrency.user_id==sharedPreferences.getInt("id",0) && ocorrency.category_id == selectedCategoryId){

                    val tempMarkerInfo :MarkerInfo = MarkerInfo()
                    tempMarkerInfo.id=ocorrency.id
                    tempMarkerInfo.category= ocorrency.category_id.toString()
                    tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                    tempMarkerInfo.streetName=ocorrency.street
                    tempMarkerInfo.description=ocorrency.description
                    tempMarkerInfo.PointofReference=ocorrency.reference_point
                    tempMarkerInfo.date=ocorrency.date
                    val gson :Gson = Gson()
                    val markerString = gson.toJson(tempMarkerInfo)
                    val markerNames = gson.toJson(allMarkers)


                    mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames).icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                }else if(ocorrency.category_id==selectedCategoryId){

                    val tempMarkerInfo :MarkerInfo = MarkerInfo()
                    tempMarkerInfo.id=ocorrency.id
                    tempMarkerInfo.category= ocorrency.category_id.toString()
                    tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                    tempMarkerInfo.streetName=ocorrency.street
                    tempMarkerInfo.description=ocorrency.description
                    tempMarkerInfo.PointofReference=ocorrency.reference_point
                    tempMarkerInfo.date=ocorrency.date
                    val gson :Gson = Gson()
                    val markerString = gson.toJson(tempMarkerInfo)
                    val markerNames = gson.toJson(allMarkers)

                    mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames))
                }
            }


            //Sets the subCategory adapter and dropdpown
            val arrayAdapterSubCategory= ArrayAdapter(applicationContext,R.layout.dropdown_item, temp)
            val dropdownViewSubCategory = findViewById<AutoCompleteTextView>(R.id.MapsSubCategoryDropdown).setAdapter(arrayAdapterSubCategory)

        }

        var selectedSubCategory= findViewById<AutoCompleteTextView>(R.id.MapsSubCategoryDropdown).setOnItemClickListener { parent, view, position, id ->
            val subCategorySelected = parent.getItemAtPosition(position) as SubCategory
            selectedSubCategoryId = subCategorySelected.id

            mMap.clear()

            for(ocorrency in allOcorrences){
                var position = LatLng(ocorrency.latitude.toDouble(), ocorrency.longitude.toDouble())
                if(ocorrency.user_id==sharedPreferences.getInt("id",0) && ocorrency.category_id == selectedCategoryId && ocorrency.subCategory_id==selectedSubCategoryId){

                    val tempMarkerInfo :MarkerInfo = MarkerInfo()
                    tempMarkerInfo.id=ocorrency.id
                    tempMarkerInfo.category= ocorrency.category_id.toString()
                    tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                    tempMarkerInfo.streetName=ocorrency.street
                    tempMarkerInfo.description=ocorrency.description
                    tempMarkerInfo.PointofReference=ocorrency.reference_point
                    tempMarkerInfo.date=ocorrency.date
                    val gson :Gson = Gson()
                    val markerString = gson.toJson(tempMarkerInfo)
                    val markerNames = gson.toJson(allMarkers)


                    mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames).icon(
                        BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                }else if(ocorrency.category_id==selectedCategoryId && ocorrency.subCategory_id==selectedSubCategoryId){
                    val tempMarkerInfo :MarkerInfo = MarkerInfo()
                    tempMarkerInfo.id=ocorrency.id
                    tempMarkerInfo.category= ocorrency.category_id.toString()
                    tempMarkerInfo.subCategory=ocorrency.subCategory_id.toString()
                    tempMarkerInfo.streetName=ocorrency.street
                    tempMarkerInfo.description=ocorrency.description
                    tempMarkerInfo.PointofReference=ocorrency.reference_point
                    tempMarkerInfo.date=ocorrency.date
                    val gson :Gson = Gson()
                    val markerString = gson.toJson(tempMarkerInfo)
                    val markerNames = gson.toJson(allMarkers)
                    mMap.addMarker(MarkerOptions().position(position).title(markerString).snippet(markerNames))
                }
            }

        }


        //Implementation of location periodic updates
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0:LocationResult){
                super.onLocationResult(p0)
                lastLocation= p0.lastLocation
                loc= LatLng(lastLocation.latitude,lastLocation.longitude)

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15f))
                mMap.setInfoWindowAdapter(InfoWindowCustom(baseContext))

                //To add a circle to display user location
                if(ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //Request Permission
                    ActivityCompat.requestPermissions(Activity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
                    return
                }else{ mMap.isMyLocationEnabled=true}

                fab.setOnClickListener {
                    startActivity(Intent(applicationContext, AddOcorrency::class.java).apply {
                        putExtra("latitude",loc.latitude)
                        putExtra("longitude",loc.longitude)
                    })
                }
            }
        }

        createLocationRequest()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val zoomLevel = 8.0f
        val viana = LatLng(41.691372, -8.834796)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana,zoomLevel))

    }

    private fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
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