package filter;

import model.UsuarioSistema;
import model.Perfil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();

        // ===============================
        // 🔓 RECURSOS PÚBLICOS
        // ===============================
        if (uri.endsWith("login") ||
            uri.endsWith("login.jsp") ||
            uri.contains("/css/") ||
            uri.contains("/js/") ||
            uri.contains("/images/") ||
            uri.contains("favicon")) {

            chain.doFilter(request, response);
            return;
        }

        // ===============================
        // 🔐 VERIFICA LOGIN
        // ===============================
        HttpSession session = req.getSession(false);

        if (session == null ||
            session.getAttribute("usuarioLogado") == null) {

            resp.sendRedirect(contextPath + "/login");
            return;
        }

        // ===============================
        // 🔐 OBTÉM USUÁRIO LOGADO
        // ===============================
        UsuarioSistema usuarioLogado =
                (UsuarioSistema) session.getAttribute("usuarioLogado");

        Perfil perfil = usuarioLogado.getPerfil();

        // ===============================
        // 🔒 ÁREA ADMIN
        // ===============================
        if (uri.contains("/admin/")
                && perfil != Perfil.ADMIN) {

            resp.sendRedirect(contextPath + "/dashboard");
            return;
        }

        // ===============================
        // 🔒 RELATÓRIOS (somente ADMIN)
        // ===============================
        if (uri.contains("/relatorios")
                && perfil != Perfil.ADMIN) {

            resp.sendRedirect(contextPath + "/dashboard");
            return;
        }

        // ===============================
        // ✅ LIBERA ACESSO
        // ===============================
        chain.doFilter(request, response);
    }
}