package gabriel.estg.cleancity.api

import retrofit2.Call
import retrofit2.http.GET

interface EndPoints{
    //Gets All Users
    @GET("/user/getAllUsers")
    fun getAllUsers() : Call<List<User>>


}