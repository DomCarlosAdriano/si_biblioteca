package com.pedro.dao;

import com.pedro.config.Conexao;
import com.pedro.models.Endereco;
import com.pedro.models.Livro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

public class LivroDAO {

    private Conexao conexao;
    private PreparedStatement ps;

    public LivroDAO() {
        conexao = new Conexao();
    }

    public void cadastrarLivro(Livro livro) {
        String SQL = "INSERT INTO livro (titulo, editor_id, edicao, sinopse, genero_id, autor_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            ps = conexao.getConn().prepareStatement(SQL);

            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getEditorId());
            ps.setString(3, livro.getEdicao());
            ps.setString(4, livro.getSinopse());
            ps.setInt(5, livro.getGeneroId());
            ps.setInt(6, livro.getAutorId());

            ps.executeUpdate();
            ps.close();

            System.out.println("Livro inserido com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro ao inserir livro.");
        }
    }

    public boolean removerLivro(int id) {
        String SQL = "DELETE FROM livro WHERE id = ?";

        try {
            ps = conexao.getConn().prepareStatement(SQL);

            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ResultSet consultarLivros() {
        try {
            return conexao.getConn()
                    .createStatement()
                    .executeQuery("SELECT * FROM livro");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean editarLivro(int id, Livro livro) {
        try {
            String SQL = "UPDATE livro SET " +
                    "titulo = ?, editorId = ?, edicao = ?, sinopse = ?, generoId = ?, autorId = ? " +
                    "WHERE id = ?";

            ps = conexao.getConn().prepareStatement(SQL);

            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getEditorId());
            ps.setString(3, livro.getEdicao());
            ps.setString(4, livro.getSinopse());
            ps.setInt(5, livro.getGeneroId());
            ps.setInt(6, livro.getAutorId());
            ps.setInt(7, livro.getId());

            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Livro buscarLivroPorId(int id){
        try {
            ps = conexao.getConn().prepareStatement(
                "SELECT * FROM livro WHERE id = ?"
            );

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Livro livro = new Livro(
                    rs.getString("titulo"), 
                    rs.getInt("editora_id"),
                    rs.getString("edicao"), 
                    rs.getString("sinopse"), 
                    rs.getInt("genero_id"), 
                    rs.getInt("autor_id"));
                livro.setId(id);
                
                return livro;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    // ✅ Método adicionado: conta quantas vezes o livro foi locado
    public int contarLocacoesPorLivro(int livroId) {
        String SQL = """
            SELECT COUNT(*) AS total
            FROM livro l
                     LEFT JOIN exemplar e ON e.livro_id = l.id
                     LEFT JOIN operacao op ON op.exemplar_id = e.id
            WHERE op.tipo_operacao = 'LOCACAO' AND l.id = ?
        """;

        try {
            ps = conexao.getConn().prepareStatement(SQL);
            ps.setInt(1, livroId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                rs.close();
                ps.close();
                return total;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
