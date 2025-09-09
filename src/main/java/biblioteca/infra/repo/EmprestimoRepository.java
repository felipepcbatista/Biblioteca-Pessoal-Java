package biblioteca.infra.repo;

import biblioteca.domain.Emprestimo;
import java.util.Optional;
import java.util.List;

public interface EmprestimoRepository{

    Emprestimo salvar (Emprestimo e);
    Optional<Emprestimo> buscarPorId (long id);
    List<Emprestimo> listar();
    List<Emprestimo> listarPorUsuario (long usuarioId);
    long proximoId();
}