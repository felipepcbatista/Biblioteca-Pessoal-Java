package biblioteca.cli;

import biblioteca.application.BibliotecaService;

import biblioteca.domain.Livro;

import biblioteca.infra.exceptions.*;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;

public class MenuPrincipal{
    private final BibliotecaService service;
    private final Scanner in;

    public MenuPrincipal (BibliotecaService service, Scanner in){
        this.service = service;
        this.in = in;
    }

    private String lerNaoVazio (String prompt){
        while (true){
            System.out.print(prompt);
            String s = in.nextLine().trim();
            if (!s.isBlank()){ return s; }
            System.out.println("Valor não pode ser vazio.");
        }
    }

    private long lerLong (String prompt){
        while (true){
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try { return Long.parseLong(s); }
            catch (NumberFormatException e){
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    private int lerInt (String prompt){
        while (true){
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e){
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    private void cadastrarLivro(){
        String titulo = lerNaoVazio ("Título: ");
        String autor = lerNaoVazio ("Autor: ");
        int ano = lerInt ("Ano: ");
        var livro = service.cadastrarLivro(titulo, autor, ano);
        System.out.println("Cadastrado:\n" + livro);
    }

    private void cadastrarUsuario(){
        String nome = lerNaoVazio ("Nome: ");
        String email = lerNaoVazio ("Email: ");
        try{
            var usuario = service.cadastrarUsuario(nome, email);
            System.out.println("Cadastrado: " + usuario.getNome() + " | " + usuario.getEmail());
        } catch (EmailInvalidoException e){
            System.out.println ("Erro: " + e.getMessage());
        }
    }

    private void emprestarLivro (){
        long livroId = lerLong ("ID do livro: ");
        long usuarioId = lerLong ("ID do usuário: ");
        try{
            var emprestimo = service.emprestar(livroId, usuarioId, LocalDate.now());
            System.out.println("Empréstimo criado: " + emprestimo);
        } catch (LivroNaoEncontradoException | LivroIndisponivelException |
                UsuarioNaoEncontradoException | UsuarioBloqueadoPorAtrasoException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void devolverLivro(){
        long emprestimoId = lerLong ("ID do empréstimo: ");
        try{
            double multa = service.devolver(emprestimoId, LocalDate.now());
            System.out.printf("Devolvido. Valor a ser pago: R$ %.2f%n", multa);
        } catch (EmprestimoNaoEncontradoException | EmprestimoJaDevolvidoException e){
            System.out.println("Erro: " + e.getMessage());
        } catch (LivroNaoEncontradoException e){
            System.out.println("Aviso: " + e.getMessage());
        }
    }

    private void listarLivros(){
        List<Livro> livros = service.listarLivros();
        if (livros.isEmpty()) { System.out.println("(nenhum livro no sistema)"); }
        else livros.forEach (System.out::println);
    }

    private void listarLivrosDisponiveis(){
        var livros = service.listarLivrosDisponiveis();
        if(livros.isEmpty()) { System.out.println("(nenhum livro disponível no sistema)"); }
        else livros.forEach (System.out::println);
    }

    private void listarEmprestimos(){
        var emprestimos = service.listarEmprestimos();
        if (emprestimos.isEmpty()) { System.out.println("(nenhum empréstimo no sistema)"); }
        else emprestimos.forEach(System.out::println);
    }

    private void listarEmprestimosEmAtraso(){
        var emprestimos = service.listarEmprestimosEmAtraso(LocalDate.now());
        if (emprestimos.isEmpty()) { System.out.println("(nenhum empréstimo em atraso no sistema)"); }
        else emprestimos.forEach (System.out::println);
    }

    private void mostrarMenu(){
        System.out.println("""
                ================= BIBLIOTECA =================
                1- Cadastrar livro
                2- Cadastrar usuário
                3- Emprestar livro
                4- Devolver livro
                5- Listar todos os livros
                6- Listar livros disponíveis
                7- Listar empréstimos
                8- Listar empréstimos em atraso
                9- Sair
                """);
    }

    public void executar(){
        String opc;
        do{
            mostrarMenu();
            opc = lerNaoVazio ("Escolha uma opção: ");

            switch (opc){
                case "1" -> cadastrarLivro();
                case "2" -> cadastrarUsuario();
                case "3" -> emprestarLivro();
                case "4" -> devolverLivro();
                case "5" -> listarLivros();
                case "6" -> listarLivrosDisponiveis();
                case "7" -> listarEmprestimos();
                case "8" -> listarEmprestimosEmAtraso();
                case "9" -> System.out.println("Obrigado. Volte sempre!");
                default -> System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (!opc.equals("9"));
    }
}