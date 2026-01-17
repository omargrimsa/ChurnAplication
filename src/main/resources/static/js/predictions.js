// Obtener el ID del cliente de la URL (ej: predictions.html?customerId=5)
const urlParams = new URLSearchParams(window.location.search);
const customerId = urlParams.get('customerId');

if (!customerId) {
    alert("No se especificó un cliente. Redirigiendo al inicio.");
    window.location.href = "index.html";
}

// 1. Cargar datos del cliente para mostrar en el primer DIV
async function loadCustomerInfo() {
    try {
        const response = await fetch('/api/customers');
        if (response.ok) {
            const customers = await response.json();
            // Buscamos el cliente por ID (asegurando tipos con ==)
            const customer = customers.find(c => c.id == customerId);

            if (customer) {
                document.getElementById('displayExternalId').innerText = customer.externalId;
                document.getElementById('displayName').innerText = customer.name;
            } else {
                document.getElementById('customerInfo').innerHTML = "<p class='error'>Cliente no encontrado.</p>";
            }
        }
    } catch (error) {
        console.error("Error cargando cliente:", error);
    }
}

// Función para cargar el historial de predicciones
async function loadPredictionHistory() {
    try {
        const response = await fetch(`/api/predictions/customer/${customerId}`);
        if (response.ok) {
            const predictions = await response.json();
            const tbody = document.querySelector('#historyTable tbody');
            tbody.innerHTML = ''; // Limpiar tabla

            if (predictions.length === 0) {
                tbody.innerHTML = '<tr><td colspan="13" style="text-align:center;">No hay historial de predicciones.</td></tr>';
                return;
            }

            // Ordenar por fecha descendente (más reciente primero)
            predictions.sort((a, b) => new Date(b.sentAt) - new Date(a.sentAt));

            predictions.forEach(p => {
                const row = document.createElement('tr');
                
                // Formatear fecha
                const date = new Date(p.sentAt).toLocaleString();
                
                // Formatear probabilidad
                const prob = p.churnProbability != null ? (p.churnProbability * 100).toFixed(1) + '%' : 'N/A';
                
                // Formatear estado (Will Cancel)
                let statusHtml = 'N/A';
                if (p.willCancel === true) {
                    statusHtml = '<span class="status-churn">Churn</span>';
                } else if (p.willCancel === false) {
                    statusHtml = '<span class="status-safe">Seguro</span>';
                }

                // Helper para iconos booleanos
                const check = (val) => val ? '✅' : '❌';

                row.innerHTML = `
                    <td>${date}</td>
                    <td>${p.customerTenure}</td>
                    <td>$${p.accountTotal}</td>
                    <td>$${p.accountChargesMonthly}</td>
                    <td style="text-align:center;">${check(p.isMonthlyContract)}</td>
                    <td style="text-align:center;">${check(p.isElectronicPay)}</td>
                    <td style="text-align:center;">${check(p.hasTechnicalSupport)}</td>
                    <td style="text-align:center;">${check(p.hasOnlineSecurity)}</td>
                    <td style="text-align:center;">${check(p.hasTwoYearContract)}</td>
                    <td style="text-align:center;">${check(p.hasOnlineInvoices)}</td>
                    <td style="text-align:center;">${check(p.hasOneYearContract)}</td>
                    <td>${prob}</td>
                    <td>${statusHtml}</td>
                `;
                tbody.appendChild(row);
            });
        }
    } catch (error) {
        console.error("Error cargando historial:", error);
    }
}

// Inicializar cargas
loadCustomerInfo();
loadPredictionHistory();

// 2. Manejar el envío del formulario
document.getElementById('predictionForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    // Construir el objeto JSON tal como lo espera ModelInputDTO
    const formData = {
        customerId: parseInt(customerId), // ID del cliente obtenido de la URL
        Meses_contrato: parseFloat(document.getElementById('Meses_contrato').value),
        Total: parseFloat(document.getElementById('Total').value),
        Factura_mensual: parseFloat(document.getElementById('Factura_mensual').value),

        // Convertir checkboxes a booleanos
        Contrato_mensual: document.getElementById('Contrato_mensual').checked,
        Pago_chequera_electronica: document.getElementById('Pago_chequera_electronica').checked,
        Soporte_tecnico: document.getElementById('Soporte_tecnico').checked,
        Seguridad_online: document.getElementById('Seguridad_online').checked,
        "Contrato_2_años": document.getElementById('Contrato_2_años').checked,
        Factura_online: document.getElementById('Factura_online').checked,
        "Contrato_1_año": document.getElementById('Contrato_1_año').checked
    };

    const resultDiv = document.getElementById('predictionResult');
    resultDiv.style.display = 'none'; // Ocultar resultado previo

    try {
        const response = await fetch('/api/predictions', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (response.ok) {
            const result = await response.json();

            // Mostrar resultado
            resultDiv.style.display = 'block';

            // Usamos el campo 'willCancel' del DTO PredictionResultDTO
            const isChurn = result.willCancel === true;

            // Formatear probabilidad a porcentaje (ej. 0.85 -> 85.0%)
            const probabilityPercent = (result.churnProbability * 100).toFixed(1);

            if (isChurn) {
                resultDiv.className = 'result-box churn-yes';
                resultDiv.innerHTML = `
                    <h4>⚠️ ALERTA DE CHURN</h4>
                    <p>El modelo predice que este cliente <strong>PODRÍA ABANDONAR</strong> el servicio.</p>
                    <p>La probabilidad de churn es de: <strong>${probabilityPercent}%</strong></p>
                `;
            } else {
                resultDiv.className = 'result-box churn-no';
                resultDiv.innerHTML = `
                    <h4>✅ CLIENTE SEGURO</h4>
                    <p>El modelo predice que este cliente <strong>SE QUEDARÁ</strong>.</p>
                    <p>La probabilidad de churn es de: <strong>${probabilityPercent}%</strong></p>
                `;
            }

            // Desplazar el scroll hasta el resultado
            resultDiv.scrollIntoView({ behavior: 'smooth', block: 'start' });
            
            // Recargar el historial para mostrar la nueva predicción
            loadPredictionHistory();

            // Limpiar el formulario
            document.getElementById('predictionForm').reset();

        } else {
            const errorText = await response.text();
            alert("Error en la predicción: " + errorText);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Error de conexión al enviar la predicción.");
    }
});