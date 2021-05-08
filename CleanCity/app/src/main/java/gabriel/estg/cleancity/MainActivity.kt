package gabriel.estg.cleancity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.ServiceBuilder
import gabriel.estg.cleancity.api.User
import gabriel.estg.cleancity.notes.NotesActivity
import org.json.JSONArray
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import gabriel.estg.cleancity.MainActivity as MainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variable to request to endpoints
        val request = ServiceBuilder.buildService(EndPoints::class.java)

        //Variable sharedPreferences
        val sharedPreferences = getSharedPreferences(R.string.sharedPreferences_file_key.toString(),Context.MODE_PRIVATE)

        val myBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY",false)
        val myId = sharedPreferences.getInt("id",0)

        if(myBoolean && myId!=0){
            startActivity(Intent(applicationContext, MapsActivity::class.java).apply {})
        }

        //Listener for login button
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {

            //Getting the values from the textViews
            val username = findViewById<TextView>(R.id.editTextUsername)
            val password = findViewById<TextView>(R.id.editTextTextPassword)
            val rememberMe = findViewById<CheckBox>(R.id.rememberMe)

            //Checking if all values are not empty
            if(TextUtils.isEmpty(username.text) || TextUtils.isEmpty(password.text)){
                Toast.makeText(applicationContext, R.string.loginMissingFields, Toast.LENGTH_LONG).show()
            }else{
                val login = request.login(username.text.toString(),password.text.toString())

                //Sees if the login was successful
                login.enqueue(object: Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext,R.string.loginSuccessful, Toast.LENGTH_LONG).show()
                            val responseBody = response.body()!!

                            if(findViewById<CheckBox>(R.id.rememberMe).isChecked ){
                                saveData(responseBody[0].id)
                            }

                            startActivity(Intent(applicationContext, MapsActivity::class.java).apply {})
                        }
                    }

                    //If not displays a toast
                    override fun onFailure(call:Call<List<User>>, t:Throwable){
                        Toast.makeText(applicationContext, R.string.loginWrongCredentials, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        //Listener for register Image button
        findViewById<ImageView>(R.id.imageViewRegisterLocation).setOnClickListener {
            startActivity(Intent(this,Register::class.java).apply {})
        }


        //Listener for register button
        findViewById<Button>(R.id.buttonRegister).setOnClickListener {
            startActivity(Intent(this,Register::class.java).apply {})
        }

        //Listener for notes button
        findViewById<Button>(R.id.buttonNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {
            })
        }

    }

    private fun saveData(id:Int){
        val sharedPreferences = getSharedPreferences(R.string.sharedPreferences_file_key.toString(),Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putInt("id", id)
            putBoolean("BOOLEAN_KEY",true)
        }.apply()
    }

}