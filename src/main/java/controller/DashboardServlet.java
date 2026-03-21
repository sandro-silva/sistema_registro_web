package controller;

import dao.UsuarioDAO;
import model.UsuarioSistema;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            HttpSession session = req.getSession(false);

            if (session == null ||
                session.getAttribute("usuarioLogado") == null) {

                resp.sendRedirect("login.jsp");
                return;
            }

            UsuarioSistema usuarioLogado =
                    (UsuarioSistema) session.getAttribute("usuarioLogado");

            // =============================
            // TOTAL DE USUÁRIOS
            // =============================
            int totalUsuarios = dao.contarUsuarios();

            req.setAttribute("usuarioLogado", usuarioLogado);
            req.setAttribute("totalUsuarios", totalUsuarios);

            req.getRequestDispatcher("index.jsp")
                    .forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace();

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro no dashboard");
        }
    }
}