package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReportePesajePDFGenerator {

    public void generarReportePesaje(OutputStream outputStream, List<ReportePesajeDTO> registros) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Logo y datos de la empresa
            Paragraph empresa = new Paragraph(
                    "AutoTech\nRUC: 20456789012\nAv. Independencia 456 - Arequipa, Perú\nTeléfono: (+51) 000 000 000\ncontacto@autotech.com",
                    new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)
            );
            empresa.setAlignment(Element.ALIGN_LEFT);
            document.add(empresa);

            document.add(new Paragraph(" "));

            // 🔹 Título del reporte
            Paragraph titulo = new Paragraph(
                    "REPORTE DE PESAJE DE VEHÍCULOS",
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            // 🔹 Fecha de generación
            Paragraph fecha = new Paragraph(
                    "Fecha de generación: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)
            );
            fecha.setAlignment(Element.ALIGN_RIGHT);
            document.add(fecha);

            document.add(new Paragraph(" "));

            // 🔹 Tabla de solicitudes
            PdfPTable tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);
            tabla.setSpacingAfter(10f);
            tabla.setWidths(new float[]{2f, 2f, 3f, 2f, 2f, 3f});

            // Encabezado
            addCellEncabezado(tabla, "Placa");
            addCellEncabezado(tabla, "Tipo");
            addCellEncabezado(tabla, "Observaciones");
            addCellEncabezado(tabla, "Peso Total (kg)");
            addCellEncabezado(tabla, "Usuario");
            addCellEncabezado(tabla, "Fecha Registro");

            // Filas con datos
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            for (ReportePesajeDTO r : registros) {
                addCellTexto(tabla, r.getPlacaVehiculo());
                addCellTexto(tabla, r.getTipoVehiculo());
                addCellTexto(tabla, r.getObservaciones());
                addCellTexto(tabla, r.getPesoTotal() != null ? String.format("%.2f", r.getPesoTotal()) : "-");
                addCellTexto(tabla, r.getNombreUsuario());
                addCellTexto(tabla, r.getFechaRegistro() != null ? r.getFechaRegistro().format(formatter) : "-");
            }

            document.add(tabla);

            document.add(new Paragraph(" "));

            // 🔹 Pie de página
            Paragraph footer = new Paragraph(
                    "AutoTech - Sistema de Gestión de Pesaje © 2025",
                    new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)
            );
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando el reporte PDF de pesaje", e);
        }
    }

    private void addCellEncabezado(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addCellTexto(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.DARK_GRAY)));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
