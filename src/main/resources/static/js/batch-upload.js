const dropZone = document.getElementById('dropZone');
const fileInput = document.getElementById('csvFile');
const fileNameDisplay = document.getElementById('fileName');
const uploadForm = document.getElementById('uploadForm');
const loadingDiv = document.getElementById('loading');
const reportSection = document.getElementById('reportSection');

// --- Lógica de Descarga de Plantilla ---
document.getElementById('btnDownloadTemplate').addEventListener('click', () => {
    // Definir el contenido del CSV con los encabezados correctos y valores en 0
    // Usamos \uFEFF (BOM) al principio para que Excel reconozca UTF-8 y muestre bien la ñ
    const csvContent = "\uFEFFId_cliente,Meses_contrato,Total,Factura_mensual,Contrato_mensual,Pago_chequera_electronica,Soporte_tecnico,Seguridad_online,Contrato_2_años,Factura_online,Contrato_1_año\n" +
                       "CUST-001,0,0,0,0,0,0,0,0,0,0";
    
    // Crear un Blob con codificación UTF-8 explícita
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement("a");
    const url = URL.createObjectURL(blob);
    link.setAttribute("href", url);
    link.setAttribute("download", "plantilla_predicciones.csv");
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
});

// --- Manejo de Drag & Drop y Click ---
dropZone.addEventListener('click', () => fileInput.click());

fileInput.addEventListener('change', () => {
    if (fileInput.files.length > 0) {
        fileNameDisplay.textContent = "Archivo seleccionado: " + fileInput.files[0].name;
    }
});

dropZone.addEventListener('dragover', (e) => {
    e.preventDefault();
    dropZone.style.backgroundColor = '#e9ecef';
});

dropZone.addEventListener('dragleave', () => {
    dropZone.style.backgroundColor = 'transparent';
});

dropZone.addEventListener('drop', (e) => {
    e.preventDefault();
    dropZone.style.backgroundColor = 'transparent';
    if (e.dataTransfer.files.length > 0) {
        fileInput.files = e.dataTransfer.files;
        fileNameDisplay.textContent = "Archivo seleccionado: " + fileInput.files[0].name;
    }
});

// --- Enviar Formulario ---
uploadForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    if (fileInput.files.length === 0) {
        alert("Por favor selecciona un archivo CSV.");
        return;
    }

    const formData = new FormData();
    formData.append('file', fileInput.files[0]);

    // UI Updates
    document.getElementById('btnUpload').disabled = true;
    loadingDiv.style.display = 'block';
    reportSection.style.display = 'none';

    try {
        const response = await fetch('/api/predictions/upload-csv', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            const report = await response.json();
            showReport(report);
        } else {
            const errorText = await response.text();
            alert("Error al subir el archivo: " + errorText);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("Error de conexión con el servidor.");
    } finally {
        document.getElementById('btnUpload').disabled = false;
        loadingDiv.style.display = 'none';
    }
});

function showReport(report) {
    document.getElementById('totalRows').textContent = report.totalRows;
    document.getElementById('successCount').textContent = report.successCount;
    document.getElementById('failureCount').textContent = report.failureCount;

    // --- Mostrar Errores ---
    const errorListDiv = document.getElementById('errorList');
    const errorSection = document.getElementById('errorSection');
    
    errorListDiv.innerHTML = ''; 

    if (report.failureCount > 0 && report.errors && report.errors.length > 0) {
        errorSection.style.display = 'block';
        report.errors.forEach(err => {
            const div = document.createElement('div');
            div.className = 'error-item';
            div.textContent = err;
            errorListDiv.appendChild(div);
        });
    } else {
        errorSection.style.display = 'none';
    }

    // --- Mostrar Tabla de Éxitos ---
    const successSection = document.getElementById('successSection');
    const tbody = document.querySelector('#batchResultsTable tbody');
    tbody.innerHTML = '';

    if (report.successCount > 0 && report.successfulPredictions) {
        successSection.style.display = 'block';
        
        report.successfulPredictions.forEach(p => {
            const row = document.createElement('tr');
            
            // Formatear probabilidad
            const prob = p.churnProbability != null ? (p.churnProbability * 100).toFixed(1) + '%' : 'N/A';
            
            // Formatear estado
            let statusHtml = 'N/A';
            if (p.willCancel === true) {
                statusHtml = '<span class="status-churn">Churn</span>';
            } else if (p.willCancel === false) {
                statusHtml = '<span class="status-safe">Seguro</span>';
            }

            // Helper para iconos booleanos
            const check = (val) => val ? '✅' : '❌';

            row.innerHTML = `
                <td>${p.externalId}</td>
                <td>${p.customerName}</td>
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
    } else {
        successSection.style.display = 'none';
    }

    reportSection.style.display = 'block';
    
    // Scroll hacia el reporte
    reportSection.scrollIntoView({ behavior: 'smooth' });
}