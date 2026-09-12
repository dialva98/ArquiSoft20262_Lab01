ArquiSoft20262_Lab01
Laboratorio 1 - Arquitectura de Software

Aplicación bancaria desarrollada con Spring Boot y React, que permite gestionar clientes, consultar cuentas, realizar transferencias y consultar el historial de transacciones.

Tecnologías
Java 17
Spring Boot
Spring Data JPA
MySQL
Maven
React
Vite
MapStruct
Postman
Arquitectura

El backend implementa una arquitectura por capas: Controller, Service, Repository, Entity y DTO/Mapper.

El frontend está desarrollado en React y consume la API REST proporcionada por Spring Boot.

Ejecución

Backend: http://localhost:8080

Frontend durante desarrollo: http://localhost:5173

El proyecto también incluye la versión compilada del frontend dentro de src/main/resources/static.

Nota: Para realizar las pruebas, es necesario contar con una base de datos MySQL local y cargar previamente algunos clientes/cuentas de prueba. También se debe ajustar en application.properties el usuario, contraseña y parámetros de conexión correspondientes a la configuración local de MySQL.

Universidad de Antioquia - Arquitectura de Software - Laboratorio 1
