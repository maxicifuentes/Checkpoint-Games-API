# Documento de APIs e integración

## Modelo de datos

El modelo sigue la propuesta oficial: `Usuario` crea una `Boleta`; cada boleta contiene uno o más `DetalleBoleta`; cada detalle corresponde a un `Producto`; y cada producto pertenece a una `Categoria`.

## Rutas versionadas

| Método | Ruta | Acceso | Uso |
|---|---|---|---|
| POST | `/api/v1/auth/login` | Público | Valida credenciales y entrega JWT. |
| POST | `/api/v1/auth/registro` | Público | Crea una cuenta cliente. |
| POST | `/api/v1/auth/refresh` | JWT válido | Renueva el token. |
| GET | `/api/v1/productos` | Público | Catálogo del frontend. |
| POST, PUT, DELETE | `/api/v1/productos` | Administrador | CRUD de inventario. |
| GET | `/api/v1/boletas` | JWT | Cliente: propias; administrador/vendedor: todas. |
| POST | `/api/v1/boletas` | Cliente | Confirma compra y descuenta stock. |
| PATCH | `/api/v1/boletas/{id}/estado` | Administrador/Vendedor | Cambia estado de pedido. |

## Flujo integrado

1. React solicita `GET /api/v1/productos` al cargar el catálogo.
2. El formulario de acceso envía correo y contraseña a `POST /api/v1/auth/login`.
3. El backend verifica BCrypt, genera JWT con `uid` y `rol`; React mantiene token y perfil en `localStorage` para persistir la sesión tras una recarga.
4. Las operaciones protegidas envían `Authorization: Bearer <token>`.
5. Al confirmar una compra, React envía líneas del carrito. Spring valida stock, calcula el total, guarda boleta/detalles y actualiza inventario dentro de una transacción.

## Seguridad

- Contraseñas almacenadas con BCrypt.
- JWT firmado con HMAC y vencimiento configurable.
- Spring Security valida roles en backend; las restricciones visuales del frontend son una segunda capa, no sustituyen la autorización del servidor.
- CORS restringido a los puertos de Vite de desarrollo.
