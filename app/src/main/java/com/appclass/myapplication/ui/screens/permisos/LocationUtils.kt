package com.appclass.myapplication.ui.screens.permisos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


/**
 * archivo utilitario para la obtencion de la ubicacion
 */
fun getUserLocation(
    context: Context,
    onLocationReady: (lat: Double, lng: Double) -> Unit
) {
    val fusedClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    if (
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
    ) {
        return // No hay permisos
    }

    fusedClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            Log.d("GPS", "Ubicación obtenida: ${it.latitude}, ${it.longitude}")
            onLocationReady(it.latitude, it.longitude)
        } ?: Log.e("GPS", "UBICACION ES NULL")
    }
}
