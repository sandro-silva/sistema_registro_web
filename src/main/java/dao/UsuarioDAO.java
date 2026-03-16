package dao;

import model.Usuario;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    /* ================= CREATE ================= */
    public void salvar(Usuario u) {

        String sql = "INSERT INTO usuarios " +
                "(nome, cpf, participou, observacao, usuario_sistema_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setBoolean(3, u.isParticipou());
            ps.setString(4, u.getObservacao());
            ps.setLong(5, u.getUsuarioSistemaId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    /* =====================================================
       LISTAR TODOS (ADMIN)
       ===================================================== */
    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios ORDER BY id";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setParticipou(rs.getBoolean("participou"));
                u.setObservacao(rs.getString("observacao"));
                u.setUsuarioSistemaId(rs.getLong("usuario_sistema_id"));

                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos usuários", e);
        }

        return lista;
    }

    /* =====================================================
       LISTAR SOMENTE DO USUÁRIO LOGADO
       ===================================================== */
    public List<Usuario> listarPorUsuarioSistema(Long usuarioSistemaId) {

        List<Usuario> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM usuarios " +
                "WHERE usuario_sistema_id = ? " +
                "ORDER BY id";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, usuarioSistemaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setParticipou(rs.getBoolean("participou"));
                u.setObservacao(rs.getString("observacao"));
                u.setUsuarioSistemaId(rs.getLong("usuario_sistema_id"));

                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }

        return lista;
    }
    
    /* ================= BUSCAR TODOS (ADMIN) ================= */
    public List<Usuario> buscarTodosPorNome(String nome) {

        List<Usuario> lista = new ArrayList<>();

        String sql =
            "SELECT * FROM usuarios " +
            "WHERE nome ILIKE ? " +
            "ORDER BY id";

        try (
            Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, "%" + nome + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setParticipou(rs.getBoolean("participou"));
                u.setObservacao(rs.getString("observacao"));
                u.setUsuarioSistemaId(
                    rs.getLong("usuario_sistema_id"));

                lista.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuários", e);
        }

        return lista;
    }

    /* ================= BUSCAR POR NOME ================= */
    public List<Usuario> buscarPorNome(String nome, Long usuarioSistemaId) {

        List<Usuario> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM usuarios " +
                "WHERE nome ILIKE ? " +
                "AND usuario_sistema_id = ? " +
                "ORDER BY id";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, "%" + nome + "%");
            ps.setLong(2, usuarioSistemaId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setParticipou(rs.getBoolean("participou"));
                u.setObservacao(rs.getString("observacao"));
                u.setUsuarioSistemaId(rs.getLong("usuario_sistema_id"));

                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuários", e);
        }

        return lista;
    }

    /* ================= BUSCAR POR ID ================= */
    public Usuario buscarPorId(Long id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setParticipou(rs.getBoolean("participou"));
                usuario.setObservacao(rs.getString("observacao"));
                usuario.setUsuarioSistemaId(
                        rs.getLong("usuario_sistema_id"));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return null;
    }

    /* ================= BUSCAR POR ID USUARIO SISTEMA ================= Esse método garante ownership.*/
    public Usuario buscarPorIdEUsuarioSistema(Long id, Long usuarioSistemaId)
            throws Exception {

        String sql =
            "SELECT * FROM usuario " +
            "WHERE id = ? AND usuario_sistema_id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.setLong(2, usuarioSistemaId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setParticipou(rs.getBoolean("participou"));
                u.setObservacao(rs.getString("observacao"));
                u.setUsuarioSistemaId(
                        rs.getLong("usuario_sistema_id"));

                return u;
            }
        }

        return null;
    }
    
    /* ================= UPDATE ================= */
    public void atualizar(Usuario u) {

        String sql =
                "UPDATE usuarios SET " +
                        "nome = ?, cpf = ?, participou = ?, observacao = ? " +
                        "WHERE id = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCpf());
            ps.setBoolean(3, u.isParticipou());
            ps.setString(4, u.getObservacao());
            ps.setLong(5, u.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    /* ================= DELETE ================= */
    public void deletar(Long id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    /* ================= CONTAR (POR USUÁRIO) ================= */
    public int contarUsuarios(Long usuarioSistemaId) {

        String sql =
                "SELECT COUNT(*) FROM usuarios " +
                "WHERE usuario_sistema_id = ?";

        try (
                Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setLong(1, usuarioSistemaId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar usuários", e);
        }

        return 0;
    }

    /* ================= CONTAR TODOS (ADMIN) ================= */
    public int contarTodosUsuarios() {

        String sql = "SELECT COUNT(*) FROM usuarios";

        try (
            Connection conn = Conexao.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao contar usuários", e);
        }

        return 0;
    }
}