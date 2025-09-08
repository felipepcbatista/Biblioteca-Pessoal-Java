package biblioteca.infra.repo;

import biblioteca.domain.Livro;
import java.util.List;
import java.util.Optional;

public interface LivroRepository{

    Livro salvar (Livro livro);
    Optional<Livro> buscarPorId (long id);
    List<Livro> listar();
    long proximoId ();
}