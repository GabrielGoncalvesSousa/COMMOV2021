package gabriel.estg.cleancity.api

import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime

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


    //Get All SubCategories by Category Id
    @GET("category/getAllSubCategoriesByCategory/{category_id}")
    fun getAllSubCategoriesByCategoryId(
        @Path("category_id") category_id: Int
    ) : Call<List<SubCategory>>


    //Add Ocorrency
    @FormUrlEncoded
    @POST("ocorrency/addNewOcorrency")
    fun addNewOcorrency(
        @Field("user_id") user_id: Int,
        @Field("category_id") category_id: Int,
        @Field("subCategory_id") subCategory_id: Int,
        @Field("foto") foto: String,
        @Field("street") street: String,
        @Field("reference_point") reference_point: String,
        @Field("description") description: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("date") date: String
    ) : Call<Ocorrency>


}