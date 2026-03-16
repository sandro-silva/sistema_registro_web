<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard - Sistema MVC</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f4f6f8;
        margin: 0;
        padding: 0;
    }

    header {
        background: #007bff;
        color: white;
        padding: 15px;
        text-align: center;
    }

    .container {
        width: 90%;
        max-width: 900px;
        margin: 40px auto;
    }

    h2 {
        text-align: center;
        margin-bottom: 30px;
    }

    .cards {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;
    }

    .card {
        background: white;
        padding: 25px;
        border-radius: 8px;
        box-shadow: 0 0 8px #ccc;
        text-align: center;
    }

    .card h3 {
        margin-bottom: 10px;
    }

    .card p {
        color: #555;
        margin-bottom: 15px;
    }

    .card a {
        display: inline-block;
        background: #007bff;
        color: white;
        padding: 8px 15px;
        border-radius: 5px;
        text-decoration: none;
    }

    .card a:hover {
        background: #0056b3;
    }

    footer {
        margin-top: 40px;
        text-align: center;
        color: #777;
        font-size: 14px;
    }
</style>
</head>

<body>

<header>
    <h1>📊 Sistema de Usuários</h1>
</header>

<div class="container">

    <h2>Dashboard</h2>

    <div class="cards">

        <!-- Cadastrar -->
        <div class="card">
            <h3>➕ Novo Cadastro</h3>
            <p>Cadastrar um novo usuário no sistema</p>
            <a href="form.jsp">Cadastrar</a>
        </div>

        <!-- Listar -->
        <div class="card">
            <h3>📋 Lista de Usuários</h3>
            <p>Visualizar, editar ou excluir registros</p>
            <a href="usuario">Listar</a>
        </div>

        <!-- Relatórios (futuro) -->
        <div class="card">
            <h3>📈 Relatórios</h3>
            <p>Estatísticas do sistema</p>
    		<a href="${pageContext.request.contextPath}/relatorios">
        		Abrir
        	</a>
        </div>

        <!-- Sobre -->
        <div class="card">
            <h3>ℹ️ Sobre</h3>
            <p>Informações do sistema</p>
            <a href="#">Detalhes</a>
        </div>
        
        <!-- TOTAL DE USUÁRIOS -->
		<div class="card">
		    <h3>👥 Total de Usuários</h3>
		
		    <p style="font-size:28px; font-weight:bold; color:#007bff;">
		        <%= request.getAttribute("totalUsuarios") != null
		              ? request.getAttribute("totalUsuarios")
		              : "0" %>
		    </p>
		
		    <p>Cadastrados no sistema</p>
		
		    <a href="usuario">Ver lista</a>
		</div>

    </div>

</div>

<footer>
    Sistema MVC Java Web - Desenvolvido por Sandro
</footer>

</body>
</html>

