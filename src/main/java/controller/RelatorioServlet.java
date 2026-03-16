package controller;

import dao.UsuarioSistemaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/relatorios")
public class RelatorioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioSistemaDAO dao = new UsuarioSistemaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // ✅ Total de usuários cadastrados por perfil
            Map<String, Integer> totalPorPerfil =
                    dao.contarPorPerfil();

            request.setAttribute("totalPorPerfil", totalPorPerfil);

            request.getRequestDispatcher("relatorios.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Erro ao gerar relatório"
            );
        }
    }
}