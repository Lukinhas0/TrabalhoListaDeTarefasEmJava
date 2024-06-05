<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException, conexaoSql.Conexao"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Tarefa</title>
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
    </style>
</head>
<body>
    <div class="container">
        <h1>Editar Tarefa</h1>
        <%
            String tarefaId = request.getParameter("tarefaId");
            String tarefaNome = "";
            String tarefaDescricao = "";

            if (tarefaId != null) {
                try {
                    Connection connection = Conexao.obterConexao();

                    String sql = "SELECT nome, descricao FROM Tarefas WHERE id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, Integer.parseInt(tarefaId));

                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        tarefaNome = resultSet.getString("nome");
                        tarefaDescricao = resultSet.getString("descricao");
                    }

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
        <form method="post" action="atualizarTarefa">
            <input type="hidden" name="tarefaId" value="<%= tarefaId %>">
            <input type="text" name="nome" value="<%= tarefaNome %>" placeholder="Nome da Tarefa">
            <br>
            <input type="text" name="descricao" value="<%= tarefaDescricao %>" placeholder="Descrição da Tarefa">
            <br>
            <input type="submit" value="Atualizar">
        </form>
    </div>
</body>
</html>
