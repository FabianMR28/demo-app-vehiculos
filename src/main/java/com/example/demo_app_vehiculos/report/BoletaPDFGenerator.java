package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.repository.VentaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class BoletaPDFGenerator {

    private final VentaRepository ventaRepository;

    public BoletaPDFGenerator(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public void generarBoleta(OutputStream outputStream) {
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Encabezado
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph titulo = new Paragraph("Boleta de Venta - AutoTech", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Fecha de emisión: " +
                    java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            document.add(new Paragraph(" "));

            // 🔹 Tabla de ventas
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            String[] headers = {"ID", "Cliente", "Producto", "Cantidad", "Total (S/.)"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<Venta> ventas = ventaRepository.findAll();

            for (Venta v : ventas) {
                table.addCell(String.valueOf(v.getId()));
                table.addCell(v.getUsuario() != null ? v.getUsuario().getNombre() : "N/A");
                table.addCell(v.getAuto() != null ? v.getAuto().getMarca() + " " + v.getAuto().getModelo() : "N/A");
                table.addCell(v.getFecha() != null ? v.getFecha().toString() : "N/A");
                table.addCell(String.format("S/ %.2f", v.getTotal()));
            }


            document.add(table);

            document.add(new Paragraph("Gracias por su compra.", new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando boleta PDF", e);
        }
    }
}
