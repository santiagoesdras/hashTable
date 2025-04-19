
# Tabla hash

Proyecto no.1 de curso Estructura de datos 1

Proyecto pensado para simular la migracion de una bitacora de base de datos, los datos de migracion se leen desde un archivo csv con el siguiente formato:

## Csv de Ejemplo

| action            | person                                                             |
| ----------------- | ------------------------------------------------------------------ |
|INSERT;|{"firstName":"Eveline","lastName":"Ferry","uid":"2e220e29908"}|
|INSERT;|{"firstName":"Murl","lastName":"Rodriguez","uid":"e220e299087"}|
|INSERT;|{"firstName":"Eveline","lastName":"Ferry","uid":"220e299087f"}|
|INSERT;|{"firstName":"Murl","lastName":"White","uid":"20e299087fd"}|
|DELETE;|{"firstName":"Eveline","lastName":"Ferry","uid":"2e220e29908"}|
|INSERT;|{"firstName":"Eveline","lastName":"Ferry","uid":"e299087fdcf"}|

Se procesa los inserts y deletes para determinar si un usuario existe en base de datos o no actualmente y los endpoints devuelven la propiedad: 

exists: true || false





