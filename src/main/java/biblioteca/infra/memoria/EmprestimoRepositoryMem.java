package biblioteca.infra.memoria;

import biblioteca.domain.Emprestimo;
import biblioteca.infra.repo.EmprestimoRepository;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class EmprestimoRepositoryMem implements EmprestimoRepository{

    private final Map< Long, Emprestimo> db = new HashMap<>();
    private long seq = 1L;

    @Override
    public long proximoId () { return seq++; }

    @Override
    public Emprestimo salvar (Emprestimo e){
        db.put(e.getId(), e);
        return e;
    }

    @Override
    public Optional<Emprestimo> buscarPorId (long id){ return Optional.ofNullable(db.get(id)); }

    @Override
    public List<Emprestimo> listar() {
        return new ArrayList<>(db.values());
    }

    @Override
    public List<Emprestimo> listarPorUsuario (long usuarioId){
        List<Emprestimo> result = new ArrayList<>();
        for (Emprestimo e : db.values()){
            if (e.getUsuarioId() == usuarioId){
                result.add(e);
            }
        }
        return result;
    }
}