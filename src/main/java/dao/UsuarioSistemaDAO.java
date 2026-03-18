package dao;

import model.UsuarioSistema;
import model.Perfil;
import util.Conexao;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class UsuarioSistemaDAO {

    /* ================= LOGIN ================= */
    public UsuarioSistema autenticar(String login, String senha) {

        String sql =
            "SELECT us.id, us.login, us.senha, p.nome AS perfil " +
            "FROM usuarios_sistema us " +
            "JOIN perfis p ON p.id = us.perfil_id " +
            "WHERE us.login = ? AND us.senha = ?";

        try (
            Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, login);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                UsuarioSistema u = new UsuarioSistema();

                u.setId(rs.getLong("id"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));

                // ✅ CONVERSÃO STRING → ENUM
                String perfilBanco = rs.getString("perfil");

                u.setPerfil(
                    Perfil.valueOf(perfilBanco.toUpperCase())
                );

                return u;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar", e);
        }

        return null;
    }

    /* ================= RELATÓRIO ================= */
    public Map<String, Integer> contarPorPerfil() {

        Map<String, Integer> mapa = new LinkedHashMap<>();

        String sql =
            "SELECT p.nome AS perfil, COUNT(u.id) AS total " +
            "FROM usuarios u " +
            "JOIN usuarios_sistema us ON u.usuario_sistema_id = us.id " +
            "JOIN perfis p ON us.perfil_id = p.id " +
            "GROUP BY p.nome " +
            "ORDER BY p.nome";

        try (
            Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                mapa.put(
                    rs.getString("perfil"),
                    rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }

        return mapa;
    }
}