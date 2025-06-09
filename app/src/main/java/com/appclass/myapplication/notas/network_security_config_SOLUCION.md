# Solución al error "CLEARTEXT communication not permitted" en Android para backend local

## Contexto

- Tienes un backend FastAPI corriendo en la IP local `192.168.0.35:8000`.
- La app Android intenta hacer peticiones HTTP (sin HTTPS) a esta IP.
- En Android 9+ (API 28+), por defecto, **no se permiten peticiones HTTP no seguras**.
- Esto genera el error:


CLEARTEXT communication to 192.168.0.35 not permitted by network security policy


---

## ¿Por qué ocurre?

Android bloquea el tráfico `http://` por seguridad para evitar que datos sensibles viajen sin cifrar, a menos que se configure explícitamente permitirlo para ciertas direcciones.

---

## Cómo solucionarlo

### Paso 1: Crear un archivo de configuración de seguridad de red

Crear el archivo `res/xml/network_security_config.xml` con el siguiente contenido:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">192.168.0.35</domain>
    </domain-config>
</network-security-config>
```

Este archivo indica que para el dominio (IP) 192.168.0.35 se permite tráfico HTTP sin cifrar.


Paso 2: Referenciar el archivo en el AndroidManifest.xml
Dentro de la etiqueta <application>, añadir el atributo:

xml
```
android:networkSecurityConfig="@xml/network_security_config"
```

```
<application
    ...
    android:networkSecurityConfig="@xml/network_security_config"
    ... >
    ...
</application>
```

---
---
Resumen
El problema fue causado por la política de seguridad de red de Android que bloquea tráfico HTTP no seguro. La solución fue configurar la app para permitir tráfico cleartext explícitamente hacia la IP local del backend.


