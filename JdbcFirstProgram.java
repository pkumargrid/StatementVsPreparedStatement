import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcFirstProgram {
    public static void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/prakumar", "prakumar","prakumar")){
            System.out.println("Connected to postgres database");
            try(Statement statement = connection.createStatement()) {
                final String drop_table = """
                        drop table if exists ValueTable
                        """;
                statement.executeUpdate(drop_table);
                final String create_table = """
                CREATE TABLE ValueTable (
                    ID SERIAL PRIMARY KEY,
                    Category VARCHAR(50),
                    Value VARCHAR(100)
                );
                    """;
                    statement.executeUpdate(create_table);
                    final String add_values = """
                        INSERT INTO ValueTable (Category, Value) VALUES
                        ('Category1', 'Value1'),
                        ('Category1', 'Value2'),
                        ('Category1', 'Value3'),
                        ('Category2', 'Value4'),
                        ('Category2', 'Value5'),
                        ('Category2', 'Value6'),
                        ('Category3', 'Value7'),
                        ('Category3', 'Value8'),
                        ('Category3', 'Value9'),
                        ('Category4', 'DummyValue1'),
                        ('Category4', 'DummyValue2'),
                        ('Category4', 'DummyValue3'),
                        ('CategoryAdmin','VeryImp');
                            """;
                    statement.executeUpdate(add_values);
            }

            System.out.println("done with making dummy table");
            System.out.println("Table contains category with its value");
            System.out.println("Enter the category by which you want the results to be filtered by");
            System.out.println("Available category:");
            for(int i = 1; i <= 4; i++) {
                System.out.println("Category" + i);
            }
            try(Scanner scanner = new Scanner(System.in)) {
                String category = scanner.nextLine();

                usingPreparedStatement(connection, category);
                // usingStatement(connection, category);
            }
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    public static void usingStatement(Connection connection, String category) throws SQLException {
        Statement statement = connection.createStatement();
        final String statement_filter = "SELECT * FROM ValueTable WHERE Category = '" + category + "'";
        System.out.println(statement_filter);
        try(ResultSet resultSet = statement.executeQuery(statement_filter)) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        }
    }
    public static void usingPreparedStatement(Connection connection, String category) throws SQLException {
        final String statement_filter = "select * from ValueTable where Category = ?";
        PreparedStatement statement = connection.prepareStatement(statement_filter);
        statement.setString(1, category);
        try(ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        }
    }
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
            return;
        }
        init();
    }
}