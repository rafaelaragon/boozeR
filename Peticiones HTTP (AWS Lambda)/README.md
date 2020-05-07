# Peticiones HTTP a la API
Simplemente he copiado las funciones Lambda que he creado en AWS. Estas hacen las veces de peticiones HTTP a la API.

## Información Adicional
No hay mucho que decir de la mayoría de las peticiones, a excepción de dos: _deleteBoozerDrinkFromFavorites_ y _deleteBoozerDrinkFromBlacklist_. <br/>
El problema con estas peticiones es que la API de boto3 (usada para hacer las peticiones a la API de AWS en Python) aún está en desarrollo, por lo que en lugar de simplemente usar DELETE(nombre_de_la_bebida) para borrar estas de la base de datos, tuve que obtener la posición en la base de datos de la bebida en particular.
