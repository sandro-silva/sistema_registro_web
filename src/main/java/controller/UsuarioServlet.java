package controller;

import dao.UsuarioDAO;
import model.Usuario;
import model.UsuarioSistema;
import model.Perfil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UsuarioDAO dao = new UsuarioDAO();

    // ======================
    // GET → LISTAR / BUSCAR / EDITAR / DELETAR
    // ======================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        try {

            HttpSession session = req.getSession(false);

            if (session == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            UsuarioSistema usuarioLogado =
                    (UsuarioSistema) session.getAttribute("usuarioLogado");

            Long usuarioSistemaId = usuarioLogado.getId();
            Perfil perfil = usuarioLogado.getPerfil();

            String acao = req.getParameter("acao");

            /* ======================
               DELETAR (SEGURO)
            ====================== */
            if ("deletar".equals(acao)) {

                Long id = Long.parseLong(req.getParameter("id"));

                Usuario usuario;

                // ADMIN pode deletar qualquer um
                if (perfil == Perfil.ADMIN) {
                    usuario = dao.buscarPorId(id);
                } else {
                    usuario = dao.buscarPorIdEUsuarioSistema(
                            id, usuarioSistemaId);
                }

                if (usuario == null) {
                    resp.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Acesso negado");
                    return;
                }

                dao.deletar(id);

                resp.sendRedirect("usuario");
                return;
            }

            /* ======================
               EDITAR (SEGURO)
            ====================== */
            if ("editar".equals(acao)) {

                Long id = Long.parseLong(req.getParameter("id"));

                Usuario usuario;

                if (perfil == Perfil.ADMIN) {
                    usuario = dao.buscarPorId(id);
                } else {
                    usuario = dao.buscarPorIdEUsuarioSistema(
                            id, usuarioSistemaId);
                }

                if (usuario == null) {
                    resp.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Acesso negado");
                    return;
                }

                req.setAttribute("usuario", usuario);

                req.getRequestDispatcher("form.jsp")
                        .forward(req, resp);
                return;
            }

            /* ======================
               LISTAR / BUSCAR
            ====================== */

            String busca = req.getParameter("busca");
            List<Usuario> lista;

            // ===== ADMIN =====
            if (perfil == Perfil.ADMIN) {

                if (busca != null && !busca.trim().isEmpty()) {
                    lista = dao.buscarTodosPorNome(busca);
                } else {
                    lista = dao.listarTodos();
                }

            }
            // ===== USUÁRIO NORMAL =====
            else {

                if (busca != null && !busca.trim().isEmpty()) {
                    lista = dao.buscarPorNome(busca, usuarioSistemaId);
                } else {
                    lista = dao.listarPorUsuarioSistema(usuarioSistemaId);
                }
            }

            req.setAttribute("listaUsuarios", lista);

            req.getRequestDispatcher("listar.jsp")
                    .forward(req, resp);

        } catch (Exception e) {

            e.printStackTrace();

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro no sistema");
        }
    }

    // ======================
    // POST → SALVAR / ATUALIZAR
    // ======================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");

        try {

            HttpSession session = req.getSession(false);

            if (session == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            UsuarioSistema usuarioLogado =
                    (UsuarioSistema) session.getAttribute("usuarioLogado");

            Perfil perfil = usuarioLogado.getPerfil();

            Usuario usuario = new Usuario();

            String id = req.getParameter("id");

            if (id != null && !id.isEmpty()) {
                usuario.setId(Long.parseLong(id));
            }

            usuario.setNome(req.getParameter("nome"));
            usuario.setCpf(req.getParameter("cpf"));
            usuario.setParticipou(
                    req.getParameter("participou") != null);
            usuario.setObservacao(req.getParameter("obs"));

            usuario.setUsuarioSistemaId(usuarioLogado.getId());

            // ===============================
            // UPDATE SEGURO
            // ===============================
            if (usuario.getId() != null) {

                Usuario existente;

                if (perfil == Perfil.ADMIN) {
                    existente = dao.buscarPorId(usuario.getId());
                } else {
                    existente = dao.buscarPorIdEUsuarioSistema(
                            usuario.getId(),
                            usuarioLogado.getId());
                }

                if (existente == null) {
                    resp.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Acesso negado");
                    return;
                }

                dao.atualizar(usuario);

            } else {

                dao.salvar(usuario);
            }

            resp.sendRedirect("usuario");

        } catch (Exception e) {

            e.printStackTrace();

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao salvar usuário");
        }
    }
}