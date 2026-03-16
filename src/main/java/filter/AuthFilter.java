package filter;

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

        String uri = req.getRequestURI();

        // 🔓 Páginas públicas
        if (uri.contains("login") ||
            uri.contains("css") ||
            uri.contains("js")) {

            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null ||
            session.getAttribute("usuarioLogado") == null) {

            resp.sendRedirect("login");
            return;
        }

        Perfil perfil =
            (Perfil) session.getAttribute("perfil");

        // 🔒 Apenas ADMIN pode acessar /usuarios
        if (uri.contains("/usuario") &&
            perfil != Perfil.ADMIN) {

            resp.sendRedirect("dashboard");
            return;
        }
        
        // 🔒 Apenas ADMIN pode acessar /relatorios
        if (uri.contains("/relatorios") &&
            perfil != Perfil.ADMIN) {

            resp.sendRedirect("dashboard");
            return;
        }

        chain.doFilter(request, response);
    }
}