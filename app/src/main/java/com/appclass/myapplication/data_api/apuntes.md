
# 📁 data_api – Descripción de Archivos

Este módulo contiene la capa de acceso a datos de la app, donde se define la lógica para conectarse a la API de MapBox.

---

## 📂 api/

### 🔹 `MapBoxApi.kt`
Define la interfaz de Retrofit para acceder a la API de geocodificación de MapBox. Contiene un método `getGeocoding(query, accessToken)` que realiza una petición HTTP a MapBox para obtener datos de ubicación.

### 🔹 `RetrofitInstance.kt`
(Archivo no utilizado directamente en lo mostrado) Normalmente sirve para crear una única instancia de Retrofit, configurada con base URL, conversores, etc. Podrías usarlo en lugar de crear Retrofit manualmente en la UI.

---

## 📂 model/

### 🔹 `GeocodingResponse.kt`
Modelo de datos que representa la respuesta completa de la API de geocodificación de MapBox. Contiene una lista de `Feature`.

### 🔹 `Feature.kt`
Modelo de un lugar geográfico retornado por la API. Incluye el campo `placeName` (nombre completo del lugar) y una `Geometry`.

### 🔹 `Geometry.kt`
Contiene las coordenadas geográficas (`longitude`, `latitude`) del lugar que representa un `Feature`.

---

## 📂 repository/

### 🔹 `MapBoxRepository.kt`
Clase que se encarga de manejar la lógica de acceso a la API. Llama a los métodos de Retrofit (`MapBoxApi`) y le pasa los datos al ViewModel. Aquí se agrega también el `accessToken`.

---

Cada archivo en `data_api` cumple un rol en la arquitectura **MVVM**, concentrando la lógica de red y modelos de datos en esta capa para mantener la app modular y escalable.

---

| API | Necesita Model? | Por qué |
|-----|-----------------|---------|
| Geocoding API | Sí | Devuelve JSON con info compleja |
| Static Images API | No | Devuelve solo imagen (ResponseBody) |


