package com.appclass.myapplication.ui.screens.permisos

import androidx.compose.runtime.Composable
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun SolicitudPermisosUbicacion(onPermissionGranted: @Composable () -> Unit){

    val context = LocalContext.current
    val activity = context as Activity

    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                locationPermission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var shouldShowComposable by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted.value = isGranted
        if (isGranted) {
            //onPermissionGranted()
            shouldShowComposable = true
        }
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted.value) {
            permissionLauncher.launch(locationPermission)
        } else {
            //onPermissionGranted()
            shouldShowComposable = true
        }
    }

    if (shouldShowComposable) {
        onPermissionGranted()
    }
}