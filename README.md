[![CI](https://github.com/dialva98/ArquiSoft20262_Lab01/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/dialva98/ArquiSoft20262_Lab01/actions/workflows/ci.yml)

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=dialva98_ArquiSoft20262_Lab01&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=dialva98_ArquiSoft20262_Lab01)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=dialva98_ArquiSoft20262_Lab01&metric=coverage)](https://sonarcloud.io/summary/new_code?id=dialva98_ArquiSoft20262_Lab01)

[![Reliability issues](https://sonarcloud.io/api/project_badges/measure?project=dialva98_ArquiSoft20262_Lab01&metric=software_quality_reliability_issues)](https://sonarcloud.io/summary/new_code?id=dialva98_ArquiSoft20262_Lab01)

[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=dialva98_ArquiSoft20262_Lab01&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=dialva98_ArquiSoft20262_Lab01)

[![SonarQube Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-highlight.svg)](https://sonarcloud.io/summary/new_code?id=dialva98_ArquiSoft20262_Lab01)

# ArquiSoft20262_Lab02

Aplicación bancaria desarrollada con **Spring Boot** y **React**, que permite gestionar clientes, consultar cuentas, realizar transferencias y consultar el historial de transacciones. Acceso: https://bancoudea-nww2.onrender.com 

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

### Persistencia

Como mejora introducida durante el segundo ejercicio práctico del curso, se realizó la migración de la base de datos local a una base de datos alojada en un servidor en la nube, específicamente en Neon, un servicio basado en PostgreSQL.

### Funcionalidades

- Consulta de clientes y cuentas.
- Creación de nuevos clientes.
- Realización de transferencias entre cuentas.
- Consulta del historial de transacciones.
- API REST para la comunicación entre frontend y backend.
- Pruebas de endpoints mediante Postman.

### Autor

**Diego Vásquez**

**Universidad de Antioquia - Curso de Arquitectura de Software - Laboratorio 2**

