# 🚗 Sistema de Gestión de Vehículos y Maquinaria – AutoTech

Aplicación web desarrollada como parte del **Proyecto Final del Curso Integrador I – Ingeniería de Sistemas e Informática (UTP)**.  
El sistema permite gestionar procesos de **ventas, cotizaciones, soporte técnico, pesajes, usuarios y vehículos** para la empresa AutoTech S.A.C.

---

## 📌 Características Principales

- Gestión de usuarios con roles (Administrador, Cliente, Empleado).
- Registro y administración de:
  - Autos
  - Bicicletas
  - Cotizaciones
  - Ventas
  - Solicitudes de Pesaje
  - Soporte Técnico
- Autenticación segura con Spring Security.
- Generación de **reportes y comprobantes en PDF** (cotizaciones, ventas, pesajes).
- Base de datos relacional MySQL.
- Interfaz web responsive (HTML + CSS + JS + Thymeleaf).
- Arquitectura en capas siguiendo buenas prácticas (Controller - Service - Repository - Model).

---

## 🛠️ Tecnologías Utilizadas

### **Backend**
- Java 21  
- Spring Boot 3.3.5  
- Spring Data JPA  
- Spring Security  
- Hibernate  
- Lombok  

### **Frontend**
- HTML5  
- CSS3  
- JavaScript  
- Thymeleaf  

### **Base de Datos**
- MySQL 8.x

### **Herramientas adicionales**
- iTextPDF (generación de PDFs)
- Docker (opcional)
- GitHub para versionamiento

---

## 🧱 Arquitectura del Proyecto

El proyecto utiliza una **arquitectura en capas**, organizada en paquetes:

controller/ -> Manejo de peticiones y rutas
service/ -> Lógica de negocio
repository/ -> Acceso a la base de datos (JPA)
model/ -> Entidades del sistema
dto/ -> Transferencia de datos
report/ -> Generación de PDFs
security/ -> Gestión de autenticación/autorización


---

## 🗄️ Modelo de Base de Datos

La base de datos está compuesta por las siguientes tablas:

- usuarios  
- autos  
- bicicletas  
- cotizaciones  
- ventas  
- soporte_tecnico  
- solicitudes_soporte  
- solicitudes_pesaje  
- pesajes  
- cotizaciones_bicicletas  
- ventas_bicicletas  

El script SQL completo se encuentra dentro del documento del proyecto.

---
 Instalación y Ejecución

Clonar el repositorio
```bash
git clone https://github.com/FabianMR28/demo-app-vehiculos.git
cd demo-app-vehiculos
2️⃣ Configurar la Base de Datos MySQL
Crear la base de datos:

sql
Copiar código
CREATE DATABASE autotech_db;
Actualizar application.properties:

ini
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/autotech_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=update
3️⃣ Ejecutar el Proyecto con Gradle
bash
Copiar código
./gradlew bootRun
🔐 Roles del Sistema
Rol	Permisos principales
Administrador	CRUD completo, reportes, ventas
Cliente	Cotizaciones, compras, soporte
Empleado	Pesajes, soporte técnico

🧪 Pruebas
El proyecto incluye pruebas unitarias con JUnit 5 y Mockito:

bash
Copiar código
./gradlew test
👨‍💻 Integrantes del Proyecto
Nombre	CUI
Robin José Fuentes Lastarria	U23223863
Daniel Saúl Medina Flores	U23213546
Fabián André Medina Rojas	U23212231

🧑‍🏫 Docente
MBA Mg. Ing. René Alonso Nieto Valencia

📍 Institución
Universidad Tecnológica del Perú – UTP
Facultad de Ingeniería
Curso Integrador I (2025)

📄 Licencia
Este proyecto es de uso académico.
