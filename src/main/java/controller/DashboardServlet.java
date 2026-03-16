package controller;

import dao.UsuarioDAO;
import model.UsuarioSistema;
import model.Perfil;

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

            if (session == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            UsuarioSistema usuarioLogado =
                    (UsuarioSistema) session.getAttribute("usuarioLogado");

            int totalUsuarios;

            // ⭐ ADMIN vê todos
            if (usuarioLogado.getPerfil() == Perfil.ADMIN) {
                totalUsuarios = dao.contarTodosUsuarios();
            }
            // ⭐ usuário comum vê apenas os dele
            else {
                totalUsuarios =
                        dao.contarUsuarios(usuarioLogado.getId());
            }

            req.setAttribute("totalUsuarios", totalUsuarios);

            req.getRequestDispatcher("index.jsp")
               .forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace();

            resp.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro no dashboard"
            );
        }
    }
}