package br.com.estoque.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "joaoG2004";

   private static final String DATABASE_URL =
    "jdbc:sqlserver://localhost;databaseName=estoque;encrypt=false";

    public static Connection createConnectionToSQLServer() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection con = createConnectionToSQLServer()) {
            System.out.println("Conexão obtida com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}