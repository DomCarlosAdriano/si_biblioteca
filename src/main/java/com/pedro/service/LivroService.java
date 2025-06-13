package com.pedro.service;

import com.pedro.dao.LivroDAO;
import com.pedro.models.Livro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroService {

    private LivroDAO livroDAO;

    public LivroService() {
        this.livroDAO = new LivroDAO();
    }

    public boolean cadastrarLivro(Livro livro) {
        boolean valido = validarLivro(livro);
        if(!valido){
            return false;
        }
        
        livroDAO.cadastrarLivro(livro);
        return true;
    }

    public boolean editarLivro(int id, Livro livro) {
        if (livro.getId() <= 0) {
            System.err.println("Erro: ID do livro inválido.");
            return false;
        }
        boolean succ = livroDAO.editarLivro(id, livro);
        if(!succ){
            return false;
        }
        return true;
    }

    public boolean removerLivro(int id) {
        if(id <= 0){
            System.err.println("[!] ID Inválido.");
            return false;
        }
        boolean succ = livroDAO.removerLivro(id);
        if(!succ){
            return false;
        }
        return true;
    }

    public List<Livro> listarLivros() {
        List<Livro> lista = new ArrayList<>();
        try {
            ResultSet rs = livroDAO.consultarLivros();
            while (rs != null && rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getInt("editora_id"),
                        rs.getString("edicao"),
                        rs.getString("sinopse"),
                        rs.getInt("genero_id"),
                        rs.getInt("autor_id"));
                livro.setId(rs.getInt("id"));
                lista.add(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Livro buscarLivroPorId(int id){
        if(id <= 0){
            System.err.println("[!] ID Inválido.");
            return null;
        }
        Livro livro = livroDAO.buscarLivroPorId(id);
        return livro;
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean validarLivro(Livro livro){
        if(isNullOrEmpty(livro.getTitulo())){
            System.err.println("[!] O Título do Livro é Obrigatório.");
            return false;
        }

        if(livro.getEditorId() <= 0){
            System.err.println("O ID da Editora é obrigatório.");
            return false;
        }

        if(livro.getGeneroId() <= 0){
            System.err.println("[!] O ID do Gênero é Obrigatório.");
            return false;
        }

        if(livro.getAutorId() <= 0) {
            System.err.println("[!] O ID do Autor é obrigatório.");
        }

        if(isNullOrEmpty(livro.getEdicao())){
            System.err.println("[!] A Edição do Livro é obrigatória");
            return false;
        }

        if(isNullOrEmpty(livro.getSinopse())){
            System.err.println("[!] A Sinopse do Livro é obrigatória.");
        }

        return true;
    }

    // ✅ Método adicionado para contar as locações de um livro
    public int obterQuantidadeDeLocacoesPorLivro(int livroId) {
        int total = livroDAO.contarLocacoesPorLivro(livroId);
        if (total > 0) {
            return total;
        } else {
            System.out.println("[!] Nenhuma locação encontrada para o livro de ID " + livroId);
            return 0;
        }
    }
}
