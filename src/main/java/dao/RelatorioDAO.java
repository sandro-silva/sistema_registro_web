package dao;

import util.Conexao;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RelatorioDAO {

    public Map<String, Integer> totalUsuariosPorPerfil() {

        Map<String, Integer> dados = new LinkedHashMap<>();

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
                dados.put(
                    rs.getString("perfil"),
                    rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro relatório por perfil", e);
        }

        return dados;
    }
}