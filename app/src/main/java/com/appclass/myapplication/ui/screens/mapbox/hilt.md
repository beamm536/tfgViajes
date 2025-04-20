
#### HILT
- HILT es una librería que nos permite inyectar dependencias automatica en nuestro código


lo voy a implementar para la inyeccion de dependencias en el repositorio de MapBox,
para el cual, voy a cambiar el build.gradle.kts de la siguiente manera:

```
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

