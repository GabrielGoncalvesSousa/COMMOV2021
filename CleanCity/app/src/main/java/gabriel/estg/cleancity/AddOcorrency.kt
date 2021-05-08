package gabriel.estg.cleancity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import gabriel.estg.cleancity.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddOcorrency : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ocorrency)

        //Get text inputs from textViews
        var editTextStreetName = findViewById<EditText>(R.id.editTextStreetName).text
        var editTextPointOfReference = findViewById<EditText>(R.id.editTextPointOfReference).text
        var editTextDescription = findViewById<EditText>(R.id.editTextDescription).text



        //Get Data From Maps Activity
        var latitude: Double = intent.getDoubleExtra("latitude",0.0)
        var longitude: Double = intent.getDoubleExtra("longitude",0.0)



        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.addOcorrencyToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java).apply {})
        }

        val request = ServiceBuilder.buildService(EndPoints::class.java)

        val categorias = arrayListOf<Category>()
        val nomesCategorias = arrayListOf<String>()

        val subCategorias= arrayListOf<SubCategory>()
        val nomesSubCategorias = arrayListOf<String>()

        //Get Info for the category dropdown
        val getCategories = request.getAllCategories()

        //Sees if the register was successful
        getCategories.enqueue(object: Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {

                if (response.isSuccessful) {

                    //Variable that receives the body
                    var categories = listOf<Category>()
                    categories  = response.body()!!

                    //Variable that transforms the body to an arrayList
                    for(category in categories){
                        categorias.add(category)
                    }

                    //Variable that stores the names
                    for(categoria in categorias){
                        nomesCategorias.add(categoria.name)
                    }
                }
            }
            //If not displays a toast
            override fun onFailure(call: Call<List<Category>>, t:Throwable){
                Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
            }
        })

        //Get Info for the category dropdown
        val getSubCategories = request.getAllSubCategories()

        //Sees if the register was successful
        getSubCategories.enqueue(object: Callback<List<SubCategory>> {
            override fun onResponse(call: Call<List<SubCategory>>, response: Response<List<SubCategory>>) {

                if (response.isSuccessful) {

                    //Variable that receives the body
                    var subCategories = listOf<SubCategory>()
                    subCategories  = response.body()!!

                    //Variable that transforms the body to an arrayList
                    for(subCategory in subCategories){
                        subCategorias.add(subCategory)
                    }

                    //Variable that stores the names
                    for(subCategoria in subCategorias){
                        nomesSubCategorias.add(subCategoria.name)
                    }
                }
            }
            //If not displays a toast
            override fun onFailure(call: Call<List<SubCategory>>, t:Throwable){
                Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
            }
        })


        //Add the values to the dropwdown
        val arrayAdapterCategory= ArrayAdapter(this,R.layout.dropdown_item,nomesCategorias)
        val dropdownViewCategory = findViewById<AutoCompleteTextView>(R.id.categoryDropdown).setAdapter(arrayAdapterCategory)

        val arrayAdapterSubCategory= ArrayAdapter(this,R.layout.dropdown_item,nomesSubCategorias)
        val dropdownViewSubCategory = findViewById<AutoCompleteTextView>(R.id.SubCategoryDropdown).setAdapter(arrayAdapterSubCategory)


        //Back Button
        var backButton=findViewById<Button>(R.id.buttonAddOcorrenceBack).setOnClickListener {
            startActivity(Intent(applicationContext, MapsActivity::class.java).apply {})
        }

        //Next Button
        var nextButton=findViewById<Button>(R.id.buttonAddOcorrenceNext).setOnClickListener {
            //Formatter of date
            val currentDateTime= LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedDateTime = currentDateTime.format(formatter)

            //Calls service to know occurrences
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val addOcorrency= request.addNewOcorrency(1,1,1,"Uma foto",editTextStreetName.toString(),editTextPointOfReference.toString(),editTextDescription.toString()
            ,latitude.toString(),longitude.toString(),formattedDateTime)


            //Sees if the register was successful
            addOcorrency.enqueue(object: Callback<Ocorrency> {
                override fun onResponse(call: Call<Ocorrency>, response: Response<Ocorrency>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, R.string.registerSuccesfull, Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, MapsActivity::class.java).apply {
                        })
                    }
                }
                //If not displays a toast
                override fun onFailure(call: Call<Ocorrency>, t:Throwable){
                    Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
                }
            })
        }

    }


}