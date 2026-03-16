<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UsuarioSistema" %>
<%@ page import="model.Perfil" %>

<%
    UsuarioSistema usuario =
        (UsuarioSistema) session.getAttribute("usuarioLogado");

	Perfil perfil = (Perfil) session.getAttribute("perfil");
%>

<style>
    .top-header {
        background: #0d47a1; /* azul mais escuro */
        color: white;
        padding: 12px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .top-header a {
        color: white;
        text-decoration: none;
        margin-left: 15px;
        font-weight: bold;
    }

    .perfil-badge {
        background: rgba(255,255,255,0.15);
        padding: 5px 12px;
        border-radius: 20px;
        font-size: 13px;
        margin-left: 8px;
    }

    .admin {
        color: #ff1744; /* vermelho mais forte */
        font-weight: bold;
    }

    .usuario {
        color: #00e5ff; /* azul ciano mais vibrante */
        font-weight: bold;
    }
    
    /* ===== Badge estilo Bootstrap ===== */
    .badge {
        padding: 6px 12px;
        border-radius: 50px;
        font-size: 12px;
        font-weight: 600;
        margin-left: 10px;
        letter-spacing: 0.5px;
    }

    .badge-admin {
        background-color: #dc3545; /* Bootstrap danger */
        color: white;
    }

    .badge-user {
        background-color: #198754; /* Bootstrap success */
        color: white;
    }
</style>

<div class="top-header">

    <div>
        <strong>Sistema MVC</strong>
    </div>

    <div>
        <% if (usuario != null) { %>

            <strong><%= usuario.getLogin() %></strong>

           <% if (perfil == Perfil.ADMIN) { %>
                <span class="badge badge-admin">ADMIN</span>
            <% } else { %>
                <span class="badge badge-user">USUÁRIO</span>
            <% } %>

            <a href="logout">Sair</a>

        <% } %>
    </div>

</div>