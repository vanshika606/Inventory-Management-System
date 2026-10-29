# Inventory Management System

## Project Overview
The Inventory Management System is a menu-driven Java application developed using Java JDBC and MySQL. It helps a retail store manage product records, update stock, modify prices, calculate inventory value, and delete products when their quantity is zero.

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Workbench
- IntelliJ IDEA
- MySQL Connector/J

## Database Details

**Database Name:** `retail_db`

**Table Name:** `Product`

| Column Name | Data Type | Description |
|---|---|---|
| product_id | INT | Primary key |
| product_name | VARCHAR(100) | Name of the product |
| category | VARCHAR(50) | Product category |
| quantity | INT | Available stock |
| price | DOUBLE | Price per product |

## Features

1. **Insert Products:** Insert three product records using `PreparedStatement` and handle duplicate product IDs.
2. **Search by Category:** Display products belonging to a selected category whose quantity is less than 20, sorted by price in descending order.
3. **Update Stock:** Add a specified quantity to the existing stock of a product.
4. **Update Product Price:** Search for a product and update its price only if the product exists and the new price is greater than zero.
5. **Calculate Inventory Value:** Calculate the total inventory value using `SUM(quantity * price)`.
6. **Delete Product:** Delete a product only when its quantity is zero.

## Database Setup

Execute the following SQL commands in MySQL Workbench:

```sql
CREATE DATABASE retail_db;
USE retail_db;

CREATE TABLE Product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    price DOUBLE
);
```

## Installation and Execution

1. Install the JDK and MySQL Server.
2. Create the database and table using the SQL commands above.
3. Download MySQL Connector/J from the official MySQL website.
4. Add the Connector/J JAR file to the project using **File → Project Structure → Libraries** in IntelliJ IDEA.
5. Create the `InventoryManagement.java` class and paste the Java source code.
6. Update the MySQL username and password in the Java code.
7. Run the `main()` method in IntelliJ IDEA.
8. Select an operation from the displayed menu.

## Configuration

Update the following database connection details in the Java program according to your MySQL setup:

```java
String url = "jdbc:mysql://localhost:3306/retail_db";
String user = "root";
String password = "your_password";
```

Replace `your_password` with your actual MySQL password.

## Concepts Used
- JDBC connection using `DriverManager`
- `Connection`, `PreparedStatement`, `Statement`, and `ResultSet`
- SQL INSERT, SELECT, UPDATE, and DELETE operations
- Aggregate functions using `SUM()`
- Conditional queries and sorting
- Exception handling
- Menu-driven programming using `switch` and `do-while`

## Expected Outcome

The application provides a simple way to manage inventory records and perform stock and price updates through a Java console interface connected to a MySQL database.

## Author
Student Project – Inventory Management System
