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
