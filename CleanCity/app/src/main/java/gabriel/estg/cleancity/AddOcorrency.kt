package gabriel.estg.cleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class AddOcorrency : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ocorrency)


        //Spinners
        var category = arrayOf("Iluminacao", "Ruas");
        var Subcategory = arrayOf("Algo", "Algo2");


        var spinnerArrayAdapterCategory=
            ArrayAdapter(this,android.R.layout.simple_spinner_item,category)
        spinnerArrayAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var spinnerCategory=findViewById<Spinner>(R.id.spinnerCategory)

        with(spinnerCategory){
            adapter=spinnerArrayAdapterCategory
            setSelection(0,false)
            prompt= "Select the category"
        }


        findViewById<Spinner>(R.id.spinnerSubCategory)

        var backButton=findViewById<Button>(R.id.buttonAddOcorrenceBack).setOnClickListener({
            startActivity(Intent(applicationContext, MapsActivity::class.java).apply {})
        })

    }


}