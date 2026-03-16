<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Usuario" %>
<%@ page import="model.Perfil" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Usuários</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f6f8;
    }

    .topo {
        width: 90%;
        margin: 20px auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    table {
        border-collapse: collapse;
        width: 90%;
        margin: auto;
        background: white;
    }

    th, td {
        border: 1px solid #ccc;
        padding: 8px;
        text-align: center;
    }

    th {
        background: #f2f2f2;
    }

    h2 {
        text-align: center;
    }

    a {
        text-decoration: none;
        padding: 6px 10px;
        background: #007bff;
        color: white;
        border-radius: 4px;
    }

    a:hover {
        background: #0056b3;
    }

    .buscar input {
        padding: 6px;
        width: 220px;
    }

    .buscar button {
        padding: 6px 10px;
    }

    .info-busca {
        width: 90%;
        margin: 15px auto;
        padding: 12px;
        background: #e9f5ff;
        border-left: 5px solid #007bff;
        font-size: 15px;
        border-radius: 4px;
    }

    .sem-permissao {
        color: #999;
        font-size: 14px;
    }
</style>
</head>

<body>

<h2>📋 Lista de Usuários</h2>

<div class="topo">

    <div class="buscar">
        <form action="usuario" method="get">

            <input type="text"
                   name="busca"
                   placeholder="Buscar por nome..."
                   value="<%= request.getAttribute("busca") != null
                           ? request.getAttribute("busca")
                           : "" %>">

            <button type="submit">🔍 Buscar</button>

            <a href="usuario" style="margin-left:10px;">Limpar</a>

        </form>
    </div>

    <div>
        <a href="dashboard">⬅ Dashboard</a>
    </div>

</div>

<%
    String busca = (String) request.getAttribute("busca");
    if (busca != null && !busca.isEmpty()) {
%>
    <div class="info-busca">
        🔎 Exibindo resultados para:
        <strong><%= busca %></strong>
    </div>
<%
    }
%>

<table>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>CPF</th>
        <th>Participou</th>
        <th>Observação</th>
        <th>Ações</th>
    </tr>

<%
    List<Usuario> lista =
        (List<Usuario>) request.getAttribute("listaUsuarios");

    if (lista != null && !lista.isEmpty()) {

        for (Usuario u : lista) {
%>

    <tr>
        <td><%= u.getId() %></td>
        <td><%= u.getNome() %></td>
        <td><%= u.getCpf() %></td>
        <td><%= u.isParticipou() ? "SIM" : "NÃO" %></td>
        <td><%= u.getObservacao() %></td>

        <td>
            <% if (perfil == Perfil.ADMIN) { %>

                <a href="usuario?acao=editar&id=<%=u.getId()%>">Editar</a>
                |
                <a href="usuario?acao=deletar&id=<%=u.getId()%>"
                   onclick="return confirm('Deseja excluir?')">
                   Excluir
                </a>

            <% } else { %>

                <span class="sem-permissao">🔒 Sem permissão</span>

            <% } %>
        </td>
    </tr>

<%
        }

    } else {
%>

    <tr>
        <td colspan="6">Nenhum usuário encontrado.</td>
    </tr>

<%
    }
%>

</table>

</body>
</html>