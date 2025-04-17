# architectural-intelligence-backend
Proyecto para gestión de proyectos de Arquitectura

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