package biblioteca.infra.exceptions;

public class EmprestimoJaDevolvidoException extends RuntimeException {
    public EmprestimoJaDevolvidoException(String message) {
        super(message);
    }
}
