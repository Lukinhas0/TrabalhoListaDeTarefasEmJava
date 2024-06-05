package conexaoSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlserver://192.168.11.134;databaseName=100002720222;encrypt=false";
    private static final String USER = "prof";
    private static final String PASSWORD = "senha123";

    public static Connection obterConexao() {
        Connection conexao = null;    
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao obter conexão com o banco de dados");
            e.printStackTrace();
        }
        return conexao;
    }
}
