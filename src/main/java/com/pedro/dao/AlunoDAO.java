package com.pedro.dao;

import com.pedro.config.Conexao;
import com.pedro.models.Aluno;

import javax.xml.transform.Result;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

    private Conexao conexao;
    private PreparedStatement ps;

    public AlunoDAO() {
        conexao = new Conexao();
    }

    public boolean editarAluno(int id, Aluno aluno) {
        try {
            String SQL = """
            UPDATE aluno SET 
                cpf = ?, nome = ?, telefone = ?, email = ?,
                endereco_id = ?, curso = ?, periodo = ?,
                turno = ?, matricula = ?
            """;
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getTelefone());
            ps.setString(4, aluno.getEmail());
            if (aluno.getEnderecoId() != 0) {
                ps.setInt(5, aluno.getEnderecoId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setString(6, aluno.getCurso());
            ps.setString(7, aluno.getPeriodo());
            ps.setString(8, aluno.getTurno());
            ps.setString(9, aluno.getMatricula());
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void cadastrarAluno(Aluno aluno) {
        AutenticacaoDAO autenticacaoDAO = new AutenticacaoDAO();
        boolean sucesso = autenticacaoDAO.cadastrarUsuario(null, aluno, null);

    }

    public ResultSet listarAlunos() {
        try {
            return conexao.getConn().createStatement().executeQuery("SELECT * FROM aluno;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Aluno consultarAluno(String login) {
        try {
            String SQL = "SELECT * FROM aluno WHERE login = ?";
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            Aluno aluno = null;
            if (rs.next()) {
                aluno = new Aluno(
                        rs.getString("cpf"), rs.getString("nome"),
                        rs.getString("telefone"), rs.getString("email"),
                        rs.getInt("enderecoId"), rs.getString("curso"),
                        rs.getString("periodo"), rs.getString("turno"),
                        rs.getString("matricula"), rs.getString("login"),
                        rs.getString("senha")
                );
                return aluno;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public Aluno consultarAluno(int id) {
        try {
            String SQL = "SELECT * FROM aluno WHERE id = ?";
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Aluno aluno = null;
            if (rs.next()) {
                aluno = new Aluno(
                        rs.getString("cpf"), rs.getString("nome"),
                        rs.getString("telefone"), rs.getString("email"),
                        rs.getInt("enderecoId"), rs.getString("curso"),
                        rs.getString("periodo"), rs.getString("turno"),
                        rs.getString("matricula"), rs.getString("login"),
                        rs.getString("senha")
                );
                return aluno;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public boolean excluirAluno(int id) {
        try {
            ps = conexao.getConn().prepareStatement(
                    "DELETE FROM aluno WHERE id = ?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


}
