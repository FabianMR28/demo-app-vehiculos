package com.example.demo_app_vehiculos.report;

import com.example.demo_app_vehiculos.model.VentaBicicleta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class BoletaBicicletaPDFGenerator {

    public void generarBoleta(OutputStream outputStream, VentaBicicleta ventaBicicleta) {
        try {
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // 🔹 Encabezado con datos de la empresa
            Paragraph empresa = new Paragraph(
                    "BikePro Perú\nRUC: 20567890123\nAv. Los Andes 789 - Arequipa, Perú\nTeléfono: (+51) 999 888 777\nventas@bikepro.com",
                    new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL)
            );
            empresa.setAlignment(Element.ALIGN_LEFT);
            document.add(empresa);

            document.add(new Paragraph(" "));

            // 🔹 Título
            Paragraph titulo = new Paragraph("COMPROBANTE DE COMPRA - BICICLETA",
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));

            // 🔹 Datos generales
            document.add(new Paragraph("Fecha: " + ventaBicicleta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            document.add(new Paragraph("Comprobante N°: BK-" + ventaBicicleta.getFecha().getYear() + "-" + String.format("%04d", ventaBicicleta.getId())));
            document.add(new Paragraph("Vendedor: Fabian Medina"));
            document.add(new Paragraph("Sucursal: Arequipa - Av. Ejército"));
            document.add(new Paragraph(" "));

            // 🔹 Datos del cliente
            Paragraph datosCliente = new Paragraph("Datos del Cliente:", new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD));
            document.add(datosCliente);
            document.add(new Paragraph("Nombre: " + ventaBicicleta.getUsuario().getNombre()));
            document.add(new Paragraph("Correo: " + ventaBicicleta.getUsuario().getEmail()));
            document.add(new Paragraph("Teléfono: 957465634"));
            document.add(new Paragraph(" "));

            // 🔹 Detalles de la bicicleta
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setSpacingBefore(10f);

            tabla.addCell("Marca: " + ventaBicicleta.getBicicleta().getMarca());
            tabla.addCell("Modelo: " + ventaBicicleta.getBicicleta().getModelo());
            tabla.addCell("Tipo: " + ventaBicicleta.getBicicleta().getTipo());
            tabla.addCell("Condición: Nueva");
            tabla.addCell("Precio unitario: S/ " + String.format("%.2f", ventaBicicleta.getBicicleta().getPrecio()));
            tabla.addCell("Cantidad: 1");
            tabla.addCell("IGV (18%): S/ " + String.format("%.2f", ventaBicicleta.getTotal() * 0.18 / 1.18));
            tabla.addCell("Total a Pagar: S/ " + String.format("%.2f", ventaBicicleta.getTotal()));

            document.add(tabla);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Forma de Pago: Contado / Tarjeta / Transferencia"));
            document.add(new Paragraph("Observaciones: Bicicleta entregada con accesorios básicos y comprobante de garantía."));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Gracias por su compra - BikePro Perú © 2025",
                    new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC)));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generando la boleta PDF de bicicleta", e);
        }
    }
}
