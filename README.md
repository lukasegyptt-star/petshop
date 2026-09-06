# Petshop

Sistema de gestión para peluquería/baño de mascotas. Proyecto Maven en Java con
persistencia en MySQL vía JDBC.

## Funcionalidades

- Tres tipos de usuario: `ADMINISTRADOR`, `ADMINISTRATIVO`, `BANADOR`.
  - El **administrador** registra administrativos y bañadores.
  - El **administrativo** registra clientes, registra mascotas y arma la agenda.
  - El **bañador** también puede agendar y, además, finaliza los turnos.
- Cada cliente puede tener varias mascotas; cada mascota tiene un único dueño.
- Al agendar un turno se elige uno de 3 servicios: **Baño**, **Corte de pelo**,
  **Baño y corte de pelo**.
- No se pueden agendar más de **10 baños por día** (aplica a los servicios que
  incluyen baño).

## Requisitos

- Java 17+
- Maven 3.8+
- MySQL 8+

## Configuración

1. Crear la base de datos ejecutando el script `src/main/resources/schema.sql`
   en MySQL (crea la base `petshop_db`, las tablas y un usuario administrador
   inicial: `admin` / `admin123`).
2. Editar `src/main/java/com/petshop/conexion/Conexion.java` con el usuario y
   contraseña de tu MySQL local.
3. Compilar el proyecto:
   ```
   mvn clean compile
   ```
4. Ejecutar la aplicación:
   ```
   mvn exec:java
   ```
   o simplemente correr `Main.java` desde VS Code (extensión "Extension Pack
   for Java").

## Estructura

```
petshop/
├── pom.xml
├── src/main/java/com/petshop/
│   ├── Main.java
│   ├── conexion/Conexion.java
│   ├── modelo/        (Usuario, Cliente, Mascota, Turno, enums)
│   └── dao/            (UsuarioDAO, ClienteDAO, MascotaDAO, TurnoDAO)
└── src/main/resources/schema.sql
```

## Subir a GitHub

Desde la carpeta del proyecto:
```
git init
git add .
git commit -m "Proyecto inicial petshop"
git branch -M main
git remote add origin https://github.com/<tu-usuario>/<tu-repo>.git
git push -u origin main
```
