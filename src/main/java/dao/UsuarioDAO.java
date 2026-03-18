package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import util.Conexao;

public class UsuarioDAO {

    // =============================
    // INSERT
    // =============================
    public void salvar(Usuario usuario) {

        String sql = "INSERT INTO usuarios "
                + "(nome, cpf, atividade, observacao) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getAtividade());
            ps.setString(4, usuario.getObservacao());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuário", e);
        }
    }

    // =============================
    // LISTAR TODOS
    // =============================
    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios ORDER BY nome";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();

                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setCpf(rs.getString("cpf"));
                u.setAtividade(rs.getString("atividade"));
                u.setObservacao(rs.getString("observacao"));

                lista.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }

        return lista;
    }

    // =============================
    // BUSCAR POR ID
    // =============================
    public Usuario buscarPorId(Long id) {

        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setAtividade(rs.getString("atividade"));
                usuario.setObservacao(rs.getString("observacao"));

                return usuario;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }

        return null;
    }

    // =============================
    // ATUALIZAR
    // =============================
    public void atualizar(Usuario usuario) {

        String sql = "UPDATE usuarios SET "
                + "nome = ?, "
                + "cpf = ?, "
                + "atividade = ?, "
                + "observacao = ? "
                + "WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getAtividade());
            ps.setString(4, usuario.getObservacao());
            ps.setLong(5, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    // =============================
    // EXCLUIR
    // =============================
    public void excluir(Long id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }
	 // =============================
	 // CONTAR USUÁRIOS
	 // =============================
	 public int contarUsuarios() {
	
	     String sql = "SELECT COUNT(*) FROM usuarios";
	
	     try (Connection conn = Conexao.getConnection();
	          PreparedStatement ps = conn.prepareStatement(sql);
	          ResultSet rs = ps.executeQuery()) {
	
	         if (rs.next()) {
	             return rs.getInt(1);
	         }
	
	     } catch (SQLException e) {
	         throw new RuntimeException("Erro ao contar usuários", e);
	     }
	
	     return 0;
	 }
}