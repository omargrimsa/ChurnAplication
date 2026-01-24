-- Desactivar verificación de claves foráneas para evitar errores al borrar
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiar tablas (TRUNCATE es más rápido y resetea el AUTO_INCREMENT automáticamente en MySQL)
TRUNCATE TABLE prediction_results;
TRUNCATE TABLE model_inputs;
TRUNCATE TABLE customers;

-- Asegurar reinicio de AUTO_INCREMENT (redundante con TRUNCATE pero buena práctica explícita)
ALTER TABLE prediction_results AUTO_INCREMENT = 1;
ALTER TABLE model_inputs AUTO_INCREMENT = 1;
ALTER TABLE customers AUTO_INCREMENT = 1;

-- Reactivar verificación de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;

-- Confirmación (Opcional)
SELECT 'Base de datos reseteada correctamente' AS Status;
