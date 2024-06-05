package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import conexaoSql.Conexao;

@WebServlet("/adicionarTarefa")
public class adicionarTarefa extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String novaTarefa = request.getParameter("novaTarefa");
        String novaDescricao = request.getParameter("novaDescricao");

        HttpSession session = request.getSession();
        int usuarioId = (int) session.getAttribute("userId");

        if (usuarioId == 0) {
            response.sendRedirect("index.html"); 
            return;
        }

        try (Connection connection = Conexao.obterConexao()) {
            String sql = "INSERT INTO Tarefas (usuario_id, nome, descricao) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, usuarioId);
                preparedStatement.setString(2, novaTarefa);
                preparedStatement.setString(3, novaDescricao);
                preparedStatement.executeUpdate();
            }
            
         
            List<String[]> tarefas = new ArrayList<>();
            sql = "SELECT nome, descricao FROM Tarefas WHERE usuario_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, usuarioId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String nomeTarefa = resultSet.getString("nome");
                        String descricaoTarefa = resultSet.getString("descricao");
                        String[] tarefa = {nomeTarefa, descricaoTarefa};
                        tarefas.add(tarefa);
                    }
                }
            }
            
           
            session.setAttribute("tarefas", tarefas);
        } catch (SQLException e) {
            throw new ServletException("Problema na conexão com o banco de dados", e);
        }
            
        response.sendRedirect("home.jsp");
    }
}
