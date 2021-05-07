package gabriel.estg.cleancity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.widget.Toolbar
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.ServiceBuilder
import gabriel.estg.cleancity.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val request = ServiceBuilder.buildService(EndPoints::class.java)

        //Get TextViews
        val username = findViewById<TextView>(R.id.editTextRegisterUsername)
        val firstName = findViewById<TextView>(R.id.editTextRegisterFirstName)
        val lastName = findViewById<TextView>(R.id.editTextRegisterLastName)
        val email = findViewById<TextView>(R.id.editTextRegisterEmail)
        val password = findViewById<TextView>(R.id.editTextRegisterPassword)
        val passwordVerification = findViewById<TextView>(R.id.editTextRegisterPasswordVerification)
        val checkBox = findViewById<CheckBox>(R.id.registerCheckBox)

        //Register Button Listener
        val registerButton = findViewById<Button>(R.id.registerButtonRegister).setOnClickListener{
            if(TextUtils.isEmpty(username.text) || TextUtils.isEmpty(firstName.text) || TextUtils.isEmpty(lastName.text)||
                    TextUtils.isEmpty(email.text) || TextUtils.isEmpty(password.text) || TextUtils.isEmpty(passwordVerification.text)){

                Toast.makeText(applicationContext, R.string.loginMissingFields, Toast.LENGTH_LONG).show()

            }else if ( !TextUtils.equals(password.text,passwordVerification.text)){
                Toast.makeText(applicationContext, R.string.registerPasswordDontMatch, Toast.LENGTH_LONG).show()
            }else if(!checkBox.isChecked){
                Toast.makeText(applicationContext, R.string.registerMissingCheckbox, Toast.LENGTH_LONG).show()
            }else{
                val register = request.addNewUser(username.text.toString(),firstName.text.toString(),lastName.text.toString(),email.text.toString(),password.text.toString(),false)

                //Sees if the register was successful
                register.enqueue(object: Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            var c = listOf<User>()
                            c+=response.body()!!
                            Toast.makeText(applicationContext, R.string.registerSuccesfull, Toast.LENGTH_LONG).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                            })
                        }
                    }

                    //If not displays a toast
                    override fun onFailure(call: Call<User>, t:Throwable){
                        Toast.makeText(applicationContext, R.string.registerNotSuccesfull, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        // loginRoundImage
        val loginRoundImage = findViewById<ImageView>(R.id.registerImageViewLogin).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java).apply {})
        }

        //Toolbar
        var toolbar = findViewById<Toolbar>(R.id.registerToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_action_back)
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).apply {})
        }
    }
}