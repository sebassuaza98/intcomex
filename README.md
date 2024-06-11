<a name="readme-top"></a>

# Name Repository

intcomex

## Built With

- Java 21
- Spring Boot 3.3.0
- Maven 3.0
- Mysql 8.0

### Prerequisites

--Mysql 8.0
--Maven 3.0 
--JDK 21 
--Text editor (VsCode)
--SDK

### Install
Para instlar el Api de forma local se debe:
1. Clone the repo
   ```HTTPS
   https://github.com/sebassuaza98/intcomex.git
   ```
2. Install packages
   Una vez clonado el repositorio, ejecutamos en el proyecto el comando, para cargar dep:

   mvn clean install
   ```
   ```

## Getting Started

   Si se desea ejecutar el api de forma local se deber ejecutar el siguiente 
   comando en la consola del proyecto:

    .\mvnw.cmd spring-boot:run 

    Estas son las peticiones que se pueden hacer :
    
	POST: http://localhost:8080/category/create
   Esta petición es la encargada de crear las categorías.
   Cuerpo de la peticion:

   Name: CLOUD, “ es una cadena de texto”
   Picture : “Se inserta un imagen “

 
	POST: http://localhost:8080/product/create 
Esta petición es la encargada de crear los productos de manera automática y aleatoria, 
tener en cuenta  pasarle  por parámetro el siguiente Json : 
{
    "name": "Producto {{randomInt 1 100}}",
    "quantityPerUnit": "{{randomInt 1 20}} unidades",
    "unitPrice": {{randomDecimal 10 100}},
    "unitsInStock": {{randomInt 1 500}},
    "unitsOnOrder": {{randomInt 1 100}},
    "reorderLevel": {{randomInt 1 50}},
    "discontinued": {{randomBoolean}}
}

 

	GET: http://localhost:8080/product/listPage?page=8&size=10 
Esta petición es la encargada de mostrar todos los productos, incluyendo la paginación, 
tener en cuenta pasar los parámetros page y size.
 

	GET: http://localhost:8080/product/list/picture/10  
Esta petición es la encargada de buscar por id de producto y traer la foto de la 
categoría a la que corresponde, el id del producto a buscar va al final de la peticion.


### Test
1. Para ejecutar las pruebas unitarias, se escribe el siguiente comando:
 mvn test   
```
 
## Authors

👤 **Sebastian suaza loaiza **

- GitHub: [@user](https://github.com/sebassuaza98)


