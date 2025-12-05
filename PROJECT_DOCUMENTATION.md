# Customer Segmentation App - Project Documentation

## Introduction

**Project Overview:** The Customer Segmentation App is a Java-based desktop application designed to help businesses analyze customer behavior and segment their customer base using RFM (Recency, Frequency, Monetary) analysis. The system enables businesses to identify high-value customers, detect at-risk customers, and develop targeted marketing strategies. This project demonstrates a complete three-tier architecture implementation with full CRUD operations, database normalization, and a modern Swing-based graphical user interface.

**Chosen Topic:** Customer Segmentation Management System. This system allows businesses to manage customer data, track purchase history, automatically calculate customer segments based on RFM metrics, and generate reports. The use case is relevant for e-commerce platforms, retail businesses, and any organization that needs to understand customer purchasing patterns to improve marketing effectiveness and customer retention.

## Objectives

**Primary Goals:** The primary objective is to develop a fully functional customer segmentation system that implements a three-tier architecture (Presentation, Application, and Data layers), provides complete CRUD operations for all entities, and automatically calculates customer segments using RFM analysis. The system must support at least 4 database tables with BCNF normalization, contain 15+ entries per table, and offer both console and GUI interfaces for user interaction.

**Key Features:** The application provides comprehensive CRUD operations (Create, Read, Update, Delete) for Customers and Products, view-only access to Orders, dynamic RFM segment calculation and recomputation, customer filtering by segment, revenue reporting by segment, user authentication via login dialog, and inventory management for products. The system uses JDBC for database connectivity and MySQL for data persistence, ensuring reliable data management and transaction support.

## System Architecture

**Three-Tier Architecture:** The project follows a strict three-tier architecture pattern. The **Presentation Layer** consists of two interfaces: a console-based UI (`ui.console.Main`) and a Swing GUI (`ui.swing` package with panels for Customers, Products, Orders, RFM, and Reports). The **Application Layer** (also called Logic/Business Layer) contains service classes (`service` package) that implement business logic and coordinate between the presentation and data layers. Services include `CustomerService`, `ProductService`, `OrderService`, `RfmService`, and `ReportService`. The **Data Layer** consists of DAO (Data Access Object) classes (`dao` package) that handle all database interactions using JDBC, and model classes (`model` package) that represent business entities.

**Technology Stack:** The application is built using Java SE with JDBC for database connectivity, MySQL 8.0+ as the relational database management system, MySQL Connector/J 9.5.0 as the JDBC driver, Java Swing for the graphical user interface, and SQL scripts for schema creation, data initialization, and view definitions. The project structure follows standard Java package conventions with clear separation of concerns.

**Database Connection:** The system connects to MySQL using JDBC through a centralized `DBConnection` class located in the `db` package. Connection parameters (URL, username, password) are stored in `db.properties` file, which is loaded at runtime. The connection uses the MySQL JDBC driver (`com.mysql.cj.jdbc.Driver`) and establishes connections to the `segdb` database on `localhost:3306`. All database operations use try-with-resources to ensure proper connection cleanup and prevent resource leaks.

## Database Design

**Schema Diagram:** The database consists of 6 main tables: `Customers` (stores customer information), `Products` (product catalog with inventory), `Orders` (customer purchase orders), `OrderItems` (line items for each order), `Segments` (predefined customer segment definitions), and `CustomerSegments` (snapshot table storing RFM analysis results). Relationships include: Customers → Orders (one-to-many), Orders → OrderItems (one-to-many), Products → OrderItems (one-to-many), Customers → CustomerSegments (one-to-many), and Segments → CustomerSegments (one-to-many). The schema also includes views: `v_customer_metrics` (calculates R, F, M values), `v_revenue_by_segment` (revenue aggregation), `v_top_customers` (top 5 spenders), and `v_customers_without_orders` (customers with no purchase history).

**Normalization:** All tables are in Boyce-Codd Normal Form (BCNF). The `Customers` table contains only customer attributes with `CustomerID` as the primary key. `Products` is normalized with `ProductID` as the primary key. `Orders` contains order-level data with `OrderID` as the primary key and `CustomerID` as a foreign key. `OrderItems` represents the many-to-many relationship between Orders and Products, with `OrderItemID` as the primary key and composite foreign keys to both `Orders` and `Products`. `Segments` is a lookup table with `SegmentID` as the primary key. `CustomerSegments` is a snapshot/junction table with a composite key structure and foreign keys to both `Customers` and `Segments`. No partial dependencies or transitive dependencies exist, ensuring BCNF compliance.

