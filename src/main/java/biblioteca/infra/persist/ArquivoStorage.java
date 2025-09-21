package biblioteca.infra.persist;

import java.io.IOException;
import java.util.List;

public interface ArquivoStorage<T> {
    void salvar (List<T> dados, String caminho) throws IOException;
    List<T> carregar (String caminho) throws IOException;
}