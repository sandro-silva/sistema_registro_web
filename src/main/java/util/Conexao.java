package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    // Ajuste conforme seu banco
    private static final String URL =
            "jdbc:postgresql://localhost:5432/projeto_mvc_web";

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";


    // Bloco estático: carrega o driver uma vez
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver PostgreSQL carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar Driver PostgreSQL", e);
        }
    }


    // Método padrão para obter conexão
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

