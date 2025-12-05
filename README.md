# Customer Segmentation System  
A Java + MySQL Desktop Application Using RFM Analysis

## Overview
The Customer Segmentation System is a Java-based desktop application that helps businesses analyze customer purchasing behavior using RFM (Recency, Frequency, Monetary) analysis. The system allows administrators to manage customers, products, orders, and segments through a Swing GUI, while MySQL handles all data storage.

This project follows a three-tier architecture (Presentation → Logic → Data) and implements complete CRUD functionality with automatic segment classification.

## Features

### Customer and Product Management
- Create, update, delete customers  
- Create, update, delete products  
- Track product pricing and inventory  

### Orders and Order Items
- Create orders with multiple items  
- Automatic line total calculation  
- View all orders for any customer  

### RFM Segmentation
- Compute recency, frequency, and monetary values  
- Automatically assign segments to customers  
- Store and view segmentation results  

### Reporting
- Revenue by segment  
- Top spenders  
- Customers with no purchase history  

### Graphical User Interface
- Java Swing admin interface  
- Login screen  
- Panels for customers, products, orders, segments, and reports  

## Database Schema
The system uses MySQL with the following tables:

- Customers  
- Products  
- Orders  
- OrderItems  
- Segments  
- CustomerSegments  

All tables are in BCNF, include primary and foreign keys, and use cascading deletes to maintain referential integrity.

## Requirements

### Software
- Java 11 or higher  
- MySQL 8.0 or higher  
- MySQL Connector/J  
- VSCode, IntelliJ, or Eclipse  

### Configuration
Update the db.properties file:

db.url=jdbc:mysql://localhost:3306/segdb  
db.user=YOUR_USERNAME  
db.password=YOUR_PASSWORD  

## How to Run the Project

### 1. Create the Database
Run the following in MySQL Workbench or terminal:

CREATE DATABASE segdb;

Load the schema and sample data:

source sql/create_schema.sql;  
source sql/initialize_data.sql;  
source sql/rfm_views.sql;

### 2. Compile the Project
In the project root:

javac -cp "lib/mysql-connector-j-9.5.0.jar" -d classes $(find src -name "*.java")

### 3. Run the GUI

java -cp "lib/mysql-connector-j-9.5.0.jar:classes" ui.swing.SwingApp

## Usage

### Login
Any non-empty username and password is accepted (demo mode).

### Interface Navigation
Tabs include:
- Customers (CRUD)  
- Products (CRUD)  
- Orders (view-only)  
- RFM Segments (recompute)  
- Reports (revenue by segment)  


## Authors
Izabella Doser, Zara Rahim, Tabassum Zahir 
