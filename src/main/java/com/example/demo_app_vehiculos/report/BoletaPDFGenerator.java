package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.model.Venta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class BoletaPDFGenerator {

    public void generarBoleta(OutputStream outputStream, Venta venta) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Logo y datos de la empresa
            Paragraph empresa = new Paragraph("AutoTech\nRUC: 20456789012\nAv. Independencia 456 - Arequipa, Perú\nTeléfono: (+51) 000 000 000\nventas@autotech.com",
                    new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL));
            empresa.setAlignment(Element.ALIGN_LEFT);
            document.add(empresa);

            document.add(new Paragraph(" "));

            // 🔹 Título
            Paragraph titulo = new Paragraph("COMPROBANTE DE COMPRA - VEHÍCULO", 
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            // 🔹 Datos generales
            document.add(new Paragraph("Fecha: " + venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            document.add(new Paragraph("Comprobante N°: AT-" + venta.getFecha().getYear() + "-" + String.format("%04d", venta.getId())));
            document.add(new Paragraph("Vendedor: Fabian Medina"));
            document.add(new Paragraph("Sucursal: Arequipa - Centro"));
            document.add(new Paragraph(" "));

            // 🔹 Datos del cliente
            Paragraph datosCliente = new Paragraph("Datos del Cliente:", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD));
            document.add(datosCliente);
            document.add(new Paragraph("Nombre: " + venta.getUsuario().getNombre()));
            document.add(new Paragraph(" "));

            // 🔹 Detalles del vehículo
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);

            tabla.addCell("Marca: " + venta.getAuto().getMarca());
            tabla.addCell("Modelo: " + venta.getAuto().getModelo());
            tabla.addCell("Año: " + venta.getAuto().getAnio());
            tabla.addCell("Condición: Nuevo");
            tabla.addCell("Kilometraje: 0 km");
            tabla.addCell("Precio base: S/ " + String.format("%.2f", venta.getTotal() / 1.18));
            tabla.addCell("IGV (18%): S/ " + String.format("%.2f", venta.getTotal() * 0.18 / 1.18));
            tabla.addCell("Total a Pagar: S/ " + String.format("%.2f", venta.getTotal()));

            document.add(tabla);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Forma de Pago: Contado / Transferencia / Crédito"));
            document.add(new Paragraph("Observaciones: Vehículo entregado con manual, juego de llaves y certificado de garantía."));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Gracias por su compra - AutoTech © 2025", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando la boleta PDF", e);
        }
    }
}
