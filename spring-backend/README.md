*** TAREAS ***

- Backend
  - Clase User NO debe mapearse a db. Crear clase Customer y Clase Shop que extiendan de User y mapearlas.
  - NO guardar mas JWT en Mongo.
  - Guardar solo los revocados en una base de datos en memoria.
  - Agregar claim a JWT isAccessToken true/false para que Refresh Token no pueda utilizarse para acceder a recursos.
  - Cambiar lógica de validacion de Tokens. Solo verifica firma, expiracion, rol y si existe en blacklist.
  
*** CODIGO
- Crear tests unitarios.
- Filtro buscar articulo por nombre

*** INFRAESTRUCTURA
- Crear EC2 y ejecutar API.
- Pipelines para CI/CD con Github Actions.

*** PLAN DE EJECUCION ***

Etapas del desarrollo:

1) Migración inicial:

    a) Bases: Rehacer la API productiva, limpieza de codigo y reacomodar para funcionar con bd no relacional.
    
    b) Seguridad: Implementar Spring Security y JWT (bien hecho y con noción de lo aplicado).
  
    c) Escalabilidad: Implementar la base de funcionalidades que permitirán expandir la aplicación a futuro: Excepciones, manejo de errores, validaciones, logueo de información y respuestas de la API.


2) E-commerce:

    a) Productos: CRUD de productos, categorías y funcionalidad de asociar productos y usuarios (carrito). Compras userless?

    c) Notificaciones: Implementar bases para notificaciones por correo electrónico.

    b) Pasarela de pago: Definir y desarrollar: Pagos directamente dentro de la app o generar reservas de productos y derivar pagos por fuera?

    d) Inventario: Gestión de productos y stock, registro de transacciones y generación de informes semanales/mensuales.

    e) Gestión de comercio / interacción con cliente: Devoluciones, conversaciones y resolución de dudas.
