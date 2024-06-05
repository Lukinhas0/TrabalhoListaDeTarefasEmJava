package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexaoSql.Conexao;

@WebServlet("/atualizarTarefa")
public class AtualizarTarefa extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tarefaId = request.getParameter("tarefaId");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");

        if (tarefaId != null && nome != null && descricao != null) {
            try {
                Connection connection = Conexao.obterConexao();

                String sql = "UPDATE Tarefas SET nome = ?, descricao = ? WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, descricao);
                preparedStatement.setInt(3, Integer.parseInt(tarefaId));

                preparedStatement.executeUpdate();

                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("home.jsp");
    }
}
