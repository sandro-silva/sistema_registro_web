package controller;

import dao.UsuarioSistemaDAO;
import model.UsuarioSistema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UsuarioSistemaDAO dao = new UsuarioSistemaDAO();

    // ======================
    // EXIBIR LOGIN
    // ======================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("login.jsp")
           .forward(req, resp);
    }

    // ======================
    // AUTENTICAR USUÁRIO
    // ======================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String login = req.getParameter("login");
        String senha = req.getParameter("senha");

        UsuarioSistema usuario = dao.autenticar(login, senha);

        if (usuario != null) {

            // 🔐 Invalida sessão antiga (segurança)
            HttpSession sessionAntiga = req.getSession(false);
            if (sessionAntiga != null) {
                sessionAntiga.invalidate();
            }

            // 🆕 Cria nova sessão
            HttpSession session = req.getSession(true);

            // 👤 Salva objeto completo do usuário
            session.setAttribute("usuarioLogado", usuario);

            // 🔑 Salva perfil separadamente (IMPORTANTE)
            session.setAttribute("perfil", usuario.getPerfil());

            // ⏳ Expiração da sessão (30 minutos)
            session.setMaxInactiveInterval(30 * 60);

            // 🚀 Redireciona para dashboard
            resp.sendRedirect("dashboard");

        } else {

            // ❌ Login inválido
            resp.sendRedirect("login?erro=true");
        }
    }
}