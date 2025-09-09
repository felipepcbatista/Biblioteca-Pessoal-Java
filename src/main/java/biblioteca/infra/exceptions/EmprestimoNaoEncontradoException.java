package biblioteca.infra.exceptions;

public class EmprestimoNaoEncontradoException extends RuntimeException {
    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }
}
