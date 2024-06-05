<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, conexaoSql.Conexao"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #2980b9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            width: 400px;
            background-color: #ecf0f1;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        h1 {
            margin-top: 0;
            color: #2c3e50;
        }

        form {
            margin-bottom: 20px;
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #3498db;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #2980b9;
        }

        ul {
            list-style-type: none;
            padding: 0;
        }

        li {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        strong {
            color: #2c3e50;
        }

        .excluir-btn, .editar-btn {
            background-color: #c0392b;
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }

        .editar-btn {
            background-color: #f39c12;
        }

        .editar-btn:hover {
            background-color: #e67e22;
        }

        .excluir-btn:hover {
            background-color: #e74c3c;
        }

        .excluir-usuario-btn {
            background-color: #e74c3c;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
        }

        .excluir-usuario-btn:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lista de Tarefas</h1>

        <form method="post" action="adicionarTarefa">
            <input type="text" name="novaTarefa" placeholder="Nova Tarefa">
            <br>
            <input type="text" name="novaDescricao" placeholder="Descrição">
            <br>
            <input type="submit" value="Adicionar">
        </form>

        <ul>
            <%
                Integer userIdLogado = (Integer) session.getAttribute("userId");

                if (userIdLogado != null) {
                    try {
                        Connection connection = Conexao.obterConexao();

                        String sql = "SELECT id, nome, descricao FROM Tarefas WHERE usuario_id = ?";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, userIdLogado);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            int tarefaId = resultSet.getInt("id");
                            String tarefaNome = resultSet.getString("nome");
                            String tarefaDescricao = resultSet.getString("descricao");
            %>
            <li>
                <strong>Tarefa:</strong> <%= (tarefaNome != null && !tarefaNome.isEmpty()) ? tarefaNome : "Título não especificado" %><br>
                <strong>Descrição:</strong> <%= (tarefaDescricao != null && !tarefaDescricao.isEmpty()) ? tarefaDescricao : "Descrição não especificada" %>
                <form method="post" action="excluirTarefa" style="display:inline;">
                    <input type="hidden" name="tarefaId" value="<%= tarefaId %>">
                    <input type="submit" class="excluir-btn" value="Excluir">
                </form>
                <form method="get" action="editarTarefa.jsp" style="display:inline;">
                    <input type="hidden" name="tarefaId" value="<%= tarefaId %>">
                    <input type="submit" class="editar-btn" value="Editar">
                </form>
            </li>
            <%
                        }

                        resultSet.close();
                        preparedStatement.close();
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            %>
            <form method="post" action="excluirUsuario">
                <input type="hidden" name="userId" value="<%= userIdLogado %>">
                <input type="submit" class="excluir-usuario-btn" value="Excluir Conta">
            </form>
            <%
                } else {
            %>
            <li>Nenhum usuário logado.</li>
            <%
                }
            %>
        </ul>
    </div>
</body>
</html>