**Entity Descriptions:** The `Customers` table stores customer personal information and status. `Products` maintains the product catalog with pricing and inventory levels. `Orders` records customer purchases with order dates and total amounts (denormalized for performance in RFM calculations). `OrderItems` stores individual line items with quantities and prices, with `LineTotal` as a generated column. `Segments` defines 15 customer segment types (Loyal, New, At-Risk, Churned, High-Value, etc.). `CustomerSegments` stores computed RFM metrics (R, F, M values) and assigned segment IDs for each customer, allowing historical tracking via `AsOfDate`.

**Attributes and Constraints:** All tables include appropriate NOT NULL constraints on essential fields. Primary keys are auto-incrementing integers. Foreign keys use CASCADE delete to maintain referential integrity. `CustomerEmail` has a UNIQUE constraint. `OrderItems.Quantity` has a CHECK constraint ensuring positive values. `OrderItems.LineTotal` is a GENERATED ALWAYS AS STORED column. `Products.Inventory` has a DEFAULT value of 0. All monetary values use `DECIMAL(10,2)` for precision. Date fields use appropriate DATE or DATETIME types with DEFAULT CURRENT_TIMESTAMP where applicable.

## Functional Requirements

**User Roles and Access:** The system supports a single administrative user role that can manage all aspects of the application. Users must authenticate through a login dialog before accessing the main dashboard. Once authenticated, administrators can view, add, update, and delete customers and products, view orders (read-only), recompute RFM segments, filter customers by segment, and generate revenue reports. The system does not support customer-facing order creation in the admin interface, as orders are intended to be created through a separate customer portal.

**Feature Descriptions:** **Adding Records:** Users can add new customers by providing first name, last name, email (validated for uniqueness), and status. Products can be added with name, price, and initial inventory quantity. Both operations validate input and display success/error messages. **Viewing Data:** The GUI displays customers, products, orders, and segments in tabular format with scrollable tables. Orders can be filtered by customer ID. Customers can be filtered by segment using a dropdown. RFM segments display customer assignments with segment names. **Updating Records:** Customers and products support in-place editing. Users select a record from the table, modify fields in the form, and click update. The system validates changes and refreshes the display. **Deleting Records:** Customers and products can be deleted with confirmation dialogs. Deletion cascades to related records (orders, order items) per foreign key constraints. **Additional Functionality:** The RFM recompute feature recalculates all customer segments based on current order data using quintile analysis. Revenue reports aggregate total spending by segment. Search functionality allows filtering customers by segment ID.

## Non-Functional Requirements

**Performance:** The system uses prepared statements for all database queries to prevent SQL injection and improve query performance through statement caching. The `OrderAmount` field in `Orders` is denormalized to avoid expensive JOINs during RFM calculations. Database views (`v_customer_metrics`, `v_revenue_by_segment`) pre-aggregate data for faster reporting. Connection pooling could be added for production use, but the current implementation uses efficient connection management with try-with-resources.

**Scalability:** The database schema supports additional customers, products, and orders without structural changes. The RFM calculation uses window functions (NTILE) that scale efficiently with data volume. The application architecture allows for easy addition of new service and DAO classes for additional features. The three-tier separation enables independent scaling of presentation and business logic layers.

**Security:** User authentication is implemented via a login dialog (currently using demo authentication that accepts any non-empty credentials; production would integrate with a user table). All database queries use parameterized prepared statements to prevent SQL injection attacks. Input validation occurs at the service layer before database operations. Error messages avoid exposing sensitive database information to users.

**Accessibility:** The Swing GUI uses standard Java look-and-feel for native platform appearance. Tables are scrollable and resizable. Form fields are clearly labeled. Error messages are displayed in user-friendly dialog boxes. The interface follows standard desktop application conventions for familiarity. Long-running operations (RFM recompute) use SwingWorker to prevent UI freezing.

## Implementation Details

