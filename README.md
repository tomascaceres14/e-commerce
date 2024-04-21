*** TAREAS ***

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
  b) Pasarela de pago: Definir y desarrollar: Pagos directamente dentro de la app o generar reservas de productos y derivar pagos por fuera?
  c) Mensajería: Confirmaciones de cuentas, compras, actualización de estado de pedido.
  d) Inventario: Gestión de productos y stock, registro de transacciones y generación de informes semanales/mensuales.
  e) Gestión de comercio / interacción con cliente: Devoluciones, conversaciones y resolución de dudas.
