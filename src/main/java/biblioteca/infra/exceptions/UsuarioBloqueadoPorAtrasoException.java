package biblioteca.infra.exceptions;

public class UsuarioBloqueadoPorAtrasoException extends RuntimeException {
    public UsuarioBloqueadoPorAtrasoException(String message) {
        super(message);
    }
}
