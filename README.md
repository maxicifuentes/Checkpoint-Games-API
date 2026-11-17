# Checkpoint Games API

Backend Spring Boot de la Evaluación Parcial 3. Expone una API REST versionada para categorías, productos, usuarios, boletas y autenticación con JWT.

## Requisitos

- JDK 21.
- Maven 3.9 o superior.
- MySQL 8 para la configuración de entrega. H2 queda disponible por defecto solo para una demostración local rápida.

## Ejecución

```powershell
# Demostración local con H2 (crea ./data/checkpoint-games)
mvn spring-boot:run

# Base de datos MySQL local
$env:SPRING_PROFILES_ACTIVE="mysql"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="checkpoint"
mvn spring-boot:run
```

La documentación Swagger queda disponible en `http://localhost:8080/swagger-ui.html`.

## Credenciales de demostración

- Administrador: `admin@duoc.cl` / `admin1`
- Vendedor: `vendedor@duoc.cl` / `venta1`
- Cliente: `cliente@gmail.com` / `user1`

## Pruebas de integración

```powershell
mvn test
```

Las pruebas levantan una base H2 aislada, prueban el catálogo público, el bloqueo de operaciones sin permiso, el inicio de sesión JWT y una compra de cliente.
