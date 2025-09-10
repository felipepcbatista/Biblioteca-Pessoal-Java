package biblioteca.cli;

import biblioteca.application.BibliotecaService;

import biblioteca.infra.repo.*;
import biblioteca.infra.memoria.*;

import java.util.Scanner;

public class BibliotecaCLI{
    public static void main (String[] args){
        LivroRepository livroRepo = new LivroRepositoryMem();
        UsuarioRepository usuarioRepo = new UsuarioRepositoryMem();
        EmprestimoRepository emprestimoRepo = new EmprestimoRepositoryMem();

        BibliotecaService service = new BibliotecaService (livroRepo, usuarioRepo, emprestimoRepo);

        try (Scanner in = new Scanner (System.in)){
            new MenuPrincipal (service, in).executar();
        }
    }
}