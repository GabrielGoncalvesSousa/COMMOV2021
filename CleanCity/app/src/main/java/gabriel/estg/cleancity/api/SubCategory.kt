package gabriel.estg.cleancity.api


 data class SubCategory(
    var id:Int,
    var category_id:Int,
    var name:String,
    var description:String

){
     override fun toString(): String {
         return name
     }
 }