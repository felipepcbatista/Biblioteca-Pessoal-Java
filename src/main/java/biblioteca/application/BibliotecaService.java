package biblioteca.application;

import biblioteca.domain.*;
import biblioteca.infra.repo.*;

import biblioteca.infra.exceptions.LivroNaoEncontradoException;
import biblioteca.infra.exceptions.LivroIndisponivelException;
import biblioteca.infra.exceptions.UsuarioNaoEncontradoException;
import biblioteca.infra.exceptions.UsuarioBloqueadoPorAtrasoException;
import biblioteca.infra.exceptions.EmprestimoNaoEncontradoException;
import biblioteca.infra.exceptions.EmprestimoJaDevolvidoException;


import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class BibliotecaService {

    private final LivroRepository livroRepo;
    private final UsuarioRepository usuarioRepo;
    private final EmprestimoRepository emprestimoRepo;

    public BibliotecaService(LivroRepository livroRepo, UsuarioRepository usuarioRepo, EmprestimoRepository emprestimoRepo) {
        this.livroRepo = livroRepo;
        this.usuarioRepo = usuarioRepo;
        this.emprestimoRepo = emprestimoRepo;
    }

    private boolean livroDisponivel (Livro livro){
        return livro.getStatus() == Livro.Status.DISPONIVEL;
    }

    private boolean usuarioTemAtraso (long usuarioId, LocalDate hoje){
        List<Emprestimo> emprestimos = emprestimoRepo.listarPorUsuario(usuarioId);

        for (Emprestimo e : emprestimos){
            if (e.isAberto() && hoje.isAfter(e.getDataPrevista())){
                return true;
            }
        }
        return false;
    }

    private double calcularMulta (Emprestimo emprestimo, LocalDate hoje){
        long diasDeAtraso = emprestimo.diasDeAtraso(hoje);
        return diasDeAtraso * 1.00;
    }

    public Livro cadastrarLivro(String titulo, String autor, int ano) {
        long livroId = livroRepo.proximoId();

        Livro livro = new Livro (livroId, titulo, autor, ano);
        livroRepo.salvar(livro);

        return livro;
    }

    public Usuario cadastrarUsuario(String nome, String email){
        long usuarioId = usuarioRepo.proximoId();

        Usuario user = new Usuario (usuarioId, nome, email);
        usuarioRepo.salvar(user);

        return user;
    }

    public Emprestimo emprestar (long livroId, long usuarioId, LocalDate hoje){
        Livro livro = livroRepo.buscarPorId(livroId)
                .orElseThrow(()-> new LivroNaoEncontradoException("Livro não encontrado: " + livroId + "."));

        if (!livroDisponivel(livro)) {
            throw new LivroIndisponivelException("Livro " + livro.getTitulo() + " está indisponível no momento.");
        }

        Usuario user = usuarioRepo.buscarPorId(usuarioId)
                .orElseThrow(()-> new UsuarioNaoEncontradoException("Usuário não encontrado: " + usuarioId + "."));

        if (usuarioTemAtraso(usuarioId, hoje)){
            throw new UsuarioBloqueadoPorAtrasoException ("Usuário " + user.getNome() + " está bloqueado por atraso.");
        }

        long id = emprestimoRepo.proximoId();

        Emprestimo emprestimo = new Emprestimo (id, livroId, usuarioId, hoje);
        emprestimoRepo.salvar(emprestimo);

        livro.marcarEmprestado();
        livroRepo.salvar(livro);

        return emprestimo;
    }

    public double devolver (long emprestimoId, LocalDate hoje){
        Emprestimo emprestimo = emprestimoRepo.buscarPorId(emprestimoId)
                .orElseThrow(()-> new EmprestimoNaoEncontradoException("Empréstimo não encontrado: " + emprestimoId + "."));

        if (emprestimo.isDevolvido()){
            throw new EmprestimoJaDevolvidoException ("Empréstimo já devolvido.");
        }

        double multa = calcularMulta(emprestimo, hoje);

        emprestimo.marcarDevolvido(hoje);
        emprestimoRepo.salvar(emprestimo);

        Livro livro = livroRepo.buscarPorId(emprestimo.getLivroId())
                .orElseThrow(()-> new LivroNaoEncontradoException ("Livro do empréstimo não encontrado: " + emprestimo.getLivroId() + "."));

        livro.marcarDisponivel();
        livroRepo.salvar(livro);

        return multa;
    }

    public List<Livro> listarLivros(){
        return livroRepo.listar();
    }

    public List<Livro> listarLivrosDisponiveis(){
        List<Livro> todos = livroRepo.listar();
        List<Livro> resultado = new ArrayList<>();

        for (Livro l : todos){
            if (l.getStatus() == Livro.Status.DISPONIVEL){
                resultado.add(l);
            }
        }
        return resultado;
    }

    public List<Emprestimo> listarEmprestimos(){
        return emprestimoRepo.listar();
    }

    public List<Emprestimo> listarEmprestimosEmAtraso(LocalDate hoje){
        List<Emprestimo> todos = emprestimoRepo.listar();
        List<Emprestimo> resultado = new ArrayList<>();

        for(Emprestimo e : todos){
            if (e.isAberto() && hoje.isAfter(e.getDataPrevista())){
                resultado.add(e);
            }
        }
        return resultado;
    }

}