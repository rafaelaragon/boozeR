# _BoozeR (app móvil)_
BoozeR es una aplicación móvil desarrollada en Android Studio que busca facilitar la vida a los usuarios que quieran descubrir bebidas nuevas.
Principalmente, la aplicación está destinada a gestionar bebidas alcohólicas. No obstante, también será posible gestionar bebidas sin alcohol, así como complementos para las mismas.

## Instalación
Simplemente hay que descargarse la [apk](https://github.com/rafaelaragon/boozeR/blob/master/BoozeR_01-06-2020.apk) e instalarla en el dispositivo. (Se requiere nivel de API 21 o superior).
# _Secciones de BoozeR_
## 1. Índice
Lo primero que se ve al iniciar la aplicación.Desde aquí es posible registrar el usuario, y en caso de estarlo, iniciar sesión </br></br>
<img src="media/index-v2.jpg" height="600px">

## 2. Registro
Esta actividad sirve tanto para registrar al usuario en **Firebase** como para añadir la información del mismo en la tabla *Users* de **DynamoDB** </br></br>
<img src="media/register-v2.jpg" height="600px">

## 3. Inicio de Sesión </br>
<img src="media/login-v2.jpg" height="600px">

## 4. Vista Principal, que a su vez contiene varios fragmentos:
   - ### Catálogo de Bebidas 
      Es el fragmento más importante. En él, se puede ver una lista de todas las bebidas almacenadas en la tabla *Drinks* en                   **DynamoDB**<br/>
     * #### Catálogo <br/><br/><img src="media/catalogue_1-v3.jpg" height="600px"><br/><br/>
     * #### Filtro 
         Es posible filtrar las bebidas según su tipo, graduación y precio, además de mostrar las bebidas de la **lista negra**<br/><br/><img src="media/catalogue_2-v3.jpg" height="600px"><br/><br/>
     * #### Barra de Búsqueda 
         En caso de querer buscar una bebida en particular, basta con tocar la lupa de la parte superior derecha. Tras hacerlo, el usuario podrá introducir por teclado el nombre de la bebida, y esta aparecerá</br><br/><img src="media/catalogue_3-v3.jpg" height="600px"><br/><br/>
   - ### Bebidas Favoritas
      Aquí se muestran las bebidas que más le gustan al usuario </br><br/>
     <img src="media/favorites.jpg" height="600px">
     <br/><br/>
   - ### Calculador de Alcohol en Sangre 
      Este fragmento permite estimar el porcentaje de alcohol en sangre en función de la cantidad de alcohol ingerida </br><br/>
       <img src="media/calculator-v3.jpg" height="600px"><br/><br/>
## 5. Perfil </br>
   - ### Datos del Usuario 
   Aquí se muestran los datos del usuario, almacenados en la tabla *Users* en **DynamoDB**<br/><br/>
   <img src="media/profile-v2.jpg" height="600px"></br><br/>
   - ### Editar Perfil
   Es posible cambiar el nombre de Usuario. Al hacerlo, se actualiza la base de datos y se cierra la sesión</br></br>
   <img src="media/profile_edit-v3.jpg" height="600px"></br><br/>
   - ### Borrar Usuario
   Si decides borrar la cuenta, se borrará el usuario de la base de datos y se eliminará el usuario de **Firebase**</br></br>
   <img src="media/profile_delete-v3.jpg" height="600px">
      
