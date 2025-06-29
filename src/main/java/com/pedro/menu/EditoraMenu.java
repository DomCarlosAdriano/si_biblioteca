package com.pedro.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.pedro.config.IO;
import com.pedro.models.Editora;
import com.pedro.models.Endereco;
import com.pedro.service.EditoraService;
import com.pedro.utils.ColunaUtils;

public class EditoraMenu {

    private Scanner scanner;
    private EditoraService editoraService;
    private EnderecoMenu enderecoMenu;
    private IO io;

    public EditoraMenu() {
        scanner = new Scanner(System.in);
        editoraService = new EditoraService();
        enderecoMenu = new EnderecoMenu();
        io = new IO();
    }

    public void imprimirMenu() {
        List<String> opcoesEditora = new ArrayList<>(Arrays.asList(
                "1. Cadastrar editora",
                "2. Consultar editoras",
                "3. Editar editora",
                "4. Excluir editora",
                "5. Voltar"));
        int opc = io.imprimirMenuRetornandoOpcao(opcoesEditora, "MENU EDITORA");
        while (opc != 5) {
            switch (opc) {
                case 1:
                    cadastrarEditora();
                    break;
                case 2:
                    listarEditoras();
                    break;
                case 3:
                    editarEditora();
                    break;
                case 4:
                    excluirEditora();
                    break;

                default:
                    System.err.println("[!] Opção Inválida.");
            }
            opc = io.imprimirMenuRetornandoOpcao(opcoesEditora, "MENU EDITORA");
        }
    }

    private void cadastrarEditora() {
        System.out.println("[!] Nome: ");
        String nome = scanner.nextLine();
        System.out.println("[!] CNPJ: ");
        String cnpj = scanner.nextLine();
        Endereco endereco = enderecoMenu.selecionarOuCadastrarEndereco();
        Editora editora = new Editora(nome.isEmpty() ? null : nome, cnpj.isEmpty() ? null : cnpj);
        editora.setEnderecoMatrizId(endereco == null ? 0 : endereco.getId());
        editoraService.cadastrarEditora(editora);
    }

    public void listarEditoras() {
        List<Editora> editoras = editoraService.listar();
        System.out.println("--------------------EDITORAS------------------");
        System.out.println(
            "| " + ColunaUtils.formatarColuna("ID", 6) + " | " + ColunaUtils.formatarColuna("Nome", 14) + 
            " | " + ColunaUtils.formatarColuna("CNPJ", 14) + " |"
        );
        System.out.println("---------------------------------------------");
        if (!editoras.isEmpty()) {
            for (Editora editora : editoras) {
                System.out.println(
                    "| " + ColunaUtils.formatarColuna(String.valueOf(editora.getId()), 6) + " | " +
                    ColunaUtils.formatarColuna(editora.getNome(), 14) + " | " +
                    ColunaUtils.formatarColuna(editora.getCnpj(), 14) + " |"
                );
            }
        }
        System.out.println("---------------------------------------------");
    }

    private void editarEditora() {
        listarEditoras();
        System.out.println("[!] ID da Editora: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("[!] Nome (pressione [ENTER] para manter atual): ");
        String nome = scanner.nextLine();
        System.out.println("[!] CNPJ (pressione [ENTER] para manter atual): ");
        String cnpj = scanner.nextLine();
        Endereco endereco = enderecoMenu.cadastrarOuPular();
        Editora editora = new Editora(nome.isEmpty() ? null : nome, cnpj.isEmpty() ? null : cnpj);
        editora.setEnderecoMatrizId(endereco == null ? 0 : endereco.getId());
        editoraService.editarEditora(id, editora);
    }

    private void excluirEditora() {
        listarEditoras();
        System.out.println("[!] ID da Editora: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        editoraService.excluirEditora(id);
    }

}
