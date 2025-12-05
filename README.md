Customer Segmentation App
README

Project Overview
The Customer Segmentation App is a Java desktop application that analyzes customer purchasing behavior using RFM analysis (Recency, Frequency, Monetary). The system supports full CRUD operations for customers and products, order creation and order-item tracking, automated RFM score computation, dynamic segment assignment, and revenue reporting. It uses a three-tier architecture consisting of a Java Swing presentation layer, a Java/JDBC logic layer, and a MySQL database backend. This project demonstrates proper database normalization, SQL scripting, JDBC integration, and GUI application design.

Project Directory Structure
app/src/
    db/                 Database connection utilities (DBConnection.java)
    dao/                Data Access Objects for SQL CRUD operations
    service/            Business logic connecting UI to DAO layer
    model/              Entity classes representing database records
    ui/console/         Console-based user interface
    ui/swing/           Java Swing GUI components (admin dashboard)
app/lib/                MySQL Connector/J JDBC driver
app/classes/            Compiled .class files
sql/
    create_schema.sql       Creates all tables, constraints, keys, and relationships
    initialize_data.sql     Inserts at least 15 entries per table with no NULL values
    rfm_views.sql           Builds SQL views for RFM metric calculation
    rfm_refresh.sql         Recomputes RFM scoring and updates CustomerSegments table
run-gui.sh               Script for launching the GUI application

Dependencies and Required Software
- Java JDK 11 or later  
- MySQL Server 8.0 or later  
- MySQL Connector/J (included in app/lib/)  
- MySQL Workbench (optional)

Database Setup Instructions
1. Start MySQL Server and open MySQL Workbench.
2. Create the database:
   CREATE DATABASE segdb;
3. Run the SQL scripts in this order:
   a. sql/create_schema.sql
   b. sql/initialize_data.sql
   c. sql/rfm_views.sql
   d. sql/rfm_refresh.sql
4. Verify that:
   - All tables contain at least 15 rows
   - No NULL values exist
   - All constraints and foreign keys are active

Database Configuration
The file db.properties must contain the following:
db.url=jdbc:mysql://localhost:3306/segdb
db.user=YOUR_USERNAME
db.password=YOUR_PASSWORD

Ensure the file is placed in app/src/db/ (same directory as DBConnection.java).

Running the Application (Terminal)
1. Compile all source files:
   javac -cp "app/lib/mysql-connector-j-9.0.0.jar" -d app/classes $(find app/src -name "*.java")

2. Run the GUI:
   java -cp "app/lib/mysql-connector-j-9.0.0.jar:app/classes" ui.swing.SwingApp

Running with Provided Script
On macOS/Linux:
   chmod +x run-gui.sh
   ./run-gui.sh

JDBC Code Requirements and Documentation
- All JDBC operations use prepared statements to prevent SQL injection.
- DAO classes (CustomerDAO, ProductDAO, OrderDAO, OrderItemDAO, SegmentDAO) include comments explaining:
  - Purpose of each method
  - Query being executed
  - How data is mapped between SQL and model objects
- Error handling is implemented using try-with-resources, ensuring connections, statements, and result sets close safely.
- DAO methods demonstrate the SQL used to create, read, update, and delete records.

SQL Script Requirements
create_schema.sql:
- Creates all BCNF-compliant tables: Customers, Products, Orders, OrderItems, Segments, CustomerSegments
- Includes NOT NULL constraints, PRIMARY KEY constraints, UNIQUE constraints (customer email), and CHECK constraints (quantity > 0)
- Includes ON DELETE CASCADE for Orders and OrderItems to maintain referential integrity
initialize_data.sql:
- Populates each table with at least 15 complete rows
- No NULL values in required fields
rfm_views.sql:
- Defines SQL views that compute RFM metrics using window functions (NTILE)
rfm_refresh.sql:
- Updates CustomerSegments with new RFM scores based on real data

Error Handling and Comments
- Service layer validates inputs before calling DAO methods.
- DAO methods catch SQL exceptions and provide meaningful error messages.
- GUI responds to database failures with clear dialog messages instead of raw stack traces.
- Comments are added to complex sections explaining intent and logic flow.

Permissions and Path Requirements
- Ensure MySQL user has permissions to create databases, create tables, and insert data.
- If using a non-default MySQL installation, update db.properties with the correct absolute path, URL, username, and password.
- If executing run-gui.sh, the script must be granted execution permission:
  chmod +x run-gui.sh

End of README
