# _BoozeR_
## [Presentación](https://docs.google.com/presentation/d/1V-Md0SyKP87gJIY4NDrheRQVYlVeGGNqkmiQywAaGYU/edit?usp=sharing)
## ¿Alguna vez has tenido problemas a la hora de elegir qué beber?
### Quizás no quieras gastarte mucho dinero. A lo mejor quieres probar un ron nuevo. O quizás simplemente no quieras algo muy cargado.
Para esos casos, y para muchos más, nació ***BoozeR***.

## _¿Qué es BoozeR?_
BoozeR es una aplicación móvil desarrollada en Android Studio que busca facilitar la vida a los usuarios que quieran descubrir bebidas nuevas.

Principalmente, la aplicación está destinada a gestionar bebidas alcohólicas. No obstante, también será posible gestionar bebidas sin alcohol, así como complementos para las mismas. </br>

## _Instalación_
- Boozer (Android Studio): Simplemente abrir el proyecto con android studio y pulsar en 'Run boozeR(Mayús+F10)'
- Boozer-Admin (React): </br>
  - En un terminal en la raíz del proyecto: `npm install`</br>
  - `npm start`</br>
## _Secciones de BoozeR_
- Índice</br></br>
<img src="Boozer (Android Studio)/media/index.jpg" height="600px">
</br>

- Registro</br></br>
<img src="Boozer (Android Studio)/media/register.jpg" height="600px">
</br>

- Inicio de Sesión</br></br>
<img src="Boozer (Android Studio)/media/login.jpg" height="600px">
</br>

- Vista Principal, que a su vez contendrá varios fragmentos:</br>
  - Catálogo de Bebidas</br></br>
  <img src="Boozer (Android Studio)/media/catalogue.jpg" height="600px">
  </br></br>
  
  - Calculador de Alcohol en Sangre</br></br>
  <img src="Boozer (Android Studio)/media/calculator.jpg" height="600px">
  </br>
  
- Perfil</br></br>
<img src="Boozer (Android Studio)/media/profile.jpg" height="600px">
</br></br>

  - Editar Cuenta
<img src="Boozer (Android Studio)/media/profile_edit.jpg" height="600px">
</br></br>

  - Borrar Cuenta
<img src="Boozer (Android Studio)/media/profile_delete.jpg" height="600px">
</br>

## _TODO_
En próximas actualizaciones, se añadira un nuevo fragmento llamado _coctelería_, en el que podrás crear tus propios cócteles a partir de bebidas ya existentes.

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

