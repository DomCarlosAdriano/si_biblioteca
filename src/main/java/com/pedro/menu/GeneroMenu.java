package com.pedro.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.pedro.config.IO;
import com.pedro.models.Genero;
import com.pedro.service.GeneroService;
import com.pedro.utils.ColunaUtils;

public class GeneroMenu {

    private Scanner scanner;
    private GeneroService generoService;
    private IO io;

    public GeneroMenu() {
        scanner = new Scanner(System.in);
        generoService = new GeneroService();
        io = new IO();
    }

    public void imprimirMenu() {
        List<String> opcoesGenero = new ArrayList<String>(Arrays.asList(
                "1. Cadastrar gênero",
                "2. Consultar gêneros",
                "3. Editar editora",
                "4. Excluir editora",
                "5. Voltar"));
        int opc = io.imprimirMenuRetornandoOpcao(opcoesGenero, "MENU GÊNERO");
        while (opc != 5) {
            switch (opc) {
                case 1:
                    cadastrarGenero();
                    break;
                case 2:
                    listarGeneros();
                    break;
                case 3:
                    editarGenero();
                    break;
                case 4:
                    excluirGenero();
                    break;
                default:
                    System.out.println("[!] Opção Inválida.");
            }
            opc = io.imprimirMenuRetornandoOpcao(opcoesGenero, "MENU GÊNERO");
        }

    }

    public void cadastrarGenero() {
        System.out.println("[!] Gênero: ");
        String genero = scanner.nextLine();
        generoService.cadastrarGenero(new Genero(genero));
    }

    public void listarGeneros() {
        List<Genero> generos = generoService.listar();
        System.out.println("----------GÊNERO-----------");
        System.out.println(
            "| " + ColunaUtils.formatarColuna("ID", 6) + " | " + ColunaUtils.formatarColuna("Descrição", 12) + " |"
        );
        System.out.println("---------------------------");
        if (!generos.isEmpty()) {
            for (Genero genero : generos) {
                System.out.println(
                    "| " + ColunaUtils.formatarColuna(String.valueOf(genero.getId()), 6) +
                    " | " + ColunaUtils.formatarColuna(genero.getGenero(), 12) + " |"
                );
            }
        }
        System.out.println("---------------------------");
    }

    public void editarGenero() {
        listarGeneros();
        System.out.println("[!] ID do Gênero: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("[!] Gênero (pressione [ENTER] para manter):");
        String genero = scanner.nextLine();
        generoService.editarGenero(id ,new Genero(genero.isEmpty() ? null : genero));
    }

    public void excluirGenero() {
            listarGeneros();
            System.out.println("[!] ID do Gênero: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            generoService.excluirGenero(id);
    }

}
