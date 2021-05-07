package gabriel.estg.cleancity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
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

        val request = ServiceBuilder.buildService(EndPoints::class.java)


        //Listener for login button
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {

            //Getting the values from the textViews
            val username = findViewById<TextView>(R.id.editTextUsername)
            val password = findViewById<TextView>(R.id.editTextTextPassword)
            val checkBox = findViewById<CheckBox>(R.id.checkBox)

            //Checking if all values are not empty
            if(TextUtils.isEmpty(username.text) || TextUtils.isEmpty(password.text)){
                Toast.makeText(applicationContext, R.string.loginMissingFields, Toast.LENGTH_LONG).show()

            //Checking if checkbox is checked
            }else if(!checkBox.isChecked){
                Toast.makeText(applicationContext, R.string.loginMissingCheckbox, Toast.LENGTH_LONG).show()

            //Requests a call to the API with the values from the textViews
            }else{
                val login = request.login(username.text.toString(),password.text.toString())

                //Sees if the login was successful
                login.enqueue(object: Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful) {
                            var c = listOf<User>()
                            c+=response.body()!!
                            Toast.makeText(applicationContext, c[0].first_name.toString(), Toast.LENGTH_LONG).show()
                            startActivity(Intent(applicationContext, HomePage::class.java).apply {
                            })
                        }
                    }

                    //If not displays a toast
                    override fun onFailure(call:Call<List<User>>, t:Throwable){
                        Toast.makeText(applicationContext, R.string.loginWrongCredentials, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        //Listener for register button
        findViewById<ImageView>(R.id.imageViewRegisterLocation).setOnClickListener {
            Toast.makeText(applicationContext, "Register Clicked", Toast.LENGTH_LONG).show()
        }

        //Listener for notes button
        findViewById<Button>(R.id.buttonNotes).setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java).apply {
            })
        }

    }


}