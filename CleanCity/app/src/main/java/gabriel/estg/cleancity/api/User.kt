package gabriel.estg.cleancity.api

data class User(
    val id:Int,
    val first_name:String,
    val last_name:String,
    val email:String,
    val password:String,
    val notification:Boolean
)