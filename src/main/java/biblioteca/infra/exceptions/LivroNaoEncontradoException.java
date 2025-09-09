package biblioteca.infra.exceptions;

public class LivroNaoEncontradoException extends RuntimeException{
    public LivroNaoEncontradoException (String message){
        super(message);
    }
}