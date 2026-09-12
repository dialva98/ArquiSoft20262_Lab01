# ArquiSoft20262_Lab01

Aplicación bancaria desarrollada con **Spring Boot** y **React**, que permite gestionar clientes, consultar cuentas, realizar transferencias y consultar el historial de transacciones.

### Tecnologías

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
- React
- Vite
- MapStruct
- Postman

### Arquitectura

El backend implementa una arquitectura por capas:

- Controller
- Service
- Repository
- Entity
- DTO / Mapper

El frontend está desarrollado en React y consume la API REST proporcionada por Spring Boot.

### Ejecución

- Backend: `http://localhost:8080`
- Frontend durante desarrollo: `http://localhost:5173`
- Frontend compilado: integrado en `src/main/resources/static`

### Configuración para pruebas

Para realizar las pruebas, es necesario contar con una base de datos **MySQL local** y cargar previamente algunos clientes/cuentas de prueba.

También se debe ajustar en `src/main/resources/application.properties` el **usuario, contraseña y parámetros de conexión** correspondientes a la configuración local de MySQL.

### Funcionalidades

- Consulta de clientes y cuentas.
- Creación de nuevos clientes.
- Realización de transferencias entre cuentas.
- Consulta del historial de transacciones.
- API REST para la comunicación entre frontend y backend.
- Pruebas de endpoints mediante Postman.

### Autor

**Diego Vásquez**

**Universidad de Antioquia - Curso de Arquitectura de Software - Laboratorio 1**

