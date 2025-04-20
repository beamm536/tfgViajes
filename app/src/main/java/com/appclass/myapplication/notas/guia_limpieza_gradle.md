
# 🧹 Guía de limpieza y diagnóstico de errores en Android Studio (Gradle, Ads, Firebase)

## ✅ Comandos clave de diagnóstico

### 📦 Ver dependencias del proyecto
```bash
./gradlew app:dependencies > deps.txt
```

> Este comando genera un archivo `deps.txt` que contiene **todas las dependencias del proyecto** y sus relaciones internas.

🔍 Luego puedes buscar posibles conflictos con:

```bash
grep "ads" deps.txt
```

O simplemente abrir `deps.txt` con Android Studio y buscar:

- `play-services-ads`
- `mediation-test-suite`
- `com.google.android.ads`

---

## 🚫 Cómo eliminar referencias ocultas a Ads

En tu `build.gradle.kts (app)`:

```kotlin
configurations.all {
    exclude(group = "com.google.android.gms", module = "play-services-ads")
}
```

---

## 🧼 Limpiar completamente el proyecto

1. **Cerrar Android Studio**
2. Borrar manualmente estas carpetas del proyecto:
    - `/app/build`
    - `/.gradle`
    - `/build`
3. Borrar caché global de Gradle (en Windows):
   ```
   C:\Users\TU_USUARIO\.gradle\caches
   ```

4. Volver a abrir Android Studio y ejecutar:

```bash
./gradlew clean
./gradlew build --refresh-dependencies
```

---

## 🔧 Solución al error de Firebase

### Error:
```
Default FirebaseApp is not initialized in this process...
```

### Solución:

1. Descargar `google-services.json` desde [Firebase Console](https://console.firebase.google.com/)
2. Colocarlo en `app/google-services.json`
3. En `build.gradle.kts (Project)`:
```kotlin
plugins {
    id("com.google.gms.google-services") version "4.4.2" apply false
}
```

4. En `build.gradle.kts (app)`:
```kotlin
plugins {
    id("com.google.gms.google-services")
}
```

5. Sync + Clean + Run

---

## 🧠 Recomendación final

- Si ocurre un error en tiempo de ejecución inesperado (`IllegalStateException`, `InvocationTargetException`, etc), revisa:
    - Logcat
    - `deps.txt` en busca de dependencias ocultas
    - Que Firebase esté correctamente inicializado
    - Que ningún ViewModel con constructor personalizado esté invocado sin `ViewModelFactory`

---

> 🎉 ¡Y lo más importante: no te desesperes! Estos errores son normales y solucionables. ¡Has aprendido muchísimo resolviéndolo tú misma!

