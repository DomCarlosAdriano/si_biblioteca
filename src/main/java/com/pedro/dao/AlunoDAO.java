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
                WHERE id = ?
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
            ps.setInt(10, id);
            ps.executeUpdate();
            ps.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarAluno(Aluno aluno) {
        AutenticacaoDAO autenticacaoDAO = new AutenticacaoDAO();
        return autenticacaoDAO.cadastrarUsuario(null, aluno, null);
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
                        rs.getString("email"), rs.getString("curso"),
                        rs.getString("periodo"), rs.getString("turno"),
                        rs.getString("matricula"), rs.getString("login"),
                        rs.getString("senha")
                );
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEnderecoId(rs.getInt("enderecoId"));
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
                        rs.getString("email"), rs.getString("curso"),
                        rs.getString("periodo"), rs.getString("turno"),
                        rs.getString("matricula"), rs.getString("login"),
                        rs.getString("senha")
                );
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEnderecoId(rs.getInt("endereco_id"));
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

    //Retornar dados do aluno que efetuou uma locação.

    public Aluno obterAlunoPorIdLocacao(int locacaoId) {
        String SQL = """
            SELECT al.* 
            FROM operacao op 
            LEFT JOIN aluno al ON op.aluno_id = al.id 
            WHERE op.aluno_id IS NOT NULL AND op.id = ?
        """;

        try {
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setInt(1, locacaoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getString("cpf"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("curso"),
                        rs.getString("periodo"), rs.getString("turno"),
                        rs.getString("matricula"), rs.getString("login"),
                        rs.getString("senha")
                );
                aluno.setTelefone(rs.getString("telefone"));
                aluno.setEnderecoId(rs.getInt("endereco_id"));
                rs.close();
                ps.close();
                return aluno;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
