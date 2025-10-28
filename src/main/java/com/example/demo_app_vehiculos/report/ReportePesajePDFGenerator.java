package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ReportePesajePDFGenerator {

    private static final Font TITULO = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
    private static final Font SUBTITULO = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private static final Font TEXTO = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.DARK_GRAY);

    public void generarReportePesaje(OutputStream outputStream, List<ReportePesajeDTO> registros) throws DocumentException {
        Document document = new Document(PageSize.A4, 36, 36, 72, 36);
        PdfWriter.getInstance(document, outputStream);

        document.open();

        // Título
        Paragraph titulo = new Paragraph("Reporte de Pesaje de Vehículos", TITULO);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // Fecha de generación
        Paragraph fecha = new Paragraph(
                "Fecha de generación: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                TEXTO
        );
        fecha.setAlignment(Element.ALIGN_RIGHT);
        fecha.setSpacingAfter(10);
        document.add(fecha);

        // Tabla
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

        // Filas
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

        // Pie de página
        Paragraph footer = new Paragraph("© AutoTech - Sistema de Gestión de Pesaje", TEXTO);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        document.add(footer);

        document.close();
    }

    private void addCellEncabezado(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, SUBTITULO));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        table.addCell(cell);
    }

    private void addCellTexto(PdfPTable table, String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, TEXTO));
        cell.setPadding(5);
        table.addCell(cell);
    }
}
