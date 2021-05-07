package gabriel.estg.cleancity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import gabriel.estg.cleancity.api.*
import gabriel.estg.cleancity.notes.NotesActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOcorrency : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ocorrency)


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


    }


}