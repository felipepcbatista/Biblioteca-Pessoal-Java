package biblioteca.infra.repo;

import biblioteca.domain.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository{

    Usuario salvar (Usuario user);
    Optional<Usuario> buscarPorId (long id);
    List<Usuario> listar ();
    long proximoId ();
}