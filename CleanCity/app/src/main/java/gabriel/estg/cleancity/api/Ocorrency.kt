package gabriel.estg.cleancity.api

import java.util.*

data class Ocorrency(
    var id:Int,
    var category_id:Int,
    var user_id:Int,
    var foto:String,
    var street:String,
    var reference_point:String,
    var description:String,
    var latitude:String,
    var longitude:String,
    var date:String
)