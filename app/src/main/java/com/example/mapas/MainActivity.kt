
//Autor Kevin Garrido

package com.example.mapas

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import kotlin.reflect.typeOf


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val Search =  findViewById<SearchView>(R.id.sv_location)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)





        Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                var location = Search.getQuery().toString();
                var addressList: List<Address>? = null

                if (location !=null || location !=""){
                    val geocoder = Geocoder (this@MainActivity)
                    try {
                        addressList = geocoder.getFromLocationName(location,1)
                    }catch (ex : Exception){
                        ex.printStackTrace()
                        Toast.makeText(this@MainActivity, "No se aagregaron ubicaciones!", Toast.LENGTH_SHORT).show()
                    }
                    val address = addressList!!.get(0)
                    val latLng = LatLng(address.getLatitude(), address.getLongitude())
                    map.addMarker(MarkerOptions().position(latLng).title(location))
                   // map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11F))
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f), 4000, null)
                }
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        mapFragment.getMapAsync(this)

        radio_group?.setOnCheckedChangeListener{radioGroup, idRadioButtom ->
            val isChecked = radioGroup.findViewById<RadioButton>(idRadioButtom).isChecked
            if(isChecked){
                val typeOfOption : Int
                when (idRadioButtom){
                    R.id.normal ->{
                        typeOfOption = GoogleMap.MAP_TYPE_NORMAL
                    }
                    R.id.hybrid ->{
                        typeOfOption = GoogleMap.MAP_TYPE_HYBRID
                    }
                    R.id.satelite ->{
                        typeOfOption = GoogleMap.MAP_TYPE_SATELLITE
                    }
                    R.id.terrain ->{
                        typeOfOption = GoogleMap.MAP_TYPE_TERRAIN
                    }
                    else ->{
                        typeOfOption = GoogleMap.MAP_TYPE_NORMAL
                    }
                }
                map.mapType = typeOfOption
            }
        }
    }




    //Llamada de metodoscuando el mapa cargue
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //cretateMarker()
    }

}



