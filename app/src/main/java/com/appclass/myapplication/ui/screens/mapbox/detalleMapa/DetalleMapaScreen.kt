package com.appclass.myapplication.ui.screens.mapbox.detalleMapa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.appclass.myapplication.ui.screens.login.LoginViewModel
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.generated.HillshadeLayer
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.sources.generated.RasterDemSource
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.terrain.generated.Terrain
import com.mapbox.maps.extension.style.terrain.generated.setTerrain

@Composable
fun DetalleMapaScreen(
    //viewModel: DetalleMapaViewModel = viewModel(),
    nombre: String,
    lat: Double,
    lon: Double,
    viewModel: DetalleMapaViewModel
){
    LaunchedEffect(Unit) {
        viewModel.cargarLugar(nombre, lat, lon)
    }

    val lugar = viewModel.lugar

    Box(modifier = Modifier.fillMaxSize()) {
        if (lugar != null) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        getMapboxMap().loadStyleUri(Style.SATELLITE_STREETS) { style ->

                            val terrainSource = RasterDemSource.Builder("terrain-source")
                                .url("mapbox://mapbox.terrain-rgb")
                                .tileSize(512)
                                .build()

                            style.addSource(terrainSource)

                            val terrain = Terrain("terrain-source")
                                .exaggeration(1.5)

                            style.setTerrain(terrain)

                            val hillshade = HillshadeLayer("hillshade", "terrain-source")
                            style.addLayer(hillshade)

                            mapboxMap.setCamera(
                                CameraOptions.Builder()
                                    .center(Point.fromLngLat(lugar.lon, lugar.lat))
                                    .zoom(14.0)
                                    .pitch(60.0)
                                    .build()
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "${lugar.nombre}\n(${lugar.lat}, ${lugar.lon})",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(8.dp),
                color = Color.White
            )
        }
    }
}