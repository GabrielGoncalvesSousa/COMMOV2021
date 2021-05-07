package gabriel.estg.cleancity.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoints{
    //Gets All Users
    @GET("/user/getAllUsers")
    fun getAllUsers() : Call<List<User>>

    //Login
    @GET("user/login/{username}&&{password}")
    fun login(@Path("username") username:String, @Path("password") password:String) : Call<List<User>>

}