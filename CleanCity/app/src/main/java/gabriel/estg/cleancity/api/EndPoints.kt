package gabriel.estg.cleancity.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    //Gets All Users
    @GET("user/getAllUsers")
    fun getAllUsers(): Call<List<User>>

    //Login
    @GET("user/login/{username}&&{password}")
    fun login(
        @Path("username") username: String,
        @Path("password") password: String
    ): Call<List<User>>

    //Register
    @FormUrlEncoded
    @POST("user/addNewUser")
    fun addNewUser(
        @Field("username") username: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("notification") notification: Boolean
    ) : Call<User>

    //Get All Occurrences
    @GET("ocorrency/getAllOcorrences")
    fun getAllOcorrences(): Call<List<Ocorrency>>

    //Get All Categories
    @GET("category/getAllCategories")
    fun getAllCategories() : Call<List<Category>>

    //Get All SubCategories
    @GET("category/getAllSubCategories")
    fun getAllSubCategories() : Call<List<SubCategory>>


}