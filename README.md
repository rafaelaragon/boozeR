# _BoozeR_
## [Presentación](https://docs.google.com/presentation/d/1V-Md0SyKP87gJIY4NDrheRQVYlVeGGNqkmiQywAaGYU/edit?usp=sharing)
## ¿Alguna vez has tenido problemas a la hora de elegir qué beber?
### Quizás no quieras gastarte mucho dinero. A lo mejor quieres probar un ron nuevo. O quizás simplemente no quieras algo muy cargado.
Para esos casos, y para muchos más, nació ***BoozeR***.

## _¿Qué es BoozeR?_
BoozeR es una aplicación móvil desarrollada en Android Studio que busca facilitar la vida a los usuarios que quieran descubrir bebidas nuevas.

Principalmente, la aplicación está destinada a gestionar bebidas alcohólicas. No obstante, también será posible gestionar bebidas sin alcohol, así como complementos para las mismas. </br>

## _¿Cómo se ha hecho BoozeR?_
### App móvil
La app se ha creado usando Android Studio, y cualquier dispositivo Android con API de nivel 21 o mayor debería poder usarla sin problemas.
### Aplicación web 
La aplicación web se hizo con React, una librería de Javascript.
### Autenticación
Para la autenticación se utiliza Firebase Auth.
### Base de datos
La información de las bebidas, así como la de los usuarios se guarda en DynamoDB, una base de datos no-SQL creada por Amazon.
### API
La API ha sido creada en AWS API-Gateway.
### Almacenamiento
Las imágenes de las bebidas se almacenan en Amazon Simple Storage Service (S3).

## _Contacto_
En caso de no entender algo, o si sencillamente quieres dar ideas para mejorar la aplicación, no dudes en contactarme a través de mi correo, rafa.ar2000@gmail.com.

## _Histórico_
### Semana 1 (23-27 Marzo):
- Conseguí migrar de firebase database(realtimedatabase) a DynamoDB (AWS).  Usé [esta](https://www.youtube.com/watch?v=oGWJ8xD2W6k) guía.
- Intenté mejorar los estilos de la aplicación, a pesar de no haber terminado, conseguí arreglar bastantes cosas.
- Traduje una buena parte del código a inglés, ya que algunas partes estaban en español.
- Creé una nueva actividad para ver con más detalle cada una de las bebidas.
### Semana 2 (30 Marzo-03 Abril):
- Conseguí recoger los datos de cada bebida individualmente mediante una nueva petición a la API.
- Mejoré el layout de cada bebida individual.
- Empecé la aplicación web con React.
### Semana 3 (06-10 Abril):
- Arreglé un problema grave al mergear en github.
- Terminé la maquetación de la página web.
### Semana 4 (13-17 Abril):
- Terminé las funciones básicas de la página web (inicio de sesión, CRUD de bebidas), en un futuro controlaré el rol del usuario, para que sólo pueda editar un administrador.
- Añadí un gif durante la carga de los componentes, tanto en web como en móvil.
- Cambié ligeramente los estilos de la aplicación.
### Semana 5 (20-24 Abril):
- Migré los usuario de firebase (Realtime Database) a AWS (DynamoDB).
- Creé e implementé las peticiones http para el CRUD de usuarios.
- Añadí seguridad a la página web encriptando la contraseña y bloqueando accesos por url si el usuario no se ha autenticado. 
- Añadí alertas a la página web.
- Implementé Redux a la página web.
### Semana 6 (27 Abril-01 Mayo):
- Modifiqué el Header de la página web.
- Mejoré el estilo de los formularios de la página web.
- Creé dos nuevas peticiones a la API: Añadir bebida a favoritos y eliminar bebida de favoritos.
- Implementé las anteriores peticiones en la aplicación.
### Semana 7 (04-08 Mayo)
- Añadí una animación de carga al botón de inicio de sesión en la página web.
- Optimicé las funciones e hice el código de la página web más "legible", además de comentarlo.
- Mejoré los estilos de la página web.
- Creé dos nuevas peticionesa la API: Añadir bebida a la lista negra y eliminar la bebida de la lista negra.
- Implementé las peticiones de favoritos y de lista negra a la aplicación móvil.
- Preparé el repositorio para la primera entrega del proyecto.

