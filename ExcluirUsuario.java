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
import java.sql.SQLException;
import conexaoSql.Conexao;

@WebServlet("/excluirUsuario")
public class ExcluirUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        try {
            Connection connection = Conexao.obterConexao();

     
            String deleteTarefasSql = "DELETE FROM Tarefas WHERE usuario_id = ?";
            PreparedStatement deleteTarefasStmt = connection.prepareStatement(deleteTarefasSql);
            deleteTarefasStmt.setInt(1, userId);
            deleteTarefasStmt.executeUpdate();
            deleteTarefasStmt.close();

         
            String deleteUsuarioSql = "DELETE FROM Usuarios WHERE id = ?";
            PreparedStatement deleteUsuarioStmt = connection.prepareStatement(deleteUsuarioSql);
            deleteUsuarioStmt.setInt(1, userId);
            deleteUsuarioStmt.executeUpdate();
            deleteUsuarioStmt.close();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

     
        HttpSession session = request.getSession();
        session.invalidate();

    
        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}