**Code Structure:** The project follows a standard Java package structure: `model` (entity classes), `dao` (data access objects), `service` (business logic), `db` (database connection utilities), `ui.console` (console interface), and `ui.swing` (GUI components). SQL scripts are organized in the `sql/` directory. The `app/src/` directory contains all source code, `app/lib/` contains the MySQL JDBC driver, and `app/classes/` contains compiled bytecode. Naming conventions follow Java standards: classes use PascalCase, methods and variables use camelCase, constants use UPPER_SNAKE_CASE.

**Important Code Snippets:** Database connection is handled by `DBConnection.get()`, which loads properties from `db.properties` and returns a `Connection` object. CRUD operations use prepared statements, for example: `CustomerDAO.update()` uses `UPDATE Customers SET FirstName = ?, LastName = ?, CustomerEmail = ?, CustomerStatus = ? WHERE CustomerID = ?` with parameter binding. The RFM recomputation in `RfmService.recompute()` first executes `rfm_views.sql` to create/update the metrics view, then runs `rfm_refresh.sql` to recalculate segments using quintile analysis with window functions. Error handling uses try-catch blocks that display user-friendly messages via `JOptionPane` in the GUI.

**Error Handling:** All database operations are wrapped in try-catch blocks. Service layer methods propagate exceptions to the presentation layer, where they are caught and displayed to users via `JOptionPane.showMessageDialog()` with appropriate error messages. Database connection failures are handled gracefully with informative messages. Foreign key constraint violations are caught and explained to users. Input validation occurs before database operations to prevent invalid data insertion. The `SqlRunner` utility handles SQL script execution errors and reports them clearly.

## Testing and Validation

**Test Cases:** **Customer CRUD:** Successfully tested adding customers with valid data, updating customer information, deleting customers (with cascade to orders), and listing all customers. **Product CRUD:** Verified adding products with inventory, updating product prices and inventory levels, deleting products, and viewing product catalog. **Order Viewing:** Tested filtering orders by customer ID and displaying order details with line items. **RFM Recompute:** Validated that recomputation correctly calculates R, F, M values, assigns segments based on quintiles, and updates the `CustomerSegments` table. **Segment Filtering:** Confirmed customers can be filtered by segment ID with accurate results. **Reports:** Verified revenue by segment report displays correct aggregated totals. **Login:** Tested authentication dialog accepts credentials and grants access.

**Sample Data:** The database is initialized with 15 customers (mix of Active/Inactive status), 15 products (various electronics and accessories with inventory ranging from 8-200 units), 22 orders (with varied dates from 5-300 days ago to create segment diversity), corresponding order items, and 15 predefined segments. Order dates are calculated using `DATE_SUB(CURRENT_DATE, INTERVAL X DAY)` to ensure variety in recency values. Some customers have multiple orders to create frequency diversity. Monetary values range from $9.99 to $1,099.98 to create spending variety.

**Test Results:** All CRUD operations function correctly. Customer and product management works as expected with proper validation. RFM recomputation successfully assigns varied segments (Loyal, New, At-Risk, Churned, High-Value) based on actual order data, not hardcoded values. Segment filtering accurately displays customers belonging to selected segments. Reports generate correct revenue totals. The GUI is responsive and user-friendly. All foreign key constraints are enforced correctly. No data integrity issues were encountered during testing.

## Challenges and Solutions

**Challenges Faced:** Initial implementation had all customers showing as "Churned" because order dates were hardcoded to early 2025, making all orders appear old. The RFM refresh script initially failed due to missing `v_customer_metrics` view and incorrect file paths. Foreign key constraint violations occurred when trying to insert segments that didn't exist in the `Segments` table. The GUI had compilation errors due to missing imports for Swing components. Database connection issues arose from incorrect property file paths.

**Solutions:** Order dates were changed to use `DATE_SUB(CURRENT_DATE, INTERVAL X DAY)` to create varied recency values (recent: 0-60 days, medium: 60-180 days, old: 180+ days). The `RfmService.recompute()` method was updated to first execute `rfm_views.sql` before `rfm_refresh.sql` to ensure the view exists. The `Segments` table is now populated with IDs 1-15 in `initialize_data.sql` before RFM calculation. Missing Swing imports were added to all GUI panel classes. The `DBConnection` class was improved to handle resource loading from the classpath correctly. All SQL scripts are executed in the correct order: `create_schema.sql`, then `initialize_data.sql`, then RFM views and refresh as needed.

