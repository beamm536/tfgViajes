package com.appclass.myapplication.ui.screens.CRUD_recomendaciones.listar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.appclass.myapplication.data.recomendaciones.UserRecomendation
import com.appclass.myapplication.ui.theme.Poppins

@Composable
fun DetalleRecomendacionesPersonalizadas(
    recomendacion: UserRecomendation,
    onBack: () -> Unit
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFF0FAF6), //0xFFFFD1DC   0xFFD1FFF6
                        Color(0xFF488091)//0xFFB5E0FF
                    )
                ),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(20.dp)

    ) {

        Text(
            text = recomendacion.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(start = 24.dp)) {
            Text(
                text = "Categoría:",
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
            Text(
                text = recomendacion.category,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Ubicación:",
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
            Text(
                text = recomendacion.locationName,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Descripción:",
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins
            )
            Text(
                text = recomendacion.description,
                fontFamily = Poppins
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(48.dp)
                .widthIn(min = 120.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF5198ED), // color superior pra el degradado
                            Color(0xFF346298)  // color inferior - degradado
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 24.dp)
                .clickable{
                    onBack()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Volver",
                color = Color.White
            )

        }

    }
}