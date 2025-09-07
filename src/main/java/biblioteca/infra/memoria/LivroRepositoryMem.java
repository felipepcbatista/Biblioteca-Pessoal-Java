package biblioteca.infra.memoria;

import biblioteca.domain.Livro;
import biblioteca.infra.repo.LivroRepository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class LivroRepositoryMem implements LivroRepository{
    private final Map <Long, Livro> db = new HashMap<>();
    private long seq = 1L;

    @Override
    public long proximoId (){
        return seq++;
    }

    @Override
    public Livro salvar (Livro livro){
        db.put(livro.getId(), livro);
        return livro;
    }

    @Override
    public Optional<Livro> buscarPorId (long id){
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Livro> listar(){
        return new ArrayList<>(db.values());
    }

    @Override
    public void removerLivro (long id){
        db.remove(id);
    }
}