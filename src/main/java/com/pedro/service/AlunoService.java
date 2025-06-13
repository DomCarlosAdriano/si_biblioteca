package com.pedro.service;

import com.pedro.dao.AlunoDAO;
import com.pedro.models.Aluno;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {

    private AlunoDAO alunoDAO;

    public AlunoService() {
        alunoDAO = new AlunoDAO();
    }

    public Aluno consultarAluno(int id) {
        Aluno aluno = alunoDAO.consultarAluno(id);
        if (aluno instanceof Aluno) return aluno;
        System.err.println("[!] Nao foi possivel consultar aluno");
        return null;
    }

    public Aluno consultarAluno(String login) {
        Aluno aluno = alunoDAO.consultarAluno(login);
        if (aluno instanceof Aluno) return aluno;
        System.err.println("[!] Nao foi possivel consultar aluno");
        return null;
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> alunosList = null;
        try {
            ResultSet alunos = alunoDAO.listarAlunos();
            alunosList = new ArrayList<Aluno>();
            while (alunos.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(alunos.getInt("id"));
                aluno.setCpf(alunos.getString("cpf"));
                aluno.setNome(alunos.getString("nome"));
                aluno.setTelefone(alunos.getString("telefone"));
                aluno.setEmail(alunos.getString("email"));
                aluno.setCurso(alunos.getString("curso"));
                aluno.setPeriodo(alunos.getString("periodo"));
                aluno.setTurno(alunos.getString("turno"));
                aluno.setMatricula(alunos.getString("matricula"));
                aluno.setLogin(alunos.getString("login"));
                aluno.setSenha(alunos.getString("senha"));
                aluno.setEnderecoId(alunos.getInt("endereco_id"));
                alunosList.add(aluno);
            }
            return alunosList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunosList;
    }

    public boolean editarAluno(int id, Aluno aluno) {
        Aluno existente = consultarAluno(id);
        if (existente == null) {
            System.out.println("[!] Aluno com esse ID não existe.");
            return false;
        }
        if (isNullOrEmpty(aluno.getLogin())){
            aluno.setLogin(existente.getLogin());
        }
        if (isNullOrEmpty(aluno.getSenha())) {
            aluno.setSenha(existente.getSenha());
        }
        if (aluno.getEnderecoId() == 0) {
            aluno.setEnderecoId(existente.getEnderecoId());
        }
        boolean valido = validarAluno(aluno);
        if(!valido){
            return false;
        }

        alunoDAO.editarAluno(id, aluno);
        System.out.println("[!] Aluno editado com sucesso");
        return true;
    }

    public boolean registrarAluno (Aluno aluno) {
        boolean valido = validarAluno(aluno);
        if(!valido){
            return false;
        }

        return alunoDAO.cadastrarAluno(aluno);
    }

    public boolean excluirAluno(int id) {
        if (id == 0) {
            return false;
        }
        boolean sucesso = alunoDAO.excluirAluno(id);
        if (!sucesso) {
            System.err.println("[!] Erro ao excluir aluno");
            return false;
        }
        return true;
    }

    private static boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }

    private static boolean validarAluno(Aluno aluno){
        if (isNullOrEmpty(aluno.getNome())) {
            System.err.println("[!] Nome do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getEmail())) {
            System.err.println("[!] E-mail do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getCurso())) {
            System.err.println("[!] Curso do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getPeriodo())) {
            System.err.println("[!] Período do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getTurno())) {
            System.err.println("[!] Turno do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getMatricula())) {
            System.err.println("[!] Matricula do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getCpf())) {
            System.err.println("[!] CPF do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getLogin())) {
            System.err.println("[!] Login do Aluno é obrigatório.");
            return false;
        }
        if (isNullOrEmpty(aluno.getSenha())) {
            System.err.println("[!] Senha do Aluno é obrigatório.");
            return false;
        }
        return true;
    }

    // Método novo para buscar aluno por ID de locação
    public Aluno consultarAlunoPorLocacaoId(int locacaoId) {
        Aluno aluno = alunoDAO.obterAlunoPorIdLocacao(locacaoId);
        if (aluno != null) {
            return aluno;
        }
        System.err.println("[!] Nenhum aluno encontrado para a locação de ID " + locacaoId);
        return null;
    }

}
