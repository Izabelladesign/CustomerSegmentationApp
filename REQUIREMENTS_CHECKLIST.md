# Project Requirements Checklist

## ✅ Database Design

### ER Diagram
- [ ] **TODO**: Create ER diagram showing entities, attributes, and relationships
- **Note**: Should be included in project report/documentation

### Schema Definition
- ✅ **COMPLETE**: Schema defined in `sql/create_schema.sql`
- ✅ **6 Tables** (exceeds minimum of 4):
  1. Customers
  2. Products
  3. Orders
  4. OrderItems
  5. Segments
  6. CustomerSegments

### Normalization
- ✅ **BCNF Form**: All tables are in Boyce-Codd Normal Form
- ✅ **Verified**: See `BCNF_ANALYSIS.md` for detailed analysis
- All functional dependencies have candidate keys as determinants

---

## ✅ SQL Requirements

### Create Database Tables
- ✅ **COMPLETE**: All 6 tables created in `sql/create_schema.sql`
- ✅ **NOT NULL Constraints**: All required fields have NOT NULL constraints
  - Customers: FirstName, LastName, CustomerEmail, CustomerStatus, CreatedAt
  - Products: ProductName, ProductPrice, Inventory (default 0)
  - Orders: CustomerID, OrderDate, OrderAmount
  - OrderItems: OrderID, ProductID, Quantity, UnitPrice, LineTotal
  - Segments: SegmentName, Description
  - CustomerSegments: CustomerID, SegmentID, AsOfDate, R, F, M, RFMScore

### Initialize Tables
- ✅ **COMPLETE**: `sql/initialize_data.sql` contains:
  - **15+ Customers** ✅
  - **15+ Products** ✅
  - **22 Orders** ✅ (exceeds 15)
  - **28 OrderItems** ✅ (exceeds 15)
  - **15 Segments** ✅
  - **CustomerSegments**: Calculated dynamically (not hardcoded) ✅

### SQL Statements (CRUD Operations)

#### ✅ SELECT (Display existing information)
- **Customers**: `CustomerDAO.listAll()` - View all customers
- **Products**: `ProductDAO.listAll()` - View all products
- **Orders**: `OrderDAO.listByCustomer()` - View orders by customer
- **OrderItems**: `OrderItemDAO.listByOrder()` - View items in an order
- **Segments**: `SegmentDAO.listAll()` - View all segments
- **CustomerSegments**: `CustomerSegmentDAO.listAll()` - View customer segments
- **Reports**: `ReportsDAO.revenueBySegment()` - View revenue reports

#### ✅ INSERT (Add new)
- **Customers**: `CustomerDAO.insert()` - Add new customer
- **Products**: `ProductDAO.insert()` - Add new product
- **Orders**: `OrderDAO.insertOrder()` - Add new order (for customer use)
- **OrderItems**: `OrderItemDAO.insertItem()` - Add order item

#### ✅ UPDATE (Edit/Update existing information)
- **Customers**: `CustomerDAO.update()` - Update customer information
- **Products**: `ProductDAO.update()` - Update product information
- **Orders**: `OrderDAO.updateOrderAmount()` - Update order amount
- **OrderItems**: `OrderItemDAO.updateQuantity()` - Update item quantity

#### ✅ DELETE (Delete existing information)
- **Customers**: `CustomerDAO.delete()` - Delete customer
- **Products**: `ProductDAO.delete()` - Delete product
- **Orders**: `OrderDAO.deleteOrder()` - Delete order
- **OrderItems**: `OrderItemDAO.deleteItem()` - Delete order item

---

## ✅ Programming Requirements

### JDBC Code
- ✅ **Well-documented**: Comments explain purpose of each section
- ✅ **SQL Statements**: All CRUD operations use PreparedStatement
- ✅ **Error Handling**: Try-with-resources for connection management
- ✅ **Code Organization**: Logical folder structure (dao/, service/, model/, ui/)

