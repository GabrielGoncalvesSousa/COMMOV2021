package gabriel.estg.cleancity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import gabriel.estg.cleancity.api.EndPoints
import gabriel.estg.cleancity.api.MyMarker
import gabriel.estg.cleancity.api.ServiceBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InfoWindowCustom(var context: Context) : InfoWindowAdapter {
    var inflater: LayoutInflater? = null

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    //Calls service to know occurrences
    val request = ServiceBuilder.buildService(EndPoints::class.java)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getInfoWindow(marker: Marker): View? {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v: View = inflater!!.inflate(R.layout.marker_window_layout, null)
        var category = v.findViewById<View>(R.id.ocorenceCategoryBD) as TextView
        val subCategory = v.findViewById<View>(R.id.ocorrenceReciclerSubCategoryBD) as TextView
        val streetName = v.findViewById<View>(R.id.ocorrenceReciclerStreetBd) as TextView
        val pointOfRerefence = v.findViewById<View>(R.id.ocorrenceReciclerPointOfReferenceBD) as TextView
        val description = v.findViewById<View>(R.id.ocorrenceReciclerDescriptionBD) as TextView
        val date = v.findViewById<View>(R.id.ocorrenceReciclerDateDB) as TextView
        val id = v.findViewById<View>(R.id.ocorrenceId) as TextView

        val gson= Gson()


        var markerInfoObject = gson.fromJson(marker.title,MarkerInfo::class.java)
//        var markerNames = gson.fromJson(marker.snippet,MyMarker::class.java)

//        markerNames

        //Formatter of date
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedDateTime = markerInfoObject.date?.format(formatter)


        //Names of the current pokfpeoi4jrf


        category.text = markerInfoObject.category
        subCategory.text = markerInfoObject.subCategory
        streetName.text = markerInfoObject.streetName
        pointOfRerefence.text = markerInfoObject.PointofReference
        description.text = markerInfoObject.description
        date.text = formattedDateTime.toString()
        id.text = markerInfoObject.id.toString()

       return v
    }
}

