<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<style>
    body {
        font-family: Arial;
        background: #f4f6f8;
    }

    .box {
        width: 300px;
        margin: 100px auto;
        background: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 0 10px #ccc;
    }

    input {
        width: 100%;
        margin: 10px 0;
        padding: 8px;
    }

    button {
        width: 100%;
        padding: 8px;
        background: #007bff;
        color: white;
        border: none;
    }

    .erro {
        color: red;
        text-align: center;
    }
</style>
</head>
<body>

<div class="box">

<h2>🔐 Login</h2>

<%
    if ("true".equals(request.getParameter("erro"))) {
%>
    <div class="erro">Login ou senha inválidos!</div>
<%
    }
%>

<form action="login" method="post">
    <input type="text" name="login" placeholder="Usuário" required>
    <input type="password" name="senha" placeholder="Senha" required>
    <button type="submit">Entrar</button>
</form>

</div>

</body>
</html>
