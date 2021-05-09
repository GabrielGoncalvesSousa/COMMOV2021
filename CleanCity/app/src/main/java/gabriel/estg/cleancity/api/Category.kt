package gabriel.estg.cleancity.api



data class Category(
    var id:Int,
    var name:String,
    var description:String
){
    override fun toString(): String {
        return name
    }
}