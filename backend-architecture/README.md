# architectural-intelligence-backend
# 🏗️ Gestión de Obras - Backend

Este proyecto es el backend de la aplicación de gestión de obras. Está desarrollado con **Spring Boot**, usa **JWT** para autenticación, y persiste datos en una base de datos **MySQL**.

---

## 🚀 Funcionalidades actuales

- Registro y login de usuarios con JWT
- Control de acceso por roles (`ADMIN`, `EMPLEADO`)
- Protección de endpoints con Spring Security
- CRUD de Categoría de Empleado (protegido para `ADMIN`)

---

## ⚙️ Tecnologías

- Java 17
- Spring Boot 3.4.x
- Gradle
- Spring Security + JWT
- JPA / Hibernate
- MySQL

---

## 🧑‍💻 Requisitos previos

- JDK 17 o superior
- MySQL Server corriendo
- IntelliJ IDEA (opcional)
- Gradle (viene incluido con el wrapper `./gradlew`)

---

## 🗄️ Configuración de la base de datos

Crear una base de datos llamada `gestion_obras`:

```sql
CREATE DATABASE gestion_obras;

para correr la app ./gradlew bootRun

clonar el repo git clone https://github.com/tu_usuario/tu_repo.git

cd backend-architecture

               ├── BackendArchitectureApplication.java   <-- clase principal
    │               ├── config                  <-- configuraciones (seguridad, cors, etc.)
    │                   ├── JwtAuthFilter       
    │                   ├── JwtUtils            
    │                   ├── SecurityConfig      
    │               ├── controller              <-- controladores (REST)
    │                   ├── AuthController    
    │               ├── dto                     <-- clases DTO (opcional, pero buena práctica)
    │                   ├── AuthRequest
    │                   ├── AuthResponse
    │                   ├── RegisterRequest    
    │               ├── exception               <-- manejo centralizado de errores
    │               ├── model                   <-- entidades JPA (empleado, obra, presupuesto, etc.)
    │                   ├── NombreRol
    │                   ├── Rol
    │                   ├── Usuario
    │               ├── repository              <-- interfaces de acceso a datos (JpaRepository)
    │                   ├── RolRepository
    │                   ├── UsuarioRepository    
    │               ├── security                 
    │                   ├── UsuarioDetailsImpl 
    │               ├── service                 <-- lógica de negocio (interfaces + implementaciones)
    │                   ├── UsuarioDetailsServiceImpl
    │                   ├── UsuarioService 
    │               └── util                    <-- helpers (métodos comunes, validaciones, etc.)
    │                   ├── PasswordEncoderConfig 