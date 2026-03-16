<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Usuario" %>

<%
    Usuario u = (Usuario) request.getAttribute("usuario");

    boolean editando = (u != null);
%>

<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= editando ? "Editar Usuário" : "Cadastrar Usuário" %></title>

<style>
    body {
        font-family: Arial;
        background: #f4f6f8;
    }

    .box {
        width: 400px;
        margin: 50px auto;
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px #ccc;
    }
    
    label {
    	display: block;
	}

    input:not([type="checkbox"]), textarea {
    	width: 100%;
    	margin: 5px 0 15px;
    	padding: 6px;
	}

	input[type="checkbox"] {
    	width: auto;
    	margin: 5px 0 15px;
	}

    button {
        background: #007bff;
        color: white;
        border: none;
        padding: 8px 15px;
        cursor: pointer;
    }

    button:hover {
        background: #0056b3;
    }

    a {
        display: block;
        margin-top: 15px;
        text-align: center; 
    
}
    
</style>
</head>

<body>

<div class="box">

<h2>
    <%= editando ? "✏️ Editar Usuário" : "Cadastrar Usuário" %>
</h2>

<form action="usuario" method="post">

    <!-- ID escondido (só na edição) -->
    <input type="hidden" name="id"
           value="<%= editando ? u.getId() : "" %>">

    <label>Nome:</label>
    <input type="text" name="nome" required
           value="<%= editando ? u.getNome() : "" %>">


    <label>CPF:</label>
    <input type="text" name="cpf" required
           value="<%= editando ? u.getCpf() : "" %>">

  	<label>
    <input type="checkbox" name="participou"
        <%= (editando && u.isParticipou()) ? "checked" : "" %>>
    	Participou
	</label>

	<label>Observação:</label>
	<textarea name="obs"><%= editando ? u.getObservacao() : "" %></textarea>

    <button type="submit">
        <%= editando ? "Atualizar" : "Salvar" %>
    </button>

</form>

<a href="usuario">⬅ Voltar para lista</a>

</div>

</body>
</html>
