package com.example.helloworld

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.util.*

class Activity_second : AppCompatActivity() {

    private var btn_return: Button? = null
    private var btn_location: Button? = null
    private var text_view_location: TextView? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val PERMISSION_ID = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        btn_return = findViewById(R.id.BTN_return_2)
        btn_location = findViewById(R.id.BTN_location)
        text_view_location = findViewById(R.id.textView_location)

        btn_return?.setOnClickListener {
            finish()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        btn_location?.setOnClickListener {
            RequestPermission()
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if(CheckPermission()) {
            if(isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->
                    var location: Location? = task.result
                    if(location == null) {
                        NewLocationData()
                    } else {
                        text_view_location?.text = "Ваше текущее местоположение: \nДолгота: "+ location.longitude + " , \nШирота: " + location.latitude +
                                " \nСтрана: " + getCountryName(location.latitude,location.longitude) + " \nГород: " + getCityName(location.latitude,location.longitude)
                    }
                }
            } else {
                Toast.makeText(this,"Пожалуйста, включите определение местоположения на вашем устройстве",Toast.LENGTH_SHORT).show()
            }
        } else {
            RequestPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun NewLocationData(){
        var locationRequest =  LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,locationCallback, Looper.myLooper()!!
        )
    }


    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            var lastLocation: Location = locationResult.lastLocation
            text_view_location?.text = "Ваше предыдущее местоположениек: \nДолгота: "+ lastLocation.longitude + " , \nШирота: " + lastLocation.latitude +
                    " \nСтрана: " + getCountryName(lastLocation.latitude,lastLocation.longitude) + " \nГород: " + getCityName(lastLocation.latitude,lastLocation.longitude)
        }
    }

    private fun getCityName(lat: Double,long: Double):String {
        var cityName:String = ""
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        cityName = Adress.get(0).locality
        countryName = Adress.get(0).countryName
        return cityName
    }

    private fun getCountryName(lat: Double,long: Double):String {
        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat,long,3)

        countryName = Adress.get(0).countryName
        return countryName
    }

    fun CheckPermission(): Boolean {
        //this function will return a boolean
        //true: if we have permission
        //false if not
        if( ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            return true
        }
        return false
    }

    fun RequestPermission() {
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID )
    }

    fun isLocationEnabled(): Boolean {
        //this function will return to us the state of the location service
        //if the gps or the network provider is enabled then it will return true otherwise it will return false
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}