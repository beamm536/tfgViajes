package com.appclass.myapplication.ui.screens.googleplacesdetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // JODERRR CUIDADO CON LOS IMPORTSSS :)
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.appclass.myapplication.data_api.model.googlePlaces.featuredPlaces
import com.appclass.myapplication.ui.utils.buildImageUrl

/*
clase que contiene la lista de lugares destacados - lista estática
 */
@Composable
fun Places(navController: NavHostController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "🌍 Lugares destacados - places",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        GridDestinosEstaticos(navController, modifier = Modifier)

        // DESTINOS ESTATICOS - EN LISTA
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
//            items(featuredPlaces) { place ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            navController.navigate("placeDetails/${place.placeId}")
//                        },
//                    elevation = CardDefaults.cardElevation(4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = place.name, style = MaterialTheme.typography.titleMedium)
//                        Text(
//                            text = "Pulsa para ver detalles",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//            }
//        }



    }
}

//cada destino tiene su propio photo_reference >>> al igual que la vista que contiene los detalles,
//los parametros son los mismos pero se cambia contenido

@Composable
fun GridDestinosEstaticos(navController: NavHostController, modifier: Modifier = Modifier){
    //DESTINOS ESTATICOS - GRID >>> LazyVerticalGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), //2 columnas estaticas
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        val apiKey = "AIzaSyBWNsg0IDYVrLyi3KokioyvmvCu6iH78AM"

        items(featuredPlaces) { place ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // <-- Altura fija para uniformidad
                    .clickable {
                        navController.navigate("placeDetails/${place.placeId}")
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                /**
                 * ¿PQ EN ESTA FUNCION O EN ESTA PARTE DE LOGICA AL PASARLE LOS PARAMETROS DE PHOTO Y PHOTO REFERENCE DA ERROR ?
                 *
                 * En la vista que contiene los detalles no nos ocasiona ningun problema con estos parametros, yq que no los obtiene del
                 * mismo archivo. Es decir, PlacesDetails, obtiene los datos de viewModel.PlaceResult
                 *
                 * Sin embargo esta vista de Places, al usar la lista de featuredPlaces estatica que ya establecimos, no coge los parametros
                 * del mismo lugar y seguramente no estén ni definidos estos parametros.
                 * Ya que al principio nada mas que se usaban en la vista el placeId y el name, que se encuentran en la clase PLACEITEM
                 * >>> dnd definimos que es lo que va a contener cada item que hemos establecido dentro del LazyVerticalGrid
                 *
                 */


                Column(modifier = Modifier.fillMaxSize()) {
                    // Imagen superior
//                    place.photos?.firstOrNull()?.photo_reference?.let { ref ->
//                        val imageUrl = buildImageUrl(ref, apiKey)
                        AsyncImage(
                            model = place.imageUrl, //directamente llamamos a la url de la img definida en featured places
                            contentDescription = "Foto del lugar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                //TODO: VER LA POSIBILIDAD DE GUARDAR LAS IMAGENES EN LOCAL - DATASTORE

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2
                    )
                }
        }

    }
}