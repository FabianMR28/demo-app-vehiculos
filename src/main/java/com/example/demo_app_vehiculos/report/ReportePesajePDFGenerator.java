package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.repository.SolicitudPesajeRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReportePesajePDFGenerator {

    private final SolicitudPesajeRepository solicitudPesajeRepository;

    public ReportePesajePDFGenerator(SolicitudPesajeRepository solicitudPesajeRepository) {
        this.solicitudPesajeRepository = solicitudPesajeRepository;
    }

    public void generarReportePesaje(OutputStream outputStream) {
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Reporte de Solicitudes de Pesaje", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fecha de generación: " +
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            document.add(new Paragraph(" "));

            // 🔹 Tabla de pesajes
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            String[] headers = {"ID", "Placa", "Tipo Vehículo", "Peso Total", "Observaciones", "Usuario"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<SolicitudPesaje> solicitudes = solicitudPesajeRepository.findAll();
            for (SolicitudPesaje s : solicitudes) {
                table.addCell(String.valueOf(s.getId()));
                table.addCell(s.getPlacaVehiculo());
                table.addCell(s.getTipoVehiculo());
                table.addCell(s.getPesoTotal() != null ? s.getPesoTotal().toString() : "-");
                table.addCell(s.getObservaciones() != null ? s.getObservaciones() : "-");
                table.addCell(s.getUsuario() != null ? s.getUsuario().getNombre() : "-");
            }

            document.add(table);

            document.add(new Paragraph("Fin del reporte de pesajes.", new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte de pesaje PDF", e);
        }
    }
}
