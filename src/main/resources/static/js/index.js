// Función para renderizar la tabla
function renderTable(customers) {
    const tbody = document.querySelector('#customersTable tbody');
    const noResultsDiv = document.getElementById('noResultsMessage');
    const table = document.getElementById('customersTable');

    tbody.innerHTML = ''; // Limpiar tabla actual

    if (customers.length === 0) {
        // Si no hay clientes, mostrar mensaje y ocultar tabla (opcional, o dejar encabezados)
        // En este caso, si es una búsqueda fallida, mostramos el mensaje específico.
        // Si es carga inicial vacía, mostramos el mensaje genérico en la tabla.
        
        // Verificamos si estamos en modo búsqueda (input no vacío)
        const searchTerm = document.getElementById('searchTerm').value.trim();
        
        if (searchTerm) {
            noResultsDiv.style.display = 'block';
            // table.style.display = 'none'; // Opcional: ocultar tabla si no hay resultados
        } else {
            // Carga inicial sin datos
            tbody.innerHTML = '<tr><td colspan="3" style="text-align:center; color:#777;">No hay clientes registrados aún.</td></tr>';
            noResultsDiv.style.display = 'none';
            table.style.display = 'table';
        }
        return;
    }

    // Si hay datos
    noResultsDiv.style.display = 'none';
    table.style.display = 'table';

    customers.forEach(customer => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${customer.externalId}</td>
            <td>${customer.name}</td>
            <td>
                <a href="predictions.html?customerId=${customer.id}" class="btn-predict">Predicciones</a>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Cargar clientes (con soporte para búsqueda)
async function loadCustomers(searchBy = null, searchTerm = null) {
    try {
        let url = '/api/customers';
        
        // Si hay parámetros de búsqueda, los añadimos a la URL
        if (searchBy && searchTerm) {
            // Codificamos el término para evitar problemas con espacios o caracteres especiales
            const encodedTerm = encodeURIComponent(searchTerm);
            url += `?searchBy=${searchBy}&searchTerm=${encodedTerm}`;
        }

        const response = await fetch(url);
        if (response.ok) {
            const customers = await response.json();
            renderTable(customers);
        }
    } catch (error) {
        console.error('Error cargando clientes:', error);
    }
}

// Evento para el botón de búsqueda
document.getElementById('btnSearch').addEventListener('click', () => {
    const searchBy = document.getElementById('searchBy').value;
    const searchTerm = document.getElementById('searchTerm').value.trim();
    
    // Llamamos a la función de carga con los parámetros
    // Si searchTerm está vacío, loadCustomers recibirá null (o string vacío) y cargará todo
    loadCustomers(searchBy, searchTerm);
});

// Opcional: Buscar al presionar Enter en el input
document.getElementById('searchTerm').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        document.getElementById('btnSearch').click();
    }
});

// Cargar clientes al inicio (sin filtros)
loadCustomers();

document.getElementById('clienteForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // Evita que la página se recargue

    // 1. Capturar datos del formulario
    const data = {
        name: document.getElementById('name').value,
        externalId: document.getElementById('externalId').value
    };

    // 2. Enviar petición POST a tu API Java
    try {
        const response = await fetch('/api/customers', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        const mensajeDiv = document.getElementById('mensaje');

        if (response.ok) {
            // Ahora la API devuelve la lista completa de clientes
            const allCustomers = await response.json();

            mensajeDiv.className = 'exito';
            mensajeDiv.innerText = `¡Éxito! Cliente creado correctamente.`;
            document.getElementById('clienteForm').reset(); // Limpiar formulario

            // Limpiar búsqueda y mostrar todos (incluido el nuevo)
            document.getElementById('searchTerm').value = '';
            renderTable(allCustomers);
        } else {
            mensajeDiv.className = 'error';
            mensajeDiv.innerText = 'Error al crear el cliente. Revisa si el ID ya existe.';
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('mensaje').innerText = 'Error de conexión con el servidor.';
    }
});