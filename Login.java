package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexaoSql.Conexao;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

   
        boolean validCredentials = checkCredentials(username, password);

        if (validCredentials) {
           
            int userId = getUserIdByUsername(username);
            
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userId);

     
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } else {
   
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }

    private boolean checkCredentials(String username, String password) {
        boolean valid = false;
        Connection connection = null;
        try {
            connection = Conexao.obterConexao();
            String sql = "SELECT COUNT(*) FROM Usuarios WHERE nome = ? AND senha = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                valid = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return valid;
    }

    private int getUserIdByUsername(String username) {
        int userId = 0;
        Connection connection = null;
        try {
            connection = Conexao.obterConexao();
            String sql = "SELECT id FROM Usuarios WHERE nome = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userId;
    }
}
