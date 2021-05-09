package gabriel.estg.cleancity

import android.content.Context
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

        //Shared Preferences
        val sharedPreferences = getSharedPreferences(R.string.sharedPreferences_file_key.toString(), Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("id",0)

        //The endpoint request
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        //Get text inputs from textViews
        var editTextStreetName = findViewById<EditText>(R.id.editTextStreetName).text
        var editTextPointOfReference = findViewById<EditText>(R.id.editTextPointOfReference).text
        var editTextDescription = findViewById<EditText>(R.id.editTextDescription).text

        //Get Data From Maps Activity
        var latitude: Double = intent.getDoubleExtra("latitude",0.0)
        var longitude: Double = intent.getDoubleExtra("longitude",0.0)

        //Store the selected category and subCategory ID
        var selectedCategoryId:Int =0
        var selectedSubCategoryId:Int =0

        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.addOcorrencyToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java).apply {})
        }

        //Get Info for the category dropdown
        val getCategories = request.getAllCategories()

        //Calls the API to get the Categories data
        getCategories.enqueue(object: Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {

                    //Variable that receives the body
                    var categories = listOf<Category>()
                    categories  = response.body()!!

                    //Add the values to the dropwdown
                    val arrayAdapterCategory= ArrayAdapter(applicationContext,R.layout.dropdown_item, categories)
                    val dropdownViewCategory = findViewById<AutoCompleteTextView>(R.id.categoryDropdown).setAdapter(arrayAdapterCategory)
                }
            }
            //If not displays a toast
            override fun onFailure(call: Call<List<Category>>, t:Throwable){
                Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
            }
        })

        //Sets a OnItemListener to get different subCategories for the category the user selected
        var categorySelected = findViewById<AutoCompleteTextView>(R.id.categoryDropdown).setOnItemClickListener { parent, view, position, id ->
            val selectedCategory=parent.getItemAtPosition(position) as Category

            //Stores the Id of the selected Item
            selectedCategoryId=selectedCategory.id

            //Calls the API to Get Info for the subCategory by category ID
            val getSubCategoriesByCategoryId = request.getAllSubCategoriesByCategoryId(selectedCategory.id)

            getSubCategoriesByCategoryId.enqueue(object: Callback<List<SubCategory>> {
                override fun onResponse(call: Call<List<SubCategory>>, response: Response<List<SubCategory>>) {

                    if (response.isSuccessful) {

                        //Variable that receives the body
                        var subCategories = listOf<SubCategory>()
                        subCategories  = response.body()!!

                        //Sets the subCategory adapter and dropdpown
                        val arrayAdapterSubCategory= ArrayAdapter(applicationContext,R.layout.dropdown_item,subCategories)
                        val dropdownViewSubCategory = findViewById<AutoCompleteTextView>(R.id.SubCategoryDropdown).setAdapter(arrayAdapterSubCategory)

                    }
                }
                //If not displays a toast
                override fun onFailure(call: Call<List<SubCategory>>, t:Throwable){
                    Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
                }
            })
        }

        var selectedSubCategory= findViewById<AutoCompleteTextView>(R.id.SubCategoryDropdown).setOnItemClickListener { parent, view, position, id ->
            val subCategorySelected = parent.getItemAtPosition(position) as SubCategory
            selectedSubCategoryId = subCategorySelected.id
        }

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
            val addOcorrency= request.addNewOcorrency(user_id,selectedCategoryId,selectedSubCategoryId,"foto",editTextStreetName.toString(),editTextPointOfReference.toString(),editTextDescription.toString()
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