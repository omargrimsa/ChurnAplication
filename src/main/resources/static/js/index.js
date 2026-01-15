// Función para renderizar la tabla
function renderTable(customers) {
    const tbody = document.querySelector('#customersTable tbody');
    tbody.innerHTML = ''; // Limpiar tabla actual

    if (customers.length === 0) {
        tbody.innerHTML = '<tr><td colspan="3" style="text-align:center; color:#777;">No hay clientes registrados aún.</td></tr>';
        return;
    }

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

// Cargar clientes al iniciar la página
async function loadCustomers() {
    try {
        const response = await fetch('/api/customers');
        if (response.ok) {
            const customers = await response.json();
            renderTable(customers);
        }
    } catch (error) {
        console.error('Error cargando clientes:', error);
    }
}

// Cargar clientes al inicio
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

            // Actualizar la tabla con la nueva lista recibida
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