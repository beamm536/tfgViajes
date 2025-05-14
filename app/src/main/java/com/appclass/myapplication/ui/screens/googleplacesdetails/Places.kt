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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.appclass.myapplication.data_api.model.googlePlaces.featuredPlaces

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


@Composable
fun GridDestinosEstaticos(navController: NavHostController, modifier: Modifier = Modifier){
    //DESTINOS ESTATICOS - GRID >>> LazyVerticalGrid
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), //2 columnas estaticas
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(featuredPlaces) { place ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("placeDetails/${place.placeId}")
                    },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = place.name, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Pulsa para ver detalles",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

    }
}