### SQL Scripts
- ✅ **create_schema.sql**: Recreates database schema with all constraints
- ✅ **initialize_data.sql**: Populates tables with 15+ entries per table
- ✅ **rfm_refresh.sql**: Calculates RFM segments dynamically
- ✅ **rfm_views.sql**: Creates customer metrics view
- ✅ **reports.sql**: Creates reporting views

### Error Handling
- ✅ **Database Connection Errors**: Handled in DBConnection.java
- ✅ **SQL Execution Errors**: Try-catch blocks in all DAOs
- ✅ **User Input Validation**: Service layer validates inputs

### Comments
- ✅ **Code Comments**: Methods have JavaDoc-style comments
- ✅ **SQL Comments**: SQL files have explanatory comments
- ✅ **Complex Logic**: RFM calculation logic is documented

---

## ✅ Functional Requirements

### Users and Access
- **Admin Users**: Access via GUI (Swing) or Console interface
- **Login Feature**: Login dialog before accessing main application
- **Access Methods**:
  - GUI: `ui.swing.SwingApp` - Full graphical interface
  - Console: `ui.console.Main` - Command-line interface

### Functionality/Features

#### Customer Management
- ✅ List all customers
- ✅ Add new customer
- ✅ Update customer information
- ✅ Delete customer
- ✅ Filter customers by segment
- ✅ Search customers

#### Product Management
- ✅ List all products
- ✅ Add new product
- ✅ Update product information (name, price, inventory)
- ✅ Delete product
- ✅ View inventory levels

#### Order Management
- ✅ View orders by customer ID
- ✅ View order details
- ⚠️ **Note**: Order creation removed (customers create orders, not admins)

#### RFM Segmentation
- ✅ Recompute RFM segments (calculates dynamically from order data)
- ✅ View customer segments with R, F, M values and RFM scores
- ✅ Segment assignment based on:
  - Recency (days since last order)
  - Frequency (number of orders)
  - Monetary (total spending)

#### Reports
- ✅ Revenue by segment
- ✅ Customer count per segment

### Input/Output
- **Input**: GUI forms, console prompts, database queries
- **Output**: Tables, formatted displays, success/error messages

---

## ✅ Non-Functional Requirements (Optional)

### Performance
- Efficient database queries using indexes (primary keys, foreign keys)
- Connection pooling ready (can be enhanced)

### Scalability
- Three-tier architecture allows for horizontal scaling
- Service layer can be enhanced with caching

### Security
- Login authentication (demo mode - can be enhanced with database)
- SQL injection prevention via PreparedStatement
- Password not stored in plain text (future enhancement)

---

## 📋 Summary

| Requirement | Status | Notes |
|------------|--------|-------|
| BCNF Normalization | ✅ Complete | All 6 tables verified |
| At least 4 tables | ✅ Complete | 6 tables |
| 15+ entries per table | ✅ Complete | All tables have 15+ entries |
| NOT NULL constraints | ✅ Complete | All required fields have NOT NULL |
| SELECT operations | ✅ Complete | All tables |
| INSERT operations | ✅ Complete | Customers, Products, Orders, OrderItems |
| UPDATE operations | ✅ Complete | Customers, Products, Orders, OrderItems |
| DELETE operations | ✅ Complete | Customers, Products, Orders, OrderItems |
| SQL Scripts | ✅ Complete | create_schema.sql, initialize_data.sql |
| Error Handling | ✅ Complete | Try-catch blocks throughout |
| Code Comments | ✅ Complete | Well-documented |
| Three-tier Architecture | ✅ Complete | Presentation, Application, Data layers |

---

## 📝 Notes

1. **Order Creation**: Removed from admin interface as per requirements - customers create orders, not administrators
2. **RFM Calculation**: Now fully dynamic - calculated from actual order data, not hardcoded
3. **Inventory**: Added to Products table with NOT NULL default 0
4. **Customer Segments**: Calculated dynamically via `rfm_refresh.sql` - no hardcoded values

