# 🔄 La IP del ordenador cambia en cada conexión (si no es estática), y tu código sigue apuntando a la anterior.



¡Exacto! 💡 Ese es el típico culpable silencioso en redes domésticas: las IPs privadas cambian si no están fijadas manualmente.

🔁 ¿Por qué cambia la IP?
Porque por defecto, el router asigna IPs dinámicamente usando DHCP, y al reiniciar el ordenador o reconectarse a la red, puede que el router te dé otra IP (como pasar de 192.168.0.173 a 192.168.0.174).

✅ ¿Cómo lo solucionas?
Tienes 2 opciones:

## 🛠️ Opción 1: Fijar IP local (recomendado si siempre usas la misma red)
Ve a la configuración de red de tu sistema operativo.

Busca la opción para configurar IP manual/estática.

Asigna, por ejemplo:

IP: 192.168.0.50

Máscara: 255.255.255.0

Gateway: 192.168.0.1 (suele ser la IP del router)

Reinicia conexión y asegúrate de que siempre obtienes la misma IP.

Así siempre podrás usar esa IP fija en tu .baseUrl() sin tener que revisarla.

## 🛠️ Opción 2: Revisar IP antes de cada prueba
Ejecuta ifconfig (Mac/Linux) o ipconfig (Windows) antes de correr la app.

Actualiza temporalmente tu .baseUrl(...) en Retrofit con la nueva IP.

## 🧠 Bonus (Pro Tip)
Puedes incluso hacer que tu app detecte automáticamente la IP de un servidor en red con mDNS (como http://nombre.local:8000/), o usar un servidor con IP fija en LAN.

Pero para desarrollo local, lo más simple es fijar la IP del PC.