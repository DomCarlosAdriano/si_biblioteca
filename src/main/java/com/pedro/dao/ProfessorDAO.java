package com.pedro.dao;

import com.pedro.config.Conexao;
import com.pedro.models.Aluno;
import com.pedro.models.Funcionario;
import com.pedro.models.Professor;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {

    private Conexao conexao;
    private PreparedStatement ps;
    private Professor professor;

    public ProfessorDAO() {
        conexao = new Conexao();
    }

    public boolean editarProfessor(int id, Professor professor) {
        try {
            String SQL = """
            UPDATE professor SET 
                cpf = ?, nome = ?, telefone = ?, email = ?,
                endereco_id = ?, disciplina = ?, credencial = ?
                WHERE id = ?
                
            """;
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setString(1, professor.getCpf());
            ps.setString(2, professor.getNome());
            ps.setString(3, professor.getTelefone());
            ps.setString(4, professor.getEmail());
            if (professor.getEnderecoId() != 0) {
                ps.setInt(5, professor.getEnderecoId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setString(6, professor.getDisciplina());
            ps.setString(7, professor.getCredencial());
            ps.setInt(8, id);
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarProfessor(Professor professor) {
        AutenticacaoDAO autenticacaoDAO = new AutenticacaoDAO();
        return autenticacaoDAO.cadastrarUsuario(null, null, professor);

    }

    public ResultSet listarProfessores() {
        try {
            return conexao.getConn().createStatement().executeQuery("SELECT * FROM professor;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Professor consultarProfessor(String login) {
        try {
            String SQL = "SELECT * FROM professor WHERE login = ?";
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                professor = new Professor(
                        rs.getString("cpf"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("disciplina"),
                        rs.getString("credencial"), rs.getString("login"),
                        rs.getString("senha")
                );
                professor.setTelefone(rs.getString("telefone"));
                professor.setEnderecoId(rs.getInt("enderecoId"));
                return professor;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Professor consultarProfessor(int id) {
        try {
            String SQL = "SELECT * FROM professor WHERE id = ?";
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                professor = new Professor(
                        rs.getString("cpf"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("disciplina"),
                        rs.getString("credencial"), rs.getString("login"),
                        rs.getString("senha")
                );
                professor.setTelefone(rs.getString("telefone"));
                professor.setEnderecoId(rs.getInt("endereco_id"));
                return professor;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean excluirProfessor(int id) {
        try {
            ps = conexao.getConn().prepareStatement(
                    "DELETE FROM professor WHERE id = ?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Método adicionado: retorna o professor que mais realizou locações
    public String buscarProfessorComMaisLocacoes() {
        String SQL = """
            SELECT p.nome, COUNT(*) AS quantidade_locada
            FROM professor p
                     INNER JOIN operacao op ON op.professor_id = p.id
            WHERE op.tipo_operacao = 'LOCACAO'
            GROUP BY p.nome
            ORDER BY quantidade_locada DESC
            LIMIT 1
        """;

        try {
            ps = conexao.getConn().prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade_locada");
                rs.close();
                ps.close();
                return nome + " - " + quantidade + " locações";
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Nenhuma locação encontrada.";
    }

}
