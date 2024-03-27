# Task 1
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
# PreparedStatement vs. Statement in Java

## PreparedStatement

- **Parameterized Queries**: Supports parameterized queries, allowing placeholders for values that will be supplied later. This helps prevent SQL injection attacks and ensures proper handling of data.
- **Performance**: Precompiled by the database server, improving performance, especially for repeated executions of the same query with different parameters.
- **Binary Data Handling**: Provides specific methods (`setBinaryStream()`, `setBlob()`, etc.) for handling binary data, ensuring correct transmission and storage in the database.
- **Readability and Maintainability**: Separates the SQL query from data values, making code more readable and maintainable.

## Statement

- **Static Queries**: Executes static SQL queries without placeholders for parameters. Values are concatenated directly into the query string, which can lead to SQL injection vulnerabilities.
- **No Precompilation**: Queries are not precompiled, potentially leading to performance issues, especially for repeated executions.
- **Limited Binary Data Handling**: Does not provide specialized methods for handling binary data. Binary data must be manually encoded and embedded in the SQL query.
- **Concatenation of Values**: Values are concatenated directly into the query string, making it harder to read and maintain, and increasing the risk of syntax errors and injection attacks.

## Summary

- Use `PreparedStatement` for parameterized queries, improved performance, and proper handling of binary data.
- Use `Statement` only for simple, static queries where parameterization is not required and security risks are minimal.

# Task 2
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Practical Example of
## Sql injection

### Prerequisites

Before running the Java program, ensure you have the following:

- Java Development Kit (JDK) installed
- PostgreSQL installed or running in a Docker container

## Running PostgreSQL in Docker

To run a Docker container for PostgreSQL, use the following command:

```bash
docker run --name my-postgres-container -e POSTGRES_PASSWORD=mysecretpassword -d postgres


```
# Java JDBC Example with Prepared Statement

This Java application demonstrates the use of JDBC (Java Database Connectivity) with a PostgreSQL database, focusing on preventing SQL injection by using prepared statements.

## Environment Setup

1. **Compile the Java File:**
   ```
   javac JdbcFirstExample.java
   ```

2. **Run the Java Program:**
   ```
   java -classpath .:postgresql-42.7.3.jar JdbcFirstExample
   ```

   Ensure to pass the PostgreSQL JDBC driver (`postgresql-42.7.3.jar`) at runtime.

## Database Schema and Data

Consider a dummy table named `ValueTable` with columns `Category` and `Value`. Below is a sample schema and data:

| Category     | Value        |
|--------------|--------------|
| Category1    | Value1       |
| Category1    | Value2       |
| ...          | ...          |
| CategoryAdmin| VeryImp      |

## SQL Injection Scenario

### Using Statement (Vulnerable)

The application prompts the user to filter the table by `Category`. The SQL query is constructed as follows:

```java
"SELECT * FROM ValueTable WHERE Category = '" + category + "'"
```

If the user manipulates the input by sending `category` as `Category3' or 1=1--`, it would result in:

```sql
SELECT * FROM ValueTable WHERE Category = 'Category3' or 1=1--
```

This could potentially return all values, including those restricted for admin.

### Using Prepared Statement (Safe)

Alternatively, the application uses a prepared statement with a parameterized query:

```java
"SELECT * FROM ValueTable WHERE Category = ?"
```

When executed, this method prevents SQL injection attempts, ensuring that unexpected input like `Category3' or 1=1--` is treated as a literal value for `Category`, hence preventing any injection.
```
