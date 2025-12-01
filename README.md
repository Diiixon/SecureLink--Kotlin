
 
 
 
 **SecureLink -**
 
Aplicación de Seguridad AndroidSecureLink es una aplicación Android nativa, construida con tecnologías modernas, diseñada para mejorar la seguridad de los usuarios en la navegación web. Permite analizar URLs en tiempo real para detectar amenazas como phishing y malware, proporcionando un entorno más seguro para la interacción online y fomentando una comunidad de reportes.
 
 






 **Características Principales**
 
 **•Análisis de URLs en Tiempo Real:** Escanea cualquier URL para obtener un veredicto de seguridad instantáneo, clasificando los enlaces como seguros, sospechosos o bloqueados.•Registro y Autenticación de Usuarios: Sistema completo para la gestión de cuentas de usuario, utilizando un microservicio dedicado y persistencia de sesión local.
 
 **•Dashboard de Estadísticas:** Visualiza estadísticas detalladas con gráficos interactivos, incluyendo:
 
 -Distribución de Amenazas: Un gráfico de dona que muestra el desglose de los análisis realizados por el usuario.
 
 -Comparativa Global: Un gráfico de barras que compara los resultados del usuario con las estadísticas globales de toda la comunidad.
 
 **•Historial de Análisis:** Guarda un registro de todas las URLs escaneadas por el usuario, accesible desde su perfil.
 
 **•Perfil de Usuario:** Muestra un resumen de la actividad del usuario, sus contribuciones (reportes) y sus datos.
 
 **•Escáner de Códigos QR:** (Funcionalidad detectada por la dependencia zxing) Permite analizar URLs directamente desde códigos QR para mayor comodidad.
 


 El proyecto sigue las mejores prácticas de desarrollo Android moderno, con una arquitectura MVVM (Model-View-ViewModel) y una clara separación de responsabilidades.
 
 **•Lenguaje:** Kotlin 100%.•UI: Jetpack Compose para una interfaz de usuario declarativa, moderna y reactiva.
 
 **•Arquitectura: MVVM**
 
 -View: Composable Screens (EstadisticasScreen, LoginScreen, etc.).
 
 -ViewModel: (EstadisticasViewModel, LoginViewModel) para gestionar el estado de la UI y la lógica de negocio.
 
 -Model: Repositorios (EstadisticasRepository, AuthRepository) que actúan como única fuente de verdad para los datos.
 
 **•Asincronía:** Kotlin Coroutines y Flow para manejar operaciones en segundo plano y flujos de datos reactivos.
 
** •Navegación:** Jetpack Navigation Compose para gestionar la navegación entre las diferentes pantallas.


**•Networking:**

◦Retrofit: Para la comunicación con las APIs REST de los microservicios.

◦OkHttp: Como cliente HTTP subyacente.◦Gson: Para la serialización y deserialización de objetos JSON.

**•Visualización de Datos:** Vico, una librería de gráficos moderna y flexible para Jetpack Compose.

**•Almacenamiento Local:** SharedPreferences gestionadas a través de una clase SessionManager para persistir el token y los datos de la sesión del usuario.•Escáner QR: zxing-android-embedded, una de las librerías más populares y robustas para el escaneo de códigos de barras y QR.

 La aplicación está diseñada para operar con una arquitectura de microservicios en el backend

 Servicio de Autenticación: Gestiona el registro y login de usuarios.◦Endpoint (ejemplo): http://54.147.108.252:8080/
 •Servicio de Análisis: Procesa las URLs enviadas y devuelve el veredicto de seguridad.◦Endpoint (ejemplo): http://54.144.201.173:8081/
 •Servicio de Estadísticas: Provee datos agregados para la visualización de estadísticas personales y globales.◦Endpoint (ejemplo): http://52.22.137.158:8082/



**INTALACION**:


1.Clona este repositorio

2.Abre el proyecto en la última versión de Android Studio.

3.Asegúrate de que los microservicios del backend están en ejecución y accesibles desde la red del emulador o dispositivo de prueba.



4.Verifica que las IPs de los servicios estén correctamente configuradas en:
◦app/src/main/res/xml/network_security_config.xml 
(para permitir el tráfico HTTP si es necesario).◦app/src/main/java/com/example/securelink/network/RetrofitInstance.kt.

5.Sincroniza el proyecto con Gradle, construye y ejecuta la aplicación










 **Integrantes:**
 
 Dixon Tapia Aguilera
 
 Vicente Rodriguez Espinoza

 Grupo: 10
 
 
