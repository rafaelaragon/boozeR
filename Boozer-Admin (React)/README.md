# Boozer-Admin
Boozer-Admin es la aplicación web de Boozer creada usando React. Ha sido creada para que los administradores puedan gestionar las bebidas de la aplicación móvil. Pudiendo así crear, editar y borrar bebidas.

## Páginas de la aplicación:

### Login
Creada para restringirla entrada a la aplicación web. Sólo pueden iniciar sesión los usuarios que, además de estar registrados en firebase, tengan permisos de administración.

Al intentar iniciar sesión se revisa, por orden, que se cumpla lo siguiente:
- Los campos no estén vacíos.
- El usuario exista, es decir, se haya registrado en Firebase.
- El usuario tenga permisos de administración (esto se revisa en la tabla de usuarios de DynamoDB)

Finalmente, si se intenta acceder a una página de la aplicación sin tener permisos de administración, el usuario será redirigido automáticamente a  esta página, evitando así accesos no deseados.

### Drinks
Aquí se puede ver una lista de todas las bebidas guardadas en la base de datos. El administrador tiene la opción de:
- Crear una nueva bebida pulsando el botón con el símbolo "+".
- Editar una bebida en particular, pulsando el lápiz (✏️) que aparece bajo la misma.
- Borrar una bebida, pulsando la papelera que aparece bajo la misma.

### New
Esta página permite al administrador crear una nueva bebida.En caso de que la url de la imagen no exista, se usa una imagen por defecto.

### Drink
Por último, la página de edición permite al administrador editar una bebida existente. Se pueden todos los campos menos el nombre de la bebida, yaque lo utilizo como clave primaria en la base de datos.
