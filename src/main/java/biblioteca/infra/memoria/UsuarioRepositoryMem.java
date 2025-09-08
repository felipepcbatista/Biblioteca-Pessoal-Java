package biblioteca.infra.memoria;

import biblioteca.domain.Usuario;
import biblioteca.infra.repo.UsuarioRepository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;


public class UsuarioRepositoryMem implements UsuarioRepository{

    private final Map<Long, Usuario> db = new HashMap<>();
    private long seq = 1L;

    @Override
    public long proximoId (){
        return seq++;
    }

    @Override
    public Usuario salvar (Usuario user){
        db.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<Usuario> buscarPorId (long id){
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Usuario> listar(){
        return new ArrayList<>(db.values());
    }
}