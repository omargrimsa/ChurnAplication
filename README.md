# 🚀 ChurnInsight - Predicción de Retención de Clientes

**ChurnInsight** es una aplicación web diseñada para ayudar a las empresas a identificar clientes en riesgo de abandono (Churn). Utilizando un backend robusto en **Spring Boot** y conectándose con un modelo de Inteligencia Artificial externo, la aplicación permite realizar predicciones en tiempo real y análisis masivos de carteras de clientes.

---

## 📋 Tabla de Contenidos
1. [Descripción del Proyecto](#-descripción-del-proyecto)
2. [Características Principales](#-características-principales)
3. [Stack Tecnológico](#-stack-tecnológico)
4. [Arquitectura](#-arquitectura)
5. [Instalación y Ejecución](#-instalación-y-ejecución)
6. [Uso de la API](#-uso-de-la-api)
7. [Formato CSV para Carga Masiva](#-formato-csv-para-carga-masiva)

---

## 📖 Descripción del Proyecto

En el contexto de un Hackathon de **NoCountry**, desarrollamos esta solución para abordar la problemática de la pérdida de clientes. La aplicación gestiona el registro de clientes y sirve como puente orquestador entre los datos del negocio y un modelo de Machine Learning (Python/FastAPI), almacenando un historial detallado de cada predicción para su posterior análisis.

---

## ✨ Características Principales

*   **Gestión de Clientes:** Registro y listado de clientes con validación de duplicados por ID de negocio (`External ID`).
*   **Predicción Individual:** Formulario interactivo para evaluar el riesgo de un cliente específico.
*   **Carga Masiva (Batch Processing):** Procesamiento de archivos **CSV** para realizar predicciones de multiples clientes simultáneamente.
*   **Historial de Predicciones:** Visualización detallada de la evolución del riesgo de cada cliente a lo largo del tiempo.
*   **Reportes de Carga:** Feedback inmediato sobre el procesamiento de archivos (filas exitosas, fallidas y detalle de errores).
*   **Interfaz Intuitiva:** Frontend limpio y responsivo con indicadores visuales de estado (Churn/Seguro).

---

## 🛠 Stack Tecnológico

### Backend
*   **Lenguaje:** Java 17
*   **Framework:** Spring Boot 3 (Web, Data JPA, Validation)
*   **Base de Datos:** H2 Database (En memoria, en etapa inicial del desarrollo del proyecto)
      y en la etapa final se uso MySQL para persistir los datos.
*   **Herramientas:** 
    *   `OpenCSV` (Procesamiento de archivos masivos)
    *   `RestTemplate` (Comunicación con API de IA)
    *   `Lombok` (Reducción de código repetitivo)
    *   `Jackson` (serializar y deserializar objetos Java)

### Frontend
*   **Tecnologías:** HTML5, CSS3, Vanilla JavaScript (ES6+).
*   **Comunicación:** Fetch API para consumo de endpoints REST.

### Infraestructura / Integración
*   **Modelo IA:** Integración vía REST con servicio externo (Python).
*   **Despliegue:** Se llevó a cabo el depliegue de la aplciacion de Backend y el modelo de predición en un servidor VPS del proveedor Hostinger
                    KVM 2 con 2 núcleos vCPU, 8 GB de RAM y 100 GB de almacenamiento NVMe SSD.

---

## 🏗 Arquitectura

El proyecto sigue una arquitectura en capas (MVC) con DTOs para la transferencia de datos:

1.  **Controller Layer:** Expone endpoints REST (`/api/customers`, `/api/predictions`).
2.  **Service Layer:** Contiene la lógica de negocio, validaciones y orquestación de la carga masiva (`CsvPredictionService`).
3.  **Repository Layer:** Interacción con la base de datos MySQL/H2.
4.  **External Integration:** Comunicación con el microservicio de predicción.

---

## 🚀 Instalación y Ejecución

### Prerrequisitos
*   Java JDK 17+
*   Maven
*   MySQL 8
*   Python 3
*   FastAPI + Uvicorn

### Pasos
1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/omargrimsa/ChurnAplication.git
    cd ChurnAplication
    ```

2.  **Configurar la URL del Modelo:**
    Edita el archivo `src/main/resources/application.properties` y ajusta la URL de la API de Python:
    ```properties
    external.api.url=http://localhost:8000/predict  # O la URL del endpoint del modelo de predicción
    ```

3.  **Compilar y Ejecutar:**
    ```bash
    ./mvnw clean package
    java -jar target/ChurnAplication-0.0.1-SNAPSHOT.jar
    ```

4.  **Acceder a la Aplicación:**
    Abre tu navegador en: `http://localhost:8080`

---

## 🔌 Uso de la API

### Clientes
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/customers` | Registra un nuevo cliente. |
| `GET` | `/api/customers` | Obtiene la lista de todos los clientes. |

### Predicciones
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/predictions` | Realiza una predicción individual. |
| `GET` | `/api/predictions/customer/{id}` | Obtiene el historial de un cliente. |
| `POST` | `/api/predictions/upload-csv` | Carga masiva mediante archivo `.csv`. |

---

## 📄 Formato CSV para Carga Masiva

Para utilizar la función de carga masiva, el archivo CSV debe tener la codificación **UTF-8** y seguir la siguiente estructura de columnas:

**Encabezados:**
`Id_cliente, Meses_contrato, Total, Factura_mensual, Contrato_mensual, Pago_chequera_electronica, Soporte_tecnico, Seguridad_online, Contrato_2_años, Factura_online, Contrato_1_año`

**Ejemplo de datos:**
```csv
CUST-001, 12, 500.50, 45.00, true, false, true, true, false, true, false
CUST-002, 5, 120.00, 24.00, false, true, false, false, false, false, true
```

> **Nota:** La aplicación incluye un botón para descargar una plantilla de ejemplo directamente desde la interfaz.

---


