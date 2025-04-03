
# 🧭 UI MapBox – Descripción de Archivos

Estos archivos componen la parte visual (UI) y lógica de presentación relacionada con la pantalla de búsqueda de lugares usando MapBox.

---

## 📄 `MapBox.kt`
Archivo principal de la interfaz de usuario (UI). Aquí se definen dos funciones composables:

- `MapBoxScreen()`: Configura Retrofit, el repositorio y el ViewModel.
- `MapBox()`: Muestra un `TextField` para buscar lugares, un botón para lanzar la búsqueda, y una lista de resultados (`placeName`) obtenidos de la API.

---

## 📄 `MapBoxViewModel.kt`
Clase `ViewModel` que gestiona la lógica de presentación. Se comunica con el repositorio para obtener datos de geolocalización y expone un `StateFlow` con los resultados (`GeocodingResponse`) a la UI.

---

## 📄 `MapBoxViewModelFactory.kt`
Factory personalizada que permite inyectar el `MapBoxRepository` dentro del `MapBoxViewModel`. Es necesaria porque el ViewModel tiene parámetros personalizados y no puede ser instanciado con el constructor por defecto.

---

Todos estos archivos trabajan juntos siguiendo el patrón **MVVM**, donde:

- La **vista** (`MapBox.kt`) observa al ViewModel.
- El **ViewModel** pide los datos al repositorio.
- Y el **repositorio** consulta la API externa usando Retrofit.