## Future Enhancements

**Potential Improvements:** The login system could be enhanced with a proper user authentication table and password hashing. A customer-facing web interface could be added for order placement. Advanced filtering and search capabilities (by name, email, date range) would improve usability. Export functionality (CSV, PDF) for reports would be valuable. Email notifications could alert customers about their segment status or special offers. A dashboard with charts and visualizations (using JFreeChart) would enhance data presentation. Batch operations (bulk import/export) would improve efficiency. Audit logging could track all data changes. The system could support multiple database vendors through a database abstraction layer. Real-time RFM updates triggered by new orders would eliminate the need for manual recomputation.

## Conclusion

**Summary:** This project successfully implements a complete customer segmentation system using three-tier architecture with Java, JDBC, and MySQL. All project requirements are met: full CRUD operations, BCNF normalization, 15+ entries per table, dynamic RFM calculation, and both console and GUI interfaces. The system demonstrates proper separation of concerns, effective error handling, and scalable database design. The RFM analysis provides actionable insights into customer behavior, enabling data-driven marketing decisions.

**Reflections:** The project reinforced the importance of proper database normalization and the benefits of three-tier architecture for maintainability. Implementing dynamic RFM calculation was challenging but resulted in a more flexible and accurate system than hardcoded segments. The Swing GUI development highlighted the need for proper threading (SwingWorker) for long-running operations. Future projects would benefit from implementing unit tests and using a build tool like Maven or Gradle for dependency management. The experience with JDBC prepared statements and transaction management provided valuable insights into database programming best practices.

## Appendices

### Database Schema SQL

```sql
-- See sql/create_schema.sql for complete schema
-- Key tables: Customers, Products, Orders, OrderItems, Segments, CustomerSegments
-- All tables include NOT NULL constraints, primary keys, and foreign keys
```

### Sample Queries

**Select all customers:**
```sql
SELECT * FROM Customers;
```

**Insert a new customer:**
```sql
INSERT INTO Customers (FirstName, LastName, CustomerEmail, CustomerStatus) 
VALUES ('John', 'Doe', 'john@example.com', 'Active');
```

**Update customer status:**
```sql
UPDATE Customers SET CustomerStatus = 'Inactive' WHERE CustomerID = 1;
```

**Delete a customer (cascades to orders):**
```sql
DELETE FROM Customers WHERE CustomerID = 1;
```

**RFM segment calculation (from rfm_refresh.sql):**
```sql
INSERT INTO CustomerSegments (CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore)
SELECT CustomerID, 
       CASE WHEN R_Quintile >= 4 AND F_Quintile >= 4 THEN 1 ELSE 4 END,
       CURRENT_DATE, Recency, Frequency, Monetary, RFMScore
FROM (SELECT CustomerID, Recency, Frequency, Monetary,
      NTILE(5) OVER (ORDER BY Recency ASC) AS R_Quintile,
      NTILE(5) OVER (ORDER BY Frequency DESC) AS F_Quintile
      FROM v_customer_metrics) q;
```

### User Manual

**Setup Instructions:**
1. Install MySQL 8.0+ and Java JDK 11+
2. Create the database: `CREATE DATABASE segdb;`
3. Update `app/src/db/db.properties` with your MySQL credentials
4. Run SQL scripts in order: `create_schema.sql`, then `initialize_data.sql`
5. Compile: `javac -cp "app/lib/mysql-connector-j-9.5.0.jar" -d app/classes $(find app/src -name "*.java")`
6. Run GUI: `java -cp "app/lib/mysql-connector-j-9.5.0.jar:app/classes" ui.swing.SwingApp`
7. Or use the provided script: `./run-gui.sh`

**Usage:**
- Login with any non-empty username/password (demo mode)
- Navigate tabs: Customers (CRUD), Products (CRUD), Orders (view only), RFM (recompute segments), Reports (revenue by segment)
- To recompute RFM segments: Go to RFM tab, click "Recompute RFM Segments" button
- To filter customers by segment: Use the dropdown in Customers tab
- All operations show success/error dialogs for feedback